package com.JolyouLu.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: LZJ
 * @Date: 2020/9/19 18:08
 * @Version 1.0
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception {
        //rw表示可读可写
        RandomAccessFile randomAccessFile = new RandomAccessFile("src/main/resources/file01.txt", "rw");
        //获取对应通道
        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数1：FileChannel.MapMode.READ_WRITE 使用读写模式
         * 参数2：0 可以直接修改的起始位置
         * 参数3：5 映射到内存的大小，即使1.txt有多少字节映射到内存
         * 可以直接修改的范围0开始到5个字节的位置
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');
        randomAccessFile.close();
    }
}
