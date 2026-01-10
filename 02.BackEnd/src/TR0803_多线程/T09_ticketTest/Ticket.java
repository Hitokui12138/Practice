package TR0803_多线程.T09_ticketTest;

public class Ticket {
    public static void main(String[] args) {
        Window w1 = new Window("窗口1");
        Window w2 = new Window("窗口2");
        Window w3 = new Window("窗口3");

        w1.start();
        w2.start();
        w3.start();
    }
}

class Window extends Thread {
    private static int num = 50;//如果使用Runnable就不需要static了,因为一个r可以建多个t1,t2,t3
    //alt insert

    public Window(String name) {
        super(name);
    }

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
