package TR0803_多线程;

/**
 * 1.Runnable接口 5步
 * 1.1 创建一个实现Runnable接口的类
 * 1.2 实现抽象方法run()
 * 1.3 创建实现类的对象
 * 1.4 将此对象作为参数创建Thread对象
 * 1.5 通过Thread对象调用start方法.
 *
 * 2. 优先选择这种方法
 * 2.1 没有单继承的局限性
 * 2.2 实现的方法更适合多个线程间共享数据,   因为一个r实例可以建多个t1,t2,t3,这样就自动共享数据了,也不需要static
 *
 * 3.相同点
 * 3.1 都需要重写run方法
 * 3.2 都需要用thread的start()方法来启动
 */

class MyRunnable implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+":"+i);
        }
    }
}

public class T03_Runnable {
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        thread.start();
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+":"+i);
        }
    }

}
