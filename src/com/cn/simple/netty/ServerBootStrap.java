package com.cn.simple.netty;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;

public class ServerBootStrap {



    public void bind(SocketAddress localAddress) {
        try {
            // 获得一个ServerSocket通道
            ServerSocketChannel serverChannel = null;

            serverChannel = ServerSocketChannel.open();

            // 设置通道为非阻塞
            serverChannel.configureBlocking(false);
            // 将该通道对应的ServerSocket绑定到port端口
            serverChannel.socket().bind(localAddress);

            EventLoop bossEvent=this.eventLoopGroupManger.nextBossEvent();

            bossEvent.

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
