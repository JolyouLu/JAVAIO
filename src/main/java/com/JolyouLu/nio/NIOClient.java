package com.JolyouLu.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author: LZJ
 * @Date: 2020/9/20 12:14
 * @Version 1.0
 */
public class NIOClient {
    public static void main(String[] args) throws Exception {
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //要连接的服务端的ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器
        if (!socketChannel.connect(inetSocketAddress)){ //尝试连接服务端

            while (!socketChannel.finishConnect()){ //未连接成功，一直尝试连接
                System.out.println("客户端正在尝试连接服务端");
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其它工作");
            }

        }
        //..如果连接成功，发生数据
        String str = "Hello,world";
        //使用wrap 包裹一个字节数组 字节转成buffer
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        //发送数据，将来buffer数据写入channel
        socketChannel.write(buffer);
        System.in.read();
    }
}
