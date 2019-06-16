package nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;

public class NioClient implements Runnable {
	// The host:port combination to connect to
	private InetAddress hostAddress;
	private int port;

	// The selector we'll be monitoring
	private Selector selector;

	// The buffer into which we'll read data when it's available
	private ByteBuffer readBuffer = ByteBuffer.allocate(8192);

	// A list of PendingChange instances
	private List pendingChanges = new LinkedList();

	// Maps a SocketChannel to a list of ByteBuffer instances
	private Map pendingData = new HashMap();
	
	// Maps a SocketChannel to a RspHandler
	private Map rspHandlers = Collections.synchronizedMap(new HashMap());
	
	public NioClient(InetAddress hostAddress, int port) throws IOException {
		this.hostAddress = hostAddress;
		this.port = port;
		this.selector = this.initSelector();
	}

	public void send(byte[] data, RspHandler handler) throws IOException {
		// Start a new connection
		SocketChannel socket = this.initiateConnection();
		
		// Register the response handler
		this.rspHandlers.put(socket, handler);
		
		// And queue the data we want written
		synchronized (this.pendingData) {
			List queue = (List) this.pendingData.get(socket);
			if (queue == null) {
				queue = new ArrayList();
				this.pendingData.put(socket, queue);
			}
			queue.add(ByteBuffer.wrap(data));
		}

		// Finally, wake up our selecting thread so it can make the required changes
		this.selector.wakeup();
	}

