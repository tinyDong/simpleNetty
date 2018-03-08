package com.cn.simple.netty.EventLoop;

import java.nio.channels.ServerSocketChannel;

public interface Boss {
    /**注册ServerChannel
     * @param serverChannel
     */
    public void registServerChannel(ServerSocketChannel serverChannel);
}
