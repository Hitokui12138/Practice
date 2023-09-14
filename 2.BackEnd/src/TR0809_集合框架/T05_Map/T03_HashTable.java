package TR0809_集合框架.T05_Map;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T03_HashTable.java
 * @Description TODO
 * @createTime 2023年08月09日 15:35:00
 *
 *
 * HashTable jdk1.0 线程安全
 *
 * 区别:
 * 1. 诞生版本不同,线程安全性,效率
 * 2. HashTable的Key和Value都不能为null,HashMap没有这些限制
 *
 * 主要用它的子类properties
 * Key和Value都只能是String,用来处理属性文件
 * 存取使用setProperties(k,v)和getProperties(k)
 *
 */
public class T03_HashTable {
    @Test
    public void test1() throws IOException {
        /*
        创建一个Resource Bundle文件
         */
        Properties prop = new Properties();
        prop.load(new FileInputStream("C:/Users/Admin/IdeaProjects/TestProject/src/TR0809/T05_Map/jdbc.properties"));
        String username = prop.getProperty("username");
        System.out.println(username);
        String password = prop.getProperty("password");
        System.out.println(username);
    }
}
