package TR0803.T12_DeadLock;

/**
 * 不同的资源分别占用对方需要的同步资源不放弃,都在等待对方放弃自己的同步资源,形成了线程地死锁
 * 出现死锁后,没有异常也没有提示,嵌套加锁时可能会出现这种情况,只能尽量避免,优化代码
 */
public class DeadLock {
    public static void main(String[] args) {
        //必须要同时拿到电池和遥控器才能避免死锁
        //死锁不一定会发生
        new Thread(new Runnable(){
            @Override
            public void run() {
                //占用"遥控器",执行下面代码
                synchronized ("遥控器"){
                    //增大出错可能
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("有遥控器,去对面屋取电池");
                    //当且仅当"电池"没有被其他线程占用时,才能运行下面的方法
                    synchronized ("电池"){
                        System.out.println("把电池放入遥控器");
                    }
                }
            }
        }).start();


        new Thread(new Runnable(){
            @Override
            public void run() {
                synchronized ("电池"){
                    System.out.println("有电池,去对面屋取遥控器");
                    synchronized ("遥控器"){
                        System.out.println("把电池放入遥控器");
                    }
                }
            }
        }).start();
    }
}
