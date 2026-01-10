package TR0808_范型枚举注解.T11_jihe;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * List除了Collection的三种方法,还多了一些方法,和StringBuffer很类似
 * 1.增加的基础上多了插入操作
 * add(index,o),addAll(index,o)
 *
 * 2.获取
 * get(index)
 *
 * 3.因为List是有序可重复的,而且有get(index),因此可以使用普通的for循环
 *
 * 4,修改
 * set(index,o)
 *
 * 5.删除
 * remove(index)
 * remove(2)是删除index还是删2的问题,因为重载,因此会删index2
 * remove(new Integer(2))//换成包装类就能删了
 *
 * 6.几个和Index有关的方法
 * indexOf()查找位置,从前往后找
 * indexOfLast() 从后往前找
 * c.subList(0,2) 左闭右开,取得子串
 */
public class ListTest {
    @Test
    public void test(){
        List c = new ArrayList();
        c.add("123");
        c.add(10);
        c.add(new Object());

        c.add(2,new Object());

        List c1 = new ArrayList();
        c1.add("456");
        c.addAll(3,c1);


        /*
        List可以直接遍历,因为有size和get(i)方法
         */
        for (int i = 0; i < c.size(); i++) {
            System.out.println(c.get(i));
        }
    }
}
