package com.JolyouLu.nio;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: LZJ
 * @Date: 2020/9/19 16:52
 * @Version 1.0
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception {
        //创建一个输入流和获取对应的Channel
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/file01.txt");
        FileChannel inputChannel = fileInputStream.getChannel();
        //创建一个输出流和获取对应的Channel
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/file02.txt");
        FileChannel outputChannel = fileOutputStream.getChannel();
        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (true){
            int read = inputChannel.read(byteBuffer); //byteBuffer从通道读取数据
            if (read == -1){ // -1表示读完成
                break; //结束
            }
            byteBuffer.flip(); //读写反转
            outputChannel.write(byteBuffer); //byteBuffer写数据到Channel中
            byteBuffer.clear(); //byteBuffer内容清空
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
