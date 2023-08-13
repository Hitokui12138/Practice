package TR0803_多线程;

/**
 * start()
 * run()
 * currentThread()
 * getName()
 * setName(),用的不多,可以用构造函数创建时直接设置name
 * yield(),释放cpu的执行权,让别的线程执行(但也可能会立即再次抢到执行权)
 * b.start();b.join();,在当前线程中加入b线程,需要等b线程跑完,当前线程才能继续跑,当前线程处于阻塞状态,InterruptedException
 * stop(),已过时,停止线程
 * sleep(long millstime),休眠,当前线程处于阻塞状态,但要注意这个方法是一个静态方法,主要看在哪里调用,而不是看谁调用,会使当前线程休眠
 * isAlive(),判断run()函数是否跑完,可以和stop结合使用
 * setPriority(),0~10,设置优先级只是提高概率,不是一定会优先执行,因此没什么用
 */
public class T05_changyongfangfa {
    public static void main(String[] args) throws InterruptedException {
        TestThread r = new TestThread();
        Thread t = new Thread(r);
        for (int i = 0; i < 20; i++) {
            System.out.println(i + "----" + Thread.currentThread().getName());
            if(i==5){
                t.start();
                t.join();//这样看来join的功能就是阻塞当前线程,然后让t先运行
            }
        }
    }
}

class TestThread implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println(i + "----" + Thread.currentThread().getName());
        }
    }
}
