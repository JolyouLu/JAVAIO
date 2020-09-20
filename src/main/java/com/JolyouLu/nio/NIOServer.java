package com.JolyouLu.nio;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: LZJ
 * @Date: 2020/9/19 21:13
 * @Version 1.0
 */
public class NIOServer {
    public static void main(String[] args) throws Exception {
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个Select对象
        Selector selector = Selector.open();
        //绑定监听端口6666
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel注册到selector中关心事件为 OP_ACCEPT(连接事件)
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端连接
        while (true){
            if (selector.select(1000) == 0){//阻塞1秒 返回0 没有事件发生
                System.out.println("服务器等待1秒，无连接发生");
                continue;
            }
            //如果返回>0，表示有事件发生
            //selector.selectedKeys()返回有事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //使用迭代器遍历集合
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                //获取SelectionKeyServerSocketChannel
                SelectionKey selectionKey = keyIterator.next();
                //根据SelectionKey对应的通道发生的事件做相应的处理
                if (selectionKey.isAcceptable()){ //连接事件 OP_ACCEPT
                    //给连接的客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置未非阻塞
                    socketChannel.configureBlocking(false);
                    //将来当前的socketChannel注册到selector上
                    //关注事件读（OP_READ） 指定一个ByteBuffer给它
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("当前服务器连接客户端总数 "+selector.keys().size());
                }
                if (selectionKey.isReadable()){//读取事件 OP_READ
                    //通过key 方向获取对应的channel
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    //获取到该channel指定的buffer
                    ByteBuffer buffer = (ByteBuffer)selectionKey.attachment();
                    channel.read(buffer);
                    System.out.println("form 客户端 " + new String(buffer.array()));
                }
                //手动从集合中移除当前的selectionKey，防止重复操作
                keyIterator.remove();
            }
        }
    }
}
