package nio;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;

public class NioServer implements Runnable {
	// The host:port combination to listen on
	private InetAddress hostAddress;
	private int port;

	// The channel on which we'll accept connections
	private ServerSocketChannel serverChannel;

	// The selector we'll be monitoring
	private Selector selector;

	// The buffer into which we'll read data when it's available
	private ByteBuffer readBuffer = ByteBuffer.allocate(8192);

	private EchoWorker worker;

	// A list of PendingChange instances
	private List pendingChanges = new LinkedList();

	// Maps a SocketChannel to a list of ByteBuffer instances
	private Map pendingData = new HashMap();

	public NioServer(InetAddress hostAddress, int port, EchoWorker worker) throws IOException {
		this.hostAddress = hostAddress;
		this.port = port;
		this.selector = this.initSelector();
		this.worker = worker;
	}

	@SuppressWarnings("unchecked")
	public void send(SocketChannel socket, byte[] data) {
		synchronized (this.pendingChanges) {
			// Indicate we want the interest ops set changed
			this.pendingChanges.add(new ChangeRequest(socket, ChangeRequest.CHANGEOPS, SelectionKey.OP_WRITE));

			// And queue the data we want written
            // 这里 他把hashMap给锁住了 防止在判断后和put之前这一段时间可能发生的线程问题 就是防止拿到键的值后又有其他线程把键的值给修改了
            // 这时候用拿到的键的值修改后再放回去就可能出错了 所以他锁住了 而在netty的ConstantPool类的getOrCreate方法里
            // 就并没有把他的那个Map(实际类型是ConcurrentMap)锁住 像这个感觉可以用computeIfAbsent 避免锁this.pendingData
			synchronized (this.pendingData) {
				List queue = (List) this.pendingData.get(socket);
				if (queue == null) {
					queue = new ArrayList();
					this.pendingData.put(socket, queue);
				}
				queue.add(ByteBuffer.wrap(data));
			}
		}

		// Finally, wake up our selecting thread so it can make the required changes
		this.selector.wakeup();
	}

