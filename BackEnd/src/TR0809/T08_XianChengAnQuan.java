package TR0809;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T08_XianChengAnQuan.java
 * @Description TODO
 * @createTime 2023年08月09日 16:30:00
 *
 * 线程安全的ArraysList
 * 不要使用Vector
 */
public class T08_XianChengAnQuan {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        list = Collections.synchronizedList(list);//给所有方法都套了个sychro(lock)
    }
}
