package com.cn.simple.netty.EventLoop;

import com.cn.simple.netty.EventLoop.EventLoop;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executor;

public class WorkerEventLoop extends EventLoop implements Worker{


    public WorkerEventLoop(Executor executor, String threadName, EventLoopProvider eventLoopProvider) {
        super(executor, threadName, eventLoopProvider);

    }

    @Override
    protected void selectorProcessTask(Selector selector) throws IOException {
        Set<SelectionKey> selectionKeys=selector.selectedKeys();
        if (selectionKeys.isEmpty()){
            System.out.println(Thread.currentThread()+"worker key.size==0");
            return;
        }
        System.out.println("分割线");
        Iterator<SelectionKey> it=this.selector.selectedKeys().iterator();
        while (it.hasNext()){
            System.out.println(Thread.currentThread()+"worker有新事件到来");
            SelectionKey key=it.next();
            it.remove();
            SocketChannel channel= (SocketChannel) key.channel();

            // 数据总长度
            int ret = 0;
            boolean failure = true;
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //读取数据
            try {
                ret = channel.read(buffer);
                failure = false;
            } catch (Exception e) {
                // ignore
            }

            //判断是否连接已断开
            if (ret <= 0 || failure) {
                key.cancel();
                System.out.println("客户端断开连接");
            }else{
                System.out.println("收到数据:" + new String(buffer.array()));

                //回写数据
                ByteBuffer outBuffer = ByteBuffer.wrap("收到\n".getBytes());
                writeOutBuffer(channel,outBuffer);

            }
        }
    }

    private void writeOutBuffer(final SocketChannel channel, final ByteBuffer outBuffer) {
        WorkerEventLoop worker=getEventLoopProvider().nextWorker();
        if (worker.eventThread().equals(this.eventThread())){
            try {
                channel.write(outBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            worker.executeTask(new Runnable() {
                @Override
                public void run() {
                    try {
                        channel.write(outBuffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void registServerChannel(final SocketChannel socketChannel) {
        final Selector selector=this.selector;
        System.out.println("workerRegistServerChannel");
        executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("socketChannel.register(selector, SelectionKey.OP_READ)");
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
