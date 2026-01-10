package TR0811_IO框架2.zhoukao;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T4.java
 * @Description TODO
 * @createTime 2023年08月11日 15:00:00
 */
public class T4 {
    public static void main(String[] args) {
        Light l1 = new Light(3, "红");
        Light l2 = new Light(2, "绿");
        Light l3 = new Light(1, "黄");
        new Thread(l1).start();
        new Thread(l2).start();
        new Thread(l3).start();
    }

}

class Light extends Thread {
    static int status = 0;
    int time;
    String color;

    public Light(int time, String color) {
        this.time = time;
        this.color = color;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (Light.class) {
                Light.class.notifyAll();
                if (status == 0 && color == "红") {
                    System.out.println("红");
                    for (int i = time; i > 0; i--) {
                        System.out.println(i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    status = 1;
                    try {
                        Light.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (status == 1 && color == "绿") {
                    System.out.println("绿");
                    for (int i = time; i > 0; i--) {
                        System.out.println(i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    status = 2;
                    try {
                        Light.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (status == 2 && color == "黄") {
                    System.out.println("黄");
                    for (int i = time; i > 0; i--) {
                        System.out.println(i);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    status = 0;
                    try {
                        Light.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
