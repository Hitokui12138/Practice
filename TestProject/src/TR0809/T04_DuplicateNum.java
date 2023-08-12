package TR0809;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T04_DuplicateNum.java
 * @Description TODO
 * @createTime 2023年08月09日 14:06:00
 */
public class T04_DuplicateNum {
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(new Integer(1));
        list.add(new Integer(2));
        list.add(new Integer(2));
        list.add(new Integer(4));
        list.add(new Integer(4));
        List list2 = duplicateList(list);
        for(Object integer:list2){
            System.out.println(integer);
        }
    }

    private static List duplicateList(List list) {
        HashSet set = new HashSet();
        set.addAll(list);//collection都能用的一个集合加一个集合的方法
        return new ArrayList(set);//ArrayList生成新的集合的方法
    }
}