	public void run() {
		while (true) {
			try {
				// Process any pending changes
                // 客户端先启动了 所以下面的代码会先运行 先运行的时候 this.pendingChanges是没有数据的 所以下面的这段代码
                // 会直接走过去 走到下面的this.selector.select() 然后就阻塞住了
                // 接着运行客户端的send方法 send方法会先初始化连接 初始化连接的时候才会初始化pendingchanges
                // 而且send方法会把要发送的数据初始化到pendingData中 等待都初始化过后 send方法调用了this.selector.wakeup();
                // 刚才走到this.selector.select()阻塞的地方就会返回 但是是没有selectionKey操作的 所以阻塞下面的代码也是直接
                // 就运行过去了 然后就又到了这里 这次到这的时候 pendingChanges是有数据的 初始化的是REGISTER 所以走REGISTER那块
                // 然后ops初始化的是CONNECTION的KEY 所以 这里就会先注册连接的键
                        // 之前的教程都是在上面的这个while循环外直接写注册连接的键 就是下面的代码
                        // SocketChannel socketChannel = SocketChannel.open();
                        // socketChannel.configureBlocking(false);
                        //
                        // Selector selector = Selector.open();
                        // socketChannel.register(selector, SelectionKey.OP_CONNECT);  -- 这里  会在这里直接写连接 然后才会去循环
                        // socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));
                        //
                        // while (true) {
                        //     selector.select();
                        //     Set<SelectionKey> keySet = selector.selectedKeys();
                        //     然后循环键判断
                        // }

                        // 但是他这个不是 他是直接写到了循环里面

                // 注册完键之后往下走到select 而且注册完connect键之后发现之前是连接过的
                // 所以走到select方法时不阻塞 会接着走finishConnection的方法 他的finishConnection里面
                // 注册的键是write方法 感觉他这个是合理的 他不需要监听自己的输入(read)嘛
                        // 不需要自己输入(read)完数据再对数据操作打印什么的嘛
                        // 自己的输入(read)是在server端监听的 他那里监听输入
                        // 然后对输入数据处理就可以了 张龙完成连接后注册的是read（输入） 没啥意思

                        // 是注册完write键select就会返回吗 跟read键不太一样吗 readble的键注册完后需要有输入select方法才会返回
                        // 而write的键注册完 也是需要等到socketChannel有针对他的输出 select方法才会返回

                // 然后finishConnection运行完就会再运行到这里 pendingChanges里没数据 会直接走过去 然后走到select
                // 但是服务端那里是没有主动对socketChannel的输出的 select怎么返回？ 对于注册write键的时候
                // 只要Socket可写,网络不出现阻塞情况下,一直是可以写的 所以注册write键运行到select的时候发现是write键
                // 不会阻塞 直接返回 接着走下面的write方法 write方法里面只要有client.write()代码 这里是socketChannel.write(buf);
                // 就会立刻触发另一方的read事件 这里write完又注册了read事件 然后又循环到这里 跳过pendingChanges
                        // 客户端中获取socketChannel写 那么服务端的select返回read事件
                        // 服务端获取socketChannel写 那么客户端的select返回read事件
                        // 如果客户单这边的socketChannel关闭 没有写 也是会触发服务端那边的read事件的
                // 然后阻塞到select方法处 等待服务器端再write回来 触发read事件 客户端的read方法调用RspHandler去处理具体数据
                        // RespHandler也是个两个同步方法 但他不是线程
                // RspHandler处理完会把socketChannel关闭 会再触发服务器端那边的read

				synchronized (this.pendingChanges) {
					Iterator changes = this.pendingChanges.iterator();
					while (changes.hasNext()) {
						ChangeRequest change = (ChangeRequest) changes.next();
						switch (change.type) {
						case ChangeRequest.CHANGEOPS:
							SelectionKey key = change.socket.keyFor(this.selector);
							key.interestOps(change.ops);
							break;
						case ChangeRequest.REGISTER:
							change.socket.register(this.selector, change.ops);
							break;
						}
					}
					this.pendingChanges.clear();
				}

				// Wait for an event one of the registered channels
				int a = this.selector.select();
				System.out.println(a);

				// Iterate over the set of keys for which events are available
				Iterator selectedKeys = this.selector.selectedKeys().iterator();
				while (selectedKeys.hasNext()) {
					SelectionKey key = (SelectionKey) selectedKeys.next();
					selectedKeys.remove();

					if (!key.isValid()) {
						continue;
					}

					// Check what event is available and deal with it
					if (key.isConnectable()) {
						this.finishConnection(key);
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

		// Handle the response
		this.handleResponse(socketChannel, this.readBuffer.array(), numRead);
	}

	private void handleResponse(SocketChannel socketChannel, byte[] data, int numRead) throws IOException {
		// Make a correctly sized copy of the data before handing it
		// to the client
		byte[] rspData = new byte[numRead];
		System.arraycopy(data, 0, rspData, 0, numRead);
		
		// Look up the handler for this channel
		RspHandler handler = (RspHandler) this.rspHandlers.get(socketChannel);
		
		// And pass the response to it
		if (handler.handleResponse(rspData)) {
			// The handler has seen enough, close the connection
			socketChannel.close();
			socketChannel.keyFor(this.selector).cancel();
		}
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

	private void finishConnection(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
	
		// Finish the connection. If the connection operation failed
		// this will raise an IOException.
		try {
			socketChannel.finishConnect();
		} catch (IOException e) {
			// Cancel the channel's registration with our selector
			System.out.println(e);
			key.cancel();
			return;
		}
	
		// Register an interest in writing on this channel
		key.interestOps(SelectionKey.OP_WRITE);
	}

	private SocketChannel initiateConnection() throws IOException {
		// Create a non-blocking socket channel
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
	
		// Kick off connection establishment
		socketChannel.connect(new InetSocketAddress(this.hostAddress, this.port));
	
		// Queue a channel registration since the caller is not the 
		// selecting thread. As part of the registration we'll register
		// an interest in connection events. These are raised when a channel
		// is ready to complete connection establishment.
		synchronized(this.pendingChanges) {
			this.pendingChanges.add(new ChangeRequest(socketChannel, ChangeRequest.REGISTER, SelectionKey.OP_CONNECT));
		}
		
		return socketChannel;
	}

	private Selector initSelector() throws IOException {
		// Create a new selector
		return SelectorProvider.provider().openSelector();
	}

	public static void main(String[] args) {
		try {
//			NioClient client = new NioClient(InetAddress.getByName("www.baidu.com"), 80);
			NioClient client = new NioClient(InetAddress.getLocalHost(), 9090);
			
			Thread t = new Thread(client);
			// t.setDaemon(true);
			t.start();
			Thread.sleep(1000);
			RspHandler handler = new RspHandler();
			client.send("GET / HTTP/1.0\r\n\r\n".getBytes(), handler);
			handler.waitForResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
