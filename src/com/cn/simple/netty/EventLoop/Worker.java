package com.cn.simple.netty.EventLoop;

import java.nio.channels.SocketChannel;

public interface Worker {
    /**注册ServerChannel
     * @param socketChannel
     */
    public void registServerChannel(SocketChannel socketChannel);
}
