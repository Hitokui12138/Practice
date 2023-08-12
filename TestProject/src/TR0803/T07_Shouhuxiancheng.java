package TR0803;

/**
 * 线程分为用户线程(默认)和守护线程
 * 唯一区别是JVM何时断开,守护线程服务于用户线程
 * 在start之前使用thread.setDaemon(true)来把一个用户线程变成一个守护线程
 * Java垃圾回收就是一个守护线程
 * 若JVM都是守护线程时,当前JVM退出
 */
class DaemonThread implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("当前更新了"+i+"%");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
public class T07_Shouhuxiancheng {
    public static void main(String[] args) {
        DaemonThread daemonThread = new DaemonThread();
        Thread t1 = new Thread(daemonThread);
        t1.setDaemon(true);//这样主线程结束后,这个守护线程也会立即结束
        t1.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("程序结束");
    }
}

