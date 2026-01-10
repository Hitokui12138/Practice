package TR0804_多线程2String;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Runable的缺点:
 * run方法没有返回值
 * 不支持泛型
 * 不能抛出异常
 *
 * jdk1.5后提供的方法
 * 1.Callable接口
 * 1.1 Future FutureTask
 *
 * 2.线程池(线程的集合,里面创建的线程是可以重用的)
 * ExecutorService(线程池接口),Executors(类)
 * void executor(Runnable)
 * <V>Future<V> submit(CallAble)
 * 一些线程池工厂类
 * 1
 * 2.
 * 3
 * 4
 *
 * 2.1 每次新建线程开销很大,因此
 * 2.2 一开始的时候创建很多线程放进池子里,用完也不销毁
 * 2.3 便于县城管理:corePoolSize,maxminPoolSize,KeepAliveTime
 * //早期的时候商品图片一个图片一个线程,可能会导致内存崩溃,每次都去new一个线程不好
 */
class MyCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return sum;
    }
}
public class T01_jdk5 {
    public static void main(String[] args) {
        MyCallable myCallable=new MyCallable();
        FutureTask<Integer> futureTask = new FutureTask<>(myCallable);//封装接口对象
        Thread thread1 = new Thread(futureTask);
        Thread thread2 = new Thread(futureTask);
        thread1.start();//执行线程
        thread2.start();

        try {
            Integer o =futureTask.get();    //获取结果
            System.out.println(o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
