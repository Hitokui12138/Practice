package TR0803_多线程.T10_Tongbu;


/**
 * 1.同步的方法:
 * 1.1. 同步代码块
 * 1.2. 同步方法
 * 1.3. JDK1.5之后地方法: 手动添加锁 Lock
 * <p>
 * 2.synchronized(同步监视器){//需要同步的代码}
 * 2.1 操作共享数据的代码,即需要同步的代码,不能多也不能少(while里面)
 * 2.2 共享数据
 * 2.3 同步监视器,也称为锁,可以是任意对象,但多个线程要共有一把锁
 * <p>
 * 3. 可以用this当锁吗?,extend Thread的就不行,因为有三个对象
 * 推荐使用当前类 WindowsTest.class 当前类当作锁
 * <p>
 * 4.利弊
 * 1.同步的方式解决了多线程安全问题
 * 操作同步代码时,只能一个线程参与,相当于变成了单线程,效率低
 */
public class T10_Sync {
    public static void main(String[] args) {
        WindowsTest w2 = new WindowsTest();
        Thread t1 = new Thread(w2, "窗口1");//这三个Thread共享w2实例里面的数据
        Thread t2 = new Thread(w2, "窗口2");
        Thread t3 = new Thread(w2, "窗口3");
        t1.start();
        t2.start();
        t3.start();
    }
}

class WindowsTest implements Runnable {
    private int num = 50;//如果使用Runnable就不需要static了,因为一个r可以建多个t1,t2,t3
    //alt insert
    //private Object o = new Object();


    //1.同步代码块
//    @Override
//    public void run() {
//        while (true) {
//            synchronized (WindowsTest.class) {
//                if (num > 0) {
//                    try {
//                        Thread.sleep(50);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(Thread.currentThread().getName() + ":第" + num + "张");
//                    num--;
//                } else {
//                    System.out.println("售罄");
//                    break;
//                }
//            }
//        }
//    }

    //2.同步方法,普通的同步方法的锁是this,加上static的话锁是当前类,不需要显式地写锁
    //同步方法用的会多一些
    public synchronized void sale() {
        if (num > 0) {
            System.out.println(Thread.currentThread().getName() + ":第" + num + "张");
            num--;
        }
    }

    @Override
    public void run() {
        while (this.num > 0) {//注意while一定不要放进同步方法里面
            sale();
        }
    }
}
