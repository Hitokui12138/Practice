package TR0809_集合框架.T06_TreeMap;

import org.junit.jupiter.api.Test;

import java.util.TreeMap;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T01_TreeMap.java
 * @Description TODO
 * @createTime 2023年08月09日 16:07:00
 */
public class T01_TreeMap {
    @Test
    public void test(){
        TreeMap<String,Integer> map = new TreeMap<>();
        map.put("ww", 1);
        map.put("aa", 1);
        map.put("ss", 1);
        map.put("dd", 1);
        map.put("cc", 2);
        map.put("cc", 3);//注意对于Map来说Value会被覆盖的
        //会自动根据与Key排序
        System.out.println(map);
    }
}
