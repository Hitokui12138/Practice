package TR0804;

import java.util.concurrent.*;

/**
 * 注意上面的juc包,新的线程池类
 */
class MyRunnable implements Runnable{
    @Override
    public void run() {
        int sum = 0;
        for (int i = 0; i < 20; i++) {
            sum+=i;
        }
        System.out.println("Runnable接口:"+sum);
    }
}

class MyCallAble implements Callable{
    @Override
    public Object call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum+=i;
        }
        System.out.println("Callable接口:"+sum);
        return null;
    }
}
public class T2_ExecutorsTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*
            1.线程池练习
         */
        //ExecutorService executorService = Executors.newSingleThreadExecutor();//创建单个线程的线程池
        //ExecutorService executorService = Executors.newFixedThreadPool(10);   //java.util.concurrent.ThreadPoolExecutor
        //ExecutorService里没有这些方法,因此利用多态向下转型,转成具体实现类后再使用下面这些方法
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);//原本是个工厂类,这种情况下用getClass()看一下
        executorService.setMaximumPoolSize(20);//最大线程数
        executorService.setCorePoolSize(10);//默认线程数
        executorService.setKeepAliveTime(20000, TimeUnit.SECONDS);//线程没有任务时最多保持多少时间后中止


        System.out.println("具体是哪个类:"+executorService.getClass().getName());

        //Runnable接口使用的方法
        executorService.execute(new MyRunnable());
        //Callable接口使用的方法
        Future future= executorService.submit(new MyCallAble());
        Object o = future.get();

        executorService.shutdown();//销毁线程池
    }
}
