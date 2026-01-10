package TR0804_多线程2String.zuoye;
class MyThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {
            System.out.println(i);
        }
    }
}
public class ThreadTest {
    /*
    1.extends Thread
    2.Override run()
    3.new Constructor().run()
     */
    public static void main(String[] args) {
        new MyThread().run();
    }
}
