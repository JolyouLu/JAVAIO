package com.JolyouLu.nio.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.SocketChannel;

/**
 * @Author: LZJ
 * @Date: 2020/10/1 13:14
 * @Version 1.0
 */
public class ZeroIOClient {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7001));
        String fileName = "src/main/resources/file01.txt";
        //得到一个文件的channel
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();
        //准备发送
        long startTime = System.currentTimeMillis();
        //在linux下一个transferTo 方法
        //在Windows下一次调用transferTo 只能发送8M的文件，需要分段，而且要注意传输位置
        //transferTo底层使用到零拷贝
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的总的字节数 ="+transferCount + " 耗时"+(System.currentTimeMillis() - startTime));
        fileChannel.close();
    }
}
