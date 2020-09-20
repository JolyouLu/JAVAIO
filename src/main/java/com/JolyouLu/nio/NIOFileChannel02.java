package com.JolyouLu.nio;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: LZJ
 * @Date: 2020/9/19 16:52
 * @Version 1.0
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception {
        //创建文件输入流
        File file = new File("src/main/resources/file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        //通过fileInputStream 获取相应的channel
        FileChannel channel = fileInputStream.getChannel();
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        //从通道读取数据写入到byteBuffer
        channel.read(byteBuffer);
        //将byteBuffer的数据转成String
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();

    }
}
