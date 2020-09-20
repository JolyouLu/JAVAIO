package com.JolyouLu.nio;

import java.nio.ByteBuffer;

/**
 * @Author: LZJ
 * @Date: 2020/9/19 17:47
 * @Version 1.0
 */
public class NIOBufferPutGet {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.putInt(100);
        buffer.putLong(9L);
        buffer.putChar('L');
        buffer.putShort((short) 4);
        buffer.flip();
        //获取与存入类型必须保持一致否则会出现异常 BufferUnderflowException
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());

    }
}
