package com.cn.simple.netty.EventLoop;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

public class BossEventLoop extends EventLoop implements Boss{


    public BossEventLoop(Executor executor, String threadName, EventLoopProvider eventLoopProvider) {
        super(executor, threadName, eventLoopProvider);
    }

    @Override
    protected void processSelector(Selector selector) throws IOException {
        Set<SelectionKey> selectionKeys=selector.selectedKeys();
        if (selectionKeys.isEmpty()){
            return;
        }

        for (Iterator<SelectionKey> i = selectionKeys.iterator();i.hasNext();){
            SelectionKey key=i.next();
            i.remove();
            ServerSocketChannel serverSocketChannel= (ServerSocketChannel) key.channel();
            SocketChannel socketChannel=serverSocketChannel.accept();
            socketChannel.configureBlocking(false);

            WorkerEventLoop worker=getEventLoopProvider().nextWorker();

            worker.registServerChannel(socketChannel);
        }
    }

    @Override
    public void registServerChannel(final ServerSocketChannel serverChannel) {
        final Selector selector = this.selector;
        executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    serverChannel.register(selector, SelectionKey.OP_ACCEPT);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
