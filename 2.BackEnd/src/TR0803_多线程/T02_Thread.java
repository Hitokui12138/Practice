package TR0803_多线程;

/**
 * 线程的创建与使用
 * 1.方式(4种)
 * 1.1 继承Thread类(1.5之前的方法)
 * 1.2 实现Runnable接口(1.5之前的方法)
 * <p>
 * 2.Thread
 * 2.1 创建子类继承Thread
 * 2.2 重写run(),在方法体里写要执行的东西
 * 2.3 创建子类对象
 * 2.4 用子类对象 调用start()方法,(这个方法会启动当前线程并调用run()方法)
 * 不能直接调用run(),因为会变成普通方法,不启动多线程
 */
/*
    1. 格式化:ctr alt L
    2. 在红线的后面alt+entry
    3. 查找类方法的快捷键
 */
public class T02_Thread {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        MyThread myThread2 = new MyThread();
        myThread.start();//一个线程对象的start方法只能调用一次,有个参数threadStatus在限制,使用多次会报错
        myThread2.start();//这样是可以的
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+"--"+i);
        }
    }
}

class MyThread extends Thread {
    @Override
    public void run(){
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName()+"--"+i);
        }
    }
}
