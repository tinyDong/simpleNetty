package com.cn.simple.netty;

import com.cn.simple.netty.EventLoop.BossEventLoop;
import com.cn.simple.netty.EventLoop.EventLoopProvider;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;

public class ServerBootStrap {

    private EventLoopProvider eventLoopProvider;


    public ServerBootStrap(EventLoopProvider eventLoopProvider) {
        this.eventLoopProvider=eventLoopProvider;
    }

    public void bind(SocketAddress localAddress) {
        try {
            // 获得一个ServerSocket通道
            ServerSocketChannel serverChannel = null;

            serverChannel = ServerSocketChannel.open();

            // 设置通道为非阻塞
            serverChannel.configureBlocking(false);
            // 将该通道对应的ServerSocket绑定到port端口
            serverChannel.socket().bind(localAddress);
            BossEventLoop boss=eventLoopProvider.nextBoss();
            boss.registServerChannel(serverChannel);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
