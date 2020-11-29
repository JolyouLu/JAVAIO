package com.JolyouLu.netty.dubborpc.provider;

import com.JolyouLu.netty.dubborpc.netty.NettyServer;

/**
 * @Author: JolyouLu
 * @Date: 2020/11/29 12:52
 * @Version 1.0
 */
//启动一个服务提供者
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1",7000);
    }
}
