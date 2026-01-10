package TR0809_集合框架.T05_Map;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T01_Map.java
 * @Description TODO
 * @createTime 2023年08月09日 14:12:00
 *
 * Map和Collection是并列关系
 * Map是双列数据,使用键值对(Entry结构,也是Set结构),key和value都可以存放引用类型
 * key的存储方式是Set,不能重复,不过一般都是String类型的
 * Value则是Collection,无序且不唯一
 * 有点像抛物线y=f(x)
 *
 * HashMap(jdk1.2) 线程不安全(好像1.0的线程安全的多)<-LinkedHashMap(jdk1.4)
 * HashTable<-properties
 * TreeMap
 *
 * HashMap用的最多
 * 线程不安全,效率高
 */
public class T01_Map {
    /*
    增删改查
    1.Object put(k,v)既可以添加也可以修改
    2.void putAll(map)

    3.Object remove(k) 成功的话会返回Value
    4.void clear() 清空map

    5.Object get(k)
    6.boolean containsKey(k)
    7.boolean containsValue(v)

    8.size(),isEmpty()
     */
    @Test
    public void test1(){
        Map<String,String> map1 = new HashMap<>();
        map1.put("石原里美", "Yultoki");
        map1.put("新桓结衣", "星野源");
        map1.put("郭靖", "黄蓉");
        map1.put("谢霆锋", "张柏芝");
        map1.put("谢霆锋", "王菲");//key相同,覆盖掉前面的Value
        System.out.println(map1);

        Map<String,String> map2 = new HashMap<>();
        map2.put("AAA", "BBB");
        map2.put("CCC", "DDD");

        map1.putAll(map2);
        System.out.println(map1);

        System.out.println(map1.remove("AAA"));//删除成功后返回value
        System.out.println(map1.remove("BBB"));//删除失败返回null

        //map1.clear();
        System.out.println(map1.get("CCC"));
        System.out.println(map1.containsKey("CCC"));
        System.out.println(map1.containsValue("CCC"));
        System.out.println(map1.size());
    }

    /*
    遍历
    1.单独取得Key或Value集合
    2.Map.Entry<String,String> 这个是个Set,不能确保顺序
     */
    @Test
    public void test2(){
        Map<String,String> map1 = new HashMap<>();
        map1.put("石原里美", "Yultoki");
        map1.put("新桓结衣", "星野源");
        map1.put("郭靖", "黄蓉");
        map1.put("谢霆锋", "张柏芝");

        //拿到key的集合,value的集合
        Set<String> keys = map1.keySet();
        for(String s: keys){
            System.out.println(s);
        }
        Collection<String> values = map1.values();
        for(String s:values){
            System.out.println(s);
        }
        //拿到Entry<K,V> Set集合
        Set<Map.Entry<String,String>> entries = map1.entrySet();
        for(Map.Entry<String,String> entry : entries){
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }

    }
}
