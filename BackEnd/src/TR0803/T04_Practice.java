package TR0803;

class OddThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                System.out.println(i+"----"+Thread.currentThread().getName());
            }
        }
    }
}

class EvenThread implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i % 2 != 0) {
                System.out.println(i+"----"+Thread.currentThread().getName());
            }
        }
    }
}

public class T04_Practice {
    public static void main(String[] args) {
        //1.打印奇数
        new OddThread().start();
        //2.打印偶数
        new Thread(new EvenThread()).start();

        //3.匿名类的匿名对象
        new Thread(new Runnable() {
            @Override
            public void run() {
//                while (true) {
//                    System.out.println("吸烟喝酒烫头");
//                }
                for (int i = 0; i < 100; i++) {
                    System.out.println("吸烟喝酒烫头"+"----"+Thread.currentThread().getName());
                }
            }
        }).start();
        //4. jdk8的写法,Lambda表达式,(关于jdk8还讲过一个接口里的静态方法)
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println("追小姐姐"+"----"+Thread.currentThread().getName());
            }
        }).start();
    }
}
