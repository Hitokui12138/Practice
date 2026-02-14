package TR0811_IO框架2.zhoukao;

import org.junit.jupiter.api.Test;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T1.java
 * @Description TODO
 * @createTime 2023年08月11日 14:12:00
 */
public class T1 {
    @Test
    public void Test() {
        Ract r1 = new Ract(3.5, 4.5);
        Ract r2 = new Ract(3.5, 5.5);
        System.out.println(r1.area());
        System.out.println(r2.perimeter());
    }
}

class Ract {
    private double width;
    private double height;

    public Ract(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public Ract() {
    }

    public double area() {
        return width * height;
    }

    public double perimeter() {
        return 2 * (width + height);
    }
}