	public void run() {
		while (true) {
			try {
				// Process any pending changes
                // 第一次循环没有数据 会直接走过去这段代码 然后在下面的select处阻塞
                // 阻塞住之后 就会等待客户端的连接 连接之后 阻塞的select方法就胡返回 然后进入accpt方法
                // accpt和以往一样 把socketChannel注册READ 感知客户端的输入 其实也是感知对方端获取socketChannel的write
                // 方法执行完结束 然后又会走循环 下面的这段 代码还是没数据 直接走过去 然后又在下面的select方法阻塞住
                // 等到客户端有数据输入了 阻塞方法返回 客户端获取socketChannel然后write 这里就感知read事件了

                // 再走read方法 read方法会自己先读取数据 然后调用handler去具体处理数据 由于handler是个线程 所以可以认为
                // read方法走到调用handler的时候就已经结束了 handler是个线程 他用另一个线程去初始化pendingChanges和pendingData里的数据
                // 所以当read结束再走到这的时候pendignChanges里已经有数据了 有数据他就拿数据去注册 数据是write他就注册write
                // 正在注册的时候 这时handler就开始调用wakeup了 所以这里还没有注册完write 下面的select就要响应wakeup了
                // select往下 但是键没有注册完 所以下面while判断没有键 就又返回这了 这次键也注册完了 这个while会跳过去
                // 然后到select 发现是write 就往下走write方法

                // 先获取socketChannel然后执行write 这时客户端刚才注册的read键就会立刻感知到 然后走客户端的read方法
                // 客户端read里调用RspHandler去处理具体数据 同时这里 write方法还没有执行完
                // 在获取socketChannel执行write方法后 又注册了一个read键 等write方法执行完返回的时候selectedKey集合已经为空了
                // 因为新注册的那个read键需要再重新调用获取selectedKey才能获取到 所以就会跳回开始重新执行 在到select的时候
                // 发现是read 而且刚才write了 所以这里不会阻塞 接着再走read方法
                // 只不过这次走read读取到的是-1 然后接直接return了


                //
				synchronized (this.pendingChanges) {
					Iterator changes = this.pendingChanges.iterator();
					while (changes.hasNext()) {
						ChangeRequest change = (ChangeRequest) changes.next();
						switch (change.type) {
                        // 这段代码的意思好像是 收到请求 然后取出关联的socket 然后把socket之前注册的键拿出来
                        // 然后更改注册的键的感兴趣事件 之前都是socketChannel.register(selector, SelectionKey.OP_READ);
                        // 而且前面这个代码是在判断如果键是isAcceptable后使用
                        // 就是通道注册到selector上 然后给一个数字SelectionKey.OP_READ 让selector知道通道感兴趣的事件
                        // 这样selector就会生成一个关联的键 并且感兴趣的事件是那个给定的数字SelectionKey.OP_READ
                        // 他这种可能是另外一种写法 而且也没有判断键是isAcceptable还是isReadable就把键感兴趣的事件给改了
						case ChangeRequest.CHANGEOPS:
							SelectionKey key = change.socket.keyFor(this.selector);
							key.interestOps(change.ops);
						}
					}
					this.pendingChanges.clear();
				}

				// Wait for an event one of the registered channels
				this.selector.select();

				// Iterate over the set of keys for which events are available
				Iterator selectedKeys = this.selector.selectedKeys().iterator();
				while (selectedKeys.hasNext()) {
					SelectionKey key = (SelectionKey) selectedKeys.next();
					selectedKeys.remove();

					if (!key.isValid()) {
						continue;
					}

					// Check what event is available and deal with it
					if (key.isAcceptable()) {
						this.accept(key);
					} else if (key.isReadable()) {
						this.read(key);
					} else if (key.isWritable()) {
						this.write(key);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void accept(SelectionKey key) throws IOException {
		// For an accept to be pending the channel must be a server socket channel.
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

		// Accept the connection and make it non-blocking
		SocketChannel socketChannel = serverSocketChannel.accept();
		Socket socket = socketChannel.socket();
		socketChannel.configureBlocking(false);

		// Register the new SocketChannel with our Selector, indicating
		// we'd like to be notified when there's data waiting to be read
		socketChannel.register(this.selector, SelectionKey.OP_READ);
	}

	private void read(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();

		// Clear out our read buffer so it's ready for new data
		this.readBuffer.clear();

		// Attempt to read off the channel
		int numRead;
		try {
			numRead = socketChannel.read(this.readBuffer);
		} catch (IOException e) {
			// The remote forcibly closed the connection, cancel
			// the selection key and close the channel.
			key.cancel();
			socketChannel.close();
			return;
		}

		if (numRead == -1) {
			// Remote entity shut the socket down cleanly. Do the
			// same from our end and cancel the channel.
			key.channel().close();
			key.cancel();
			return;
		}

		// Hand the data off to our worker thread
		this.worker.processData(this, socketChannel, this.readBuffer.array(), numRead);
	}

	private void write(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();

		synchronized (this.pendingData) {
			List queue = (List) this.pendingData.get(socketChannel);

			// Write until there's not more data ...
			while (!queue.isEmpty()) {
				ByteBuffer buf = (ByteBuffer) queue.get(0);
				socketChannel.write(buf);
				if (buf.remaining() > 0) {
					// ... or the socket's buffer fills up
					break;
				}
				queue.remove(0);
			}

			if (queue.isEmpty()) {
				// We wrote away all data, so we're no longer interested
				// in writing on this socket. Switch back to waiting for
				// data.
				key.interestOps(SelectionKey.OP_READ);
			}
		}
	}

	private Selector initSelector() throws IOException {
		// Create a new selector
		Selector socketSelector = SelectorProvider.provider().openSelector();

		// Create a new non-blocking server socket channel
		this.serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);

		// Bind the server socket to the specified address and port
		InetSocketAddress isa = new InetSocketAddress(this.hostAddress, this.port);
		serverChannel.socket().bind(isa);

		// Register the server socket channel, indicating an interest in 
		// accepting new connections
		serverChannel.register(socketSelector, SelectionKey.OP_ACCEPT);

		return socketSelector;
	}

	public static void main(String[] args) {
		try {
			EchoWorker worker = new EchoWorker();
			new Thread(worker).start();

			// NioServer就相当于是BossGroup 他会监听连接的到来 也只有是连接的时候 他才会自己处理
            // 像读取客户端数据再处理这种操作 他不是完全自己做的 读数据这个他自己做 但是处理数据这个他交给了EchoWorker去处理
            // 这也就是说EchoWorker相当于了workerGroup EchoWorker这个线程可以升级为线程池 不升级线程池的话就相当于是一个线程去处理BossGroup交给的任务了
            // 当EchoWorker没有数据处理的时候 他就会wait 当有数据需要处理的时候 他也不会自己处理 他把数据封装了一下就交给了ServerDataEvent去处理
            // 这个ServerDataEvent就 相当于是注册到wokerGroup的handler了 这个ServerDataEvent会真正的去处理数据
            // 这个例子中ServerDataEvent又调用了Server中的方法 就是又调用了BossGroup中的方法了

			new Thread(new NioServer(InetAddress.getLocalHost(), 9090, worker)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
