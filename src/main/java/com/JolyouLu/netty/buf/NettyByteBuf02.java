package com.JolyouLu.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @Author: LZJ
 * @Date: 2020/10/3 19:01
 * @Version 1.0
 */
public class NettyByteBuf02 {
    public static void main(String[] args) {
        //创建byteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", CharsetUtil.UTF_8);

        if (byteBuf.hasArray()){ //查看byteBuf有没有分配数组
            byte[] content = byteBuf.array();
            //将content转字符串
            System.out.println(new String(content,CharsetUtil.UTF_8));
            System.out.println(byteBuf.arrayOffset());   //0  获取数组偏移量
            System.out.println(byteBuf.readerIndex());   //0  获取当前读到下标
            System.out.println(byteBuf.writerIndex());   //12 获取当前写到的下标
            System.out.println(byteBuf.capacity());      //Unpooled.copiedBuffer创建的byteBuf的capacity偏大
            System.out.println(byteBuf.readableBytes()); //当前可读字节数 调用一次readByte会 -1

            //使用for循环获取
            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.print((char) byteBuf.getByte(i));
            }
            System.out.println("");
            //获取指定范围的字符
            System.out.println(byteBuf.getCharSequence(0,4,CharsetUtil.UTF_8));
        }
    }
}
