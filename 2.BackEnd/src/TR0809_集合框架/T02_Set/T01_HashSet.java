package TR0809_集合框架.T02_Set;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T01_HashSet.java
 * @Description TODO
 * @createTime 2023年08月09日 10:50:00
 *
 * Set:无序性,不可重复性
 * 散列:存储元素的时候不是按照下标位置存储的
 * 每个元素都有一个hash值,再根据hashCode值来计算index,
 * 默认长度为16,延长方法与hashmap有关
 *
 * 三种可以加入的情况:(去重的原理)
 * A.比如hash值为10000,数组长度为16,最简单的方法:10000%16 得到一个0~15的值
 * B.假如(a)算出来是5,而5这个位置已经有其他值(b)了,首先判断两个hashCode值是否相同,不一样的话也可以放进去的
 * 方法是在5这个位置生成一个链表来存储两个元素
 * jdk7,b<-a, 把a放入数组,指向原来的b,七上
 * jdk8,a<-b, 原来的数b指向a(把这个位置变成a,b放到a的下面),八下
 *
 * C.但是如果hashCode相同,需要调用a的equals方法
 *  如果是true,添加失败
 *  如果是false,还是可以添加
 *
 *  所以要想办法让不同对象的hashCode不一样
 */
//多用ctrl+H看看
public class T01_HashSet {
    @Test
    public void test(){
        Set set = new HashSet();
        set.add("石原里美");
        set.add(10);
        set.add(new Object());
        set.add(10);//不会报错,但也加不进去,用的是equal+hashCode两个方法实现的
        set.add(new Person("AA",25));
        set.add(new Person("BB",25));//重写equals方法
        System.out.println(set);//打印出来是无序不重复的
        /*
        无序!=随机
        底层数组不按照index来保存,而是通过
         */

    }
}
class Person{
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        return name != null ? name.equals(person.name) : person.name == null;
    }

    /*
    return name.hashCode+age;//可能会重复
    通过放大其中一个数,再加上另一个数的方法来避免重复
    为什么是31?
    1.尽可能大的系数(地址越大,冲突可能性越小)
    2.31占5bit(2^5=32),相乘后超出可能性小,虚拟机也有针对的优化
    3.17,31是个素数
     */
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }
}
