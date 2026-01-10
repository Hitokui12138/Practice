package TR0809_集合框架.T03_TreeSet;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T01_TreeSetTest.java
 * @Description TODO
 * @createTime 2023年08月09日 13:05:00
 *
 * TreeSet 红黑二叉树,可以排序,查询,比Array的sort快很多
 * 注意去重的方法不是equals(),而是下面两种,因此里面的对象也必须实现compareTo()或者compare()方法
 * 1. 自然排序:Comparable,compareTo()
 * 2. 定制排序:ComparatorTest,compare()
 *
 * 规则
 * 1. a>b 正数
 * 2. a<b 负数
 * 3. a=b 返回0,但是特殊:因为是重复元素,Set不允许存储重复元素
 *
 *
 * 红黑二叉树: 一个接点只能有两个子节点
 * 小的存左边,大的存右边
 * 里面也会自动优化,比如三个接点都在左边的话,会优化成左右结构
 *
 *
 * 遍历可以使用
 * 增强for或者迭代器
 */
public class T01_TreeSetTest {
    @Test
    public void test1(){
        /*
        字符串和包装类实现了Comparable接口,加进去后就会自动排序
         */
        TreeSet<String> t1 = new TreeSet<>();
        t1.add("ww");
        t1.add("aa");
        t1.add("zz");
        t1.add("uu");
        System.out.println(t1);//[aa, uu, ww, zz]
    }

    @Test
    public void test2(){
        TreeSet<Goods> t1 = new TreeSet<>();
        /*
        java.lang.ClassCastException
        不是先
         */
        t1.add(new Goods("xiaomi", 20));
        t1.add(new Goods("lenovo", 16));
        t1.add(new Goods("logitech", 24));
        t1.add(new Goods("iphone", 12));
        t1.add(new Goods("iphone", 8));
        t1.add(new Goods("iphone", 4));
        System.out.println(t1.add(new Goods("rapoo", 8)));//false,因为compareTo结果为0,视作相同元素,无法加入

        System.out.println(t1);


        //定制排序,已经重载过了,可以直接写
        TreeSet<Goods> t2 = new TreeSet<>(new Comparator<Goods>() {
            @Override
            public int compare(Goods o1, Goods o2) {
                return -o1.name.compareTo(o2.name);
            }
        });
    }
}

class Goods implements Comparable{
     String name;
     int price;

    public Goods(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Goods g = (Goods) o;
        System.out.println(g.price +"=="+g.price);
        return Double.compare(this.price,g.price);
    }
}