package TR0809_集合框架.T05_Map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T02_HashMapDemo.java
 * @Description TODO
 * @createTime 2023年08月09日 15:55:00
 *
 * 统计字符串的字符个数
 */
public class T02_HashMapDemo {
    public static void main(String[] args) {
        String str = "asbfiuadbfknflkewnfkjsdniufchdslifhqkwnd";
        HashMap<Character,Integer> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char temp = str.charAt(i);
            Integer v = map.get(temp);
            if(v == null){
                map.put(temp, 1);
            }else{
                map.put(temp,++v);//注意要先++
            }
        }

        Set<Map.Entry<Character,Integer>> entries = map.entrySet();
        for(Map.Entry<Character,Integer> entry : entries){
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
    }
}
