package TR0803_多线程.T13_Lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 票数的问题解决了,但是有重票的问题
 * 原因是多个线程一起操作
 */
public class TicketRunnable {
    public static void main(String[] args) {
        Window2 w2 = new Window2();
        Thread t1 = new Thread(w2,"窗口1");//这三个Thread共享w2实例里面的数据
        Thread t2 = new Thread(w2,"窗口2");
        Thread t3 = new Thread(w2,"窗口3");
        t1.start();
        t2.start();
        t3.start();
    }
}

class Window2 implements Runnable {
    private int num = 50;//如果使用Runnable就不需要static了,因为一个r可以建多个t1,t2,t3
    //alt insert
    private ReentrantLock lock = new ReentrantLock();//static时class,Runnable不需要static,要加个true?加true的话按顺序执行线程

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                if (num > 0) {
                    System.out.println(Thread.currentThread().getName() + ":第" + num + "张");
                    num--;
                } else {
                    System.out.println("售罄");
                    break;
                }
            } finally {
                lock.unlock();
            }

        }
    }
}