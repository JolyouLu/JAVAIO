package com.JolyouLu.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @Author: LZJ
 * @Date: 2020/9/19 17:31
 * @Version 1.0
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/a.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/a2.jpg");
        //获取各个流对应的Channel
        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel outputStreamChannel = fileOutputStream.getChannel();
        //使用transferForm从别的通道复制数据过来
        outputStreamChannel.transferFrom(inputStreamChannel,0,inputStreamChannel.size());
        //关闭流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
