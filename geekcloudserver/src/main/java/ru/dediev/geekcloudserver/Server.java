package ru.dediev.geekcloudserver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        SocketChannel socketChannel = serverSocketChannel.accept();


        final File outputFile = new File("rufus.exe");

        ByteBuffer buffer = ByteBuffer.allocate(8);
        socketChannel.read(buffer);
        buffer.flip();
        long length = buffer.getLong();

        FileChannel fileChannel = new FileOutputStream(outputFile).getChannel();

        long total = 0;
        while (total < length) {
            long transferred = fileChannel.transferFrom(socketChannel, total, length - total);
            if (transferred <= 0){
                break;
            }
            total += transferred;
        }

        buffer.clear();
        buffer.putLong(total);
        buffer.flip();
        socketChannel.write(buffer);

        serverSocketChannel.close();

        System.out.println("length received = " + length + ", saved file length = " + outputFile.length());
    }
}
