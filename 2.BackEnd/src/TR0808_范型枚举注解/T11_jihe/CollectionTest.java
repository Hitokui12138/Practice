package TR0808_范型枚举注解.T11_jihe;



import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * 增:add(),addAll(),clear()
 * 查:contains(),containsAll()
 * 删:remove(),
 *
 * retainAll()取交集,equals()
 *
 * 集合转数组: toArray()
 * 数组转集合: Arrays.asList()静态方法
 *
 *  迭代器
 *  所有容器都可以使用的迭代器,是一种设计模式    (单例,模板,MVC,工厂)
 *  col.iterator()方法新建一个该集合的迭代器,指针指向第一个元素的前面
 *  1. hasNext()
 *  2. next(),并且返回下一个元素,指针往后移动一位
 *  3. remove(),注意迭代器遍历时的remove()和集合的remove()不要混着用,因为迭代器抛出CurrentModifyException异常,
 *  不能在遍历的时候使用集合的删除
 *  迭代器只能用一次,不能用两次
 *  第二次再想用的话只能重新再获取一遍
 *
 *  JDK1.5提供了一个foreach循环,既可以遍历数组又可以遍历集合
 *  for(Object o:c),但注意取出来的o是个
 *
 * 集合的四种循环
 * 1.转数组,再循环
 * 2.迭代器
 * 3.直接使用增强型for循环 for(Character c : set)
 * 4. set.forEach(System.out::print);//jdk8方法引用
 */

public class CollectionTest {
    @Test
    public void test(){
        /*
        增
         */
        Collection c = new ArrayList();
        //add
        c.add("123");
        c.add(10);
        c.add(new Object());
        c.add('c');

        Collection c1 = new ArrayList();
        c1.add("abc");

        c.addAll(c1);//这个方法可以把元素一个一个加进去
        System.out.println(c);
        //c.clear();;
        System.out.println(c);

        /*
        查
         */
        //contains(),注意自己写的类需要重写equals()方法
        System.out.println(c.contains("abc"));//true,因为String的equals已经重写过了
        c.add(new Person("c",12));
        System.out.println(c.contains(new Person("c", 12)));//true
        //containsAll(),看后者是不是前者的子集
        System.out.println(c.containsAll(c));

        /*
        删
         */
        System.out.println(c.remove(10));

        /*

         */
        //集合转数组 toArray()
        Object[] o = c.toArray();
        System.out.println(Arrays.toString(o));
        //数组转集合 asList()
        List<int[]> integers = Arrays.asList(new int[]{1,2,3});//基本数据类型看过一个整体
        List<Integer> integers2 = Arrays.asList(new Integer[]{1, 2, 3});//会拆成三个对象,因此在这里面最好不要使用基本数据类型
        System.out.println(integers);

        /*
        Iterator 的遍历
         */
        System.out.println(c);
        Iterator it = c.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }

        /*
        Iterator用来不方便,因此jdk1.5提供一个foreach,增强型for循环
        数组集合都能用
        底层用的还是迭代器,相当于一个语法糖,是iterator的简写
         */
        //c.for
        for(Object x:c){
            System.out.println(x);
        }
        //注意取得的是值,修改的话并不能影响原来的集合

    }
}
class Person{
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (age != person.age) return false;
        return name != null ? name.equals(person.name) : person.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }
}
