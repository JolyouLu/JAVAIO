package com.JolyouLu.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: LZJ
 * @Date: 2020/9/19 16:33
 * @Version 1.0
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception {
        String str = "hello,word";
        //创建一个输出流->包装到channel中
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/file01.txt");
        //通过fileOutputStream流获取对应的FileChannel
        FileChannel fileChannel = fileOutputStream.getChannel();
        //创建一个byteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将来str放入byteBuffer
        byteBuffer.put(str.getBytes());
        //读写切换
        byteBuffer.flip();
        //将来byteBuffer数据写入到fileChannel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
