package com.JolyouLu.nio.groupchat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Author: LZJ
 * @Date: 2020/9/20 14:39
 * @Version 1.0
 */
public class GroupChatServer {
    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6666;

    //构造器 完成初始化工作
    public GroupChatServer() {
        try {
            //得到选择器
            selector = Selector.open();
            //初始化ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将来listenChannel注册到Selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //监听
    public void listen(){
        try {
            //循环监听
            while (true){
                int count = selector.select(2000); //阻塞2秒监听，通道有没有事件发生
                if (count > 0){ //返回>0 有事件要处理
                    //遍历selectedKeys集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        //获取SelectionKey
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()){ //如果通道发生，客户端连接事件
                            //为连接的客户端，生成socketChannel
                            SocketChannel socketChannel = listenChannel.accept();
                            //切换非阻塞模式
                            socketChannel.configureBlocking(false);
                            //把socketChannel注册到selector中，并监听读事件
                            socketChannel.register(selector,SelectionKey.OP_READ);
                            //提示客户端连接上了
                            System.out.println(socketChannel.getRemoteAddress() + " 客户端 上线");
                        }
                        if (key.isReadable()){//如果通道发生，可读事件
                            //处理读
                            readData(key);
                        }
                        //清理读取的selectedKeys容器 防止重复处理
                        iterator.remove();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }

    //读取客户端消息
    private void readData(SelectionKey key){
        //定义一个SocketChannel
        SocketChannel channel = null;
        try {
            //取到关联的channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据count的值做处理
            if (count > 0){ //读取到数据
                //把缓冲区的数据转字符串
                String msg = new String(buffer.array());
                //输出消息
                System.out.println("from 客户端："+msg);
                //向其它客户端转发消息
                sendInfoToOtherClients(msg,channel);
            }
        }catch (IOException e){
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了..");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    //转发消息给其它客户端(channel)
    private void sendInfoToOtherClients(String msg,SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中...");
        //遍历 所有的注册到Selector的SocketChannel排查self
        for (SelectionKey key : selector.keys()) {
            //取出通道
            Channel targetChannel = key.channel();
            //targetChanneld的类型是SocketChannel，并且targetChannel不是自己
            if (targetChannel instanceof SocketChannel && targetChannel != self){
                //转型
                SocketChannel dest = (SocketChannel)targetChannel;
                //将来msg 存到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将来buffer的数据写入通道
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        //初始化服务器对象
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }
}
