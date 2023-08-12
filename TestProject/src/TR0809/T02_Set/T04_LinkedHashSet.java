package TR0809.T02_Set;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T04_LinkedHashSet.java
 * @Description TODO
 * @createTime 2023年08月09日 13:07:00
 *
 * LinkedHashSet是HashSet的子类,可以确保存取顺序
 * 原理是双向链表
 */
public class T04_LinkedHashSet {
    public static void main(String[] args) {
        Set set = new LinkedHashSet();
        set.add("石原里美");
        set.add(10);
        set.add(new Object());
        set.add(10);//不会报错,但也加不进去,用的是equal+hashCode两个方法实现的
        set.add(new Person("AA",25));
        set.add(new Person("BB",25));//重写equals方法
        System.out.println(set);
    }
}
