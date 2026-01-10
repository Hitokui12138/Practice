package TR0809_集合框架;

import java.util.*;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T07_Collections.java
 * @Description TODO
 * @createTime 2023年08月09日 16:12:00
 *
 * 工具类
 * 提供了Set,List,Map等集合的工具类
 */
public class T07_Collections {
    /*
    reverse(List)
    shuffle(List)
     */
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        System.out.println(list);
        Collections.reverse(list);
        System.out.println(list);
        Collections.shuffle(list);
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
        //降序需要自己写一下
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -o1.compareTo(o2);
            }
        });
        System.out.println(list);
        Collections.swap(list,0,4);
        System.out.println(list);
        System.out.println(Collections.max(list));//5

        //求某个元素的个数
        list.add(5);
        list.add(5);
        System.out.println(Collections.frequency(list, 5));//3次

        //替换
        Collections.replaceAll(list,5 , 6);
        System.out.println(list);

        //集合的复制
        ArrayList<Integer> list2 = new ArrayList<>();
        //Collections.copy(list2, list);//java.lang.IndexOutOfBoundsException,jdk8默认长度为空,不能直接copy
        List<Integer> list3 = Arrays.asList(new Integer[list.size()]);//要先确保长度一样
        Collections.copy(list3, list);
    }
}
