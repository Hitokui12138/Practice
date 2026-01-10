package TR0103_Lambda.T01_Runnable;

public class RunnableTest {
    public static void main(String[] args) {

        //A. Thread(实现Runnable接口的对象)
        //1.用MyRunnable实现Runnable接口
        MyRunnable myRunnable = new MyRunnable();
        //2.使用实现类对象来new多个Thread对象
        Thread thread1 = new Thread(myRunnable);
        //3. 使用start启动
        thread1.start();

        //B. 使用匿名内部类简化这个操作
        new Thread(new Runnable(){
            @Override
            public void run(){
                System.out.println("匿名类");
            }
        }).start();

        //C. 使用Lambda表达式简化
        new Thread(()->{
            System.out.println("lambda表达式");
        }).start();
    }
}
