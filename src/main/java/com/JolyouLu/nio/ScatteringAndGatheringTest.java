package com.JolyouLu.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Author: LZJ
 * @Date: 2020/9/19 18:16
 * @Version 1.0
 * Scattering：将数据写入到buffer时，可以采用buffer数组，依次写入[分散]
 * Gathering：从buffer读取数据时，可以采用buffer数组，依次读取[聚合]
 */
public class ScatteringAndGatheringTest {

    public static void main(String[] args) throws Exception {
        //使用ServerSocketChannel和SocketChannel实现
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
        //绑定端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);
        //创建2个buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        //等待客户端连接(telnet)
        SocketChannel socketChannel = serverSocketChannel.accept();
        int msgLength = 8; //假定从客户端只接收8个字节
        //循环读取
        while (true){
            int byteRead = 0;
            while (byteRead < msgLength){
                //byteBuffers数组从socketChannel通道中读取数据
                long read = socketChannel.read(byteBuffers);
                byteRead += read; //累计读取的字节数
                System.out.println("byteRead="+byteRead);
                //使用流打印,查看byteBuffers数组 每个当前buffer的position和limit
                Arrays.asList(byteBuffers).stream().map(buffer -> "position="+ buffer.position() + ",limit=" + buffer.limit()).forEach(System.out::println);
            }
            //将所以的buffer进行flip 读写读写切换
            Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());
            //将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < msgLength){
                //byteBuffers数组内容写入socketChannel通道中
                long write = socketChannel.write(byteBuffers);
                byteWrite += write;
            }
            //将所有的buffer 进行cleat操作
            Arrays.asList(byteBuffers).forEach(buffer -> buffer.clear());
            System.out.println("byteRead="+byteRead + ",byteWrite=" + byteWrite + ",msgLength" + msgLength);
        }
    }
}
