package TR0809;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T10_zuoye.java
 * @Description TODO
 * @createTime 2023年08月11日 08:05:00
 */
public class T10_zuoye {
    @Test
    public void test1(){
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        //1.Iterator
        Iterator i= list.listIterator();
        while (i.hasNext()){
            System.out.println(i.next());
        }
        //2.增强型for
        for(String s: list){
            System.out.println(s);
        }
    }
    @Test
    public void test2(){
        Map<String,String> map = new HashMap<>();
        map.put("石原里美", "Yultoki");
        map.put("新桓结衣", "星野源");
        List<String> list = new ArrayList<>();
        Collection<String> c = map.values();
        for(String s: c){
            list.add(s);
        }
    }
}
