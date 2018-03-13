"# simpleNetty" 
###仿写的netty服务端模型

###主要是帮助理解netty线程模型

###关键点:
eventLoop是一个在单线程线程池内执行的任务,并且有个线程安全的队列

EventLoop是抽象类,定义了Worker和Boss共有的一些逻辑方法

Boss接口定义boss特有的channel注册的方法

BossEventLoop线程主要负责监听可读事件,事件就绪后将任务传递给worker线程进行处理

Worker接口定义worker特有的channel注册的方法

WorkerEventLoop线程主要负责处理业务逻辑,处理完成后,写回channel

EventLoopProvider 线程提供者,充当上下文作用,主要是提供各个线程的引用

Worker执行业务时,如果有耗时操作,需要开辟业务线程,当处理完成时,需要write回channel,这里的writeOutBuffer()方法需要做一个判断,看
是否是io线程,如果不是的话,需要包装成一个任务放入队列.保证一个channel的IO操作实在一个线程内串行执行.

 