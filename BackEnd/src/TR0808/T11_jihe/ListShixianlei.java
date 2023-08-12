package TR0808.T11_jihe;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 *    1. List 有序,可重复,类似于数组
 *        1. ArrayList jdk1.2
 *        2. LinkedList
 *        3. Vector
 */
public class ListShixianlei {
    public static void main(String[] args) {
        /*
        1.ArrayList
        没有同步方法,线程不安全
        添加和删除效率低,不适合频繁添加和删除,因为是数组(连续空间),遍历很快
        扩容问题,StringBuilder默认是16,
        1.1 jdk7的情况下
        默认创建长度为10的数组
        ensureCapacityInternal(),StringBulder是*2,这个是old+old/2,也就是1.5倍,然后用copy方法做一个新集合并返回
        copy的效率很低,加入有10000000万个元素,想copy必须一一copy
        1.2 8的情况下
        默认先创建空数组,第一次add的时候创建长度为10的数组,后面就和1.7一样了
        这是为了考虑new一个数组后不使用的情况,相当于懒汉模式,延迟了数组的的创建,从而节省空间
        copy的效率还是很低
         */
        ArrayList arrayList1 = new ArrayList();//默认容量为10,底层还是一个数组
        ArrayList arrayList2 = new ArrayList(20);//指定长度的构造器
        arrayList1.add(1);

        //删除的效率也不高
        //每一个都要往前移动一位
    }
}
