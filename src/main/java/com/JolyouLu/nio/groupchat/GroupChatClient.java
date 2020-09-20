package com.JolyouLu.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Author: LZJ
 * @Date: 2020/9/20 15:14
 * @Version 1.0
 */
public class GroupChatClient {
    //定义相关属性
    private final String HOST = "127.0.0.1"; //服务器IP
    private final int PORT = 6666; //服务器端口
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    //构造器,初始化
    public GroupChatClient() {
        try {
            //得到选择器
            selector = Selector.open();
            //连接服务
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
            //设置非阻塞
            socketChannel.configureBlocking(false);
            //将来socketChannel注册到Selector，关注读事件
            socketChannel.register(selector, SelectionKey.OP_READ);
            //得到username
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + " is ok ...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //向服务器发送消息
    public void sendInfo(String info){
        info = username + " 说：" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //读取从服务器端回复的消息
    public void readInfo(){
        try {
            int readChannels = selector.select(2000);
            if (readChannels > 0){//有可用的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key.isReadable()){
                        //得到相关的通道
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        //得到一个Buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //buffer 读取通道数据
                        socketChannel.read(buffer);
                        //把读到缓冲区的数据转成字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                    iterator.remove();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //启动客户端
        GroupChatClient chatClient = new GroupChatClient();
        //启动一个线程,每隔开3秒读取服务器发送的数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    chatClient.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        //发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }

    }
}
