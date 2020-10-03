package com.JolyouLu.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author: LZJ
 * @Date: 2020/10/3 18:47
 * @Version 1.0
 */
public class NettyByteBuf01 {
    public static void main(String[] args) {
        //创建一个ByteBuf
        //1.创建一个对象，该对象包含了一个数组，是一个byte[10]
        //2.在netty的buffer总，不需要使用flip进行反转，因为底层维护了
        //readerIndex和writerIndex
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.writeByte(i);
        }
        //输出
        System.out.println("capacity ="+buffer.capacity());
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());
        }
    }
}
