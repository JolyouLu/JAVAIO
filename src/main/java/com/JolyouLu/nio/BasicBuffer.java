package com.JolyouLu.nio;

import java.nio.IntBuffer;

/**
 * @Author: LZJ
 * @Date: 2020/9/13 13:51
 * @Version 1.0
 */
public class BasicBuffer {
    public static void main(String[] args) {
        //创建一个Buffer，初始化长度是5
        IntBuffer intBuffer = IntBuffer.allocate(5);
        //向Buffer存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i*2);
        }
        //从Buffer中获取数据
        //Buffer读写切换
        intBuffer.flip();
        //读取Buffer中的数据
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
