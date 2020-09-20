package com.JolyouLu.nio;

import java.nio.ByteBuffer;

/**
 * @Author: LZJ
 * @Date: 2020/9/19 17:50
 * @Version 1.0
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        //获取一个buffer并且写入数据
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            buffer.put((byte) i);
        }
        //读写切换
        buffer.flip();
        //得到一个只读的buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        //读取
        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
    }
}
