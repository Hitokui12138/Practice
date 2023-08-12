package TR0803;

public class T16_TrafficLight {
    static int status = 0;
    public static void main(String[] args) {
        Object o = new Object();
        new Thread(()->{
            while(true){
                synchronized (o){
                    o.notifyAll();
                    if(status == 0){
                        System.out.println("绿");
                        for (int i = 5; i > 0; i--) {
                            System.out.println(i);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        status = 1;
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        new Thread(()->{
            while(true){
                synchronized (o){
                    o.notifyAll();
                    if(status == 1){
                        System.out.println("黄");
                        for (int i = 5; i > 0; i--) {
                            System.out.println(i);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        status = 2;
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        new Thread(()->{
            while(true){
                synchronized (o){
                    o.notifyAll();
                    if(status == 2){
                        System.out.println("红");
                        for (int i = 5; i > 0; i--) {
                            System.out.println(i);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        status = 0;
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
