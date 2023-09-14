package TR0804_多线程2String.zuoye;
class MyRunnable implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i <=100 ; i++) {
            System.out.println(i);
        }
    }
}
public class RunnableTest {
    /*
    1.implements Runnable
    2.Override run()
    3.new一个对象
    4.使用Thread的构造方法,再调用start
     */
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        new Thread(myRunnable).start();
    }
}
