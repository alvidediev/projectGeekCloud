package ru.dediev.geekcloudclient.geekcloudclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class FileTransferClient {

    private static final String HOST = "localhost";
    private static final int PORT = 9999;

    public void sendFile(String path) throws IOException {
        File file = new File(path);
        System.out.println("file length = " + file.length());
        FileChannel fileChannel = new FileInputStream(file).getChannel();
        SocketAddress socketAddress = new InetSocketAddress(HOST, PORT);
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(socketAddress);

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(file.length());
        buffer.flip();
        socketChannel.write(buffer);

        long total = 0;
        while (total < file.length()) {
            long transferred = fileChannel.transferTo(total, file.length() - total, socketChannel);
            total += transferred;
        }

        buffer.clear();
        socketChannel.read(buffer);
        buffer.flip();
        long length = buffer.getLong();
        System.out.println("bytes received by server = " + length);

        socketChannel.finishConnect();
    }
}
