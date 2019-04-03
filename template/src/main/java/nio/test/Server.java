package nio.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: hAdIx
 * Date: 11-11-2
 * Time: ����11:26
 */
public class Server {
    private Selector selector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(8);//��������Ĵ�С���Կ�����ӡ����ı仯
    private Map<SocketChannel, byte[]> clientMessage = new ConcurrentHashMap<SocketChannel, byte[]>();

    public void start() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress("localhost", 8001));
        selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while (!Thread.currentThread().isInterrupted()) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (!key.isValid()) {
                    continue;
                }
                if (key.isAcceptable()) {
                    accept(key);
                } else if (key.isReadable()) {
                    read(key);
                }
                keyIterator.remove();
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
            clientMessage.remove(socketChannel);
            return;
        }

        byte[] bytes = clientMessage.get(socketChannel);
        if (bytes == null) {
            bytes = new byte[0];
        }
        if (numRead > 0) {
            byte[] newBytes = new byte[bytes.length + numRead];
            System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
            System.arraycopy(readBuffer.array(), 0, newBytes, bytes.length, numRead);
            clientMessage.put(socketChannel, newBytes);
            System.out.println(new String(newBytes));
        } else {
            String message = new String(bytes);
            System.out.println(message);
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = ssc.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("a new client connected");
    }


    public static void main(String[] args) throws IOException {
        System.out.println("server started...");
        new Server().start();
    }
}