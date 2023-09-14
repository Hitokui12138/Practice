package TR0803_多线程.T14_tongxun;

/**
 * 1.线程的通讯(注意只能在同步代码块和同步方法中使用)
 * 调用者必须是同步监视器
 * 都是定义在Object里面的
 * 1.1 wait()
 * 1.2 notify(),notifyAll()
 *
 * 2.sleep
 * 都会进入阻塞
 * sleep是Thread的静态方法,wait是Object
 * sleep任何场景,wait必须是同步代码块和同步方法
 * sleep不会释放同步监视器,wait会释放同步监视器
 */
class Communication implements Runnable {
    int num = 1;

    /*
    交替打印
     */
    @Override
    public void run() {
        while (true) {
            synchronized (Communication.class){
                if (num <= 100) {
                    //进来之后唤醒其他线程池子里其他线程,注意这个池子和这个方法是当前这个锁的,而不是当前对象的
                    Communication.class.notify();//唤醒其中一个
                    System.out.println(Thread.currentThread().getName() + ":" + num);
                    num++;
                    //打印1之后停下来进入一个线程池,当前线程阻塞,并且释放锁(同步监视器),让第二个进程进来
                    try {
                        Communication.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
        }
    }
}


public class tongxun {
    public static void main(String[] args) {
        Communication c1 = new Communication();
        new Thread(c1, "线程1").start();
        new Thread(c1, "线程2").start();
    }
}
