package TR0803;

/**
 * 1. 线程的生命周期,5个状态 图背一下
 * 1.1 新建 new Thread()
 * 1.2 就绪 start()
 * 1.3 运行 获取CPU执行权或者yield()失去执行权
 * 1.4 阻塞 sleep(),join(),等待同步锁,wait(),suspend();
 * sleep(),获得同步锁,notify(),resume()
 * 1.5 死亡 执行完run(),stop(),出现Error或Exception
 *
 * 2.每个线程都有自己的栈,但共享堆空间,对堆空间进行操作时可能会遇到同步问题
 * 2.1
 */
public class T08_Life {

}
