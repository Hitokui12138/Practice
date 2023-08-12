package TR0803.T09_ticketTest;

/**
 * 票数的问题解决了,但是有重票的问题
 * 原因是多个线程一起操作
 */
public class TicketRunnable {
    public static void main(String[] args) {
        Window2 w2 = new Window2();
        Thread t1 = new Thread(w2);//这三个Thread共享w2实例里面的数据
        Thread t2 = new Thread(w2);
        Thread t3 = new Thread(w2);
        t1.start();
        t2.start();
        t3.start();
    }
}

class Window2 implements Runnable {
    private int num = 50;//如果使用Runnable就不需要static了,因为一个r可以建多个t1,t2,t3
    //alt insert

    @Override
    public void run() {
        while (true) {
            if (num > 0) {
                System.out.println(Thread.currentThread().getName()+":第" + num + "张");
                num--;
            } else {
                System.out.println("售罄");
                break;
            }
        }
    }
}