package TR0811.T03_对象流;

import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T01.java
 * @Description TODO
 * @createTime 2023年08月11日 10:27:00
 *
 *
 *  把java对象存到硬盘里,或者在网络中传输,则需要先序列化,转化成二进制流
 * 对象->序列化->保存到文件里
 *
 * 不能转化static和
 *
 * 序列化机制
 * 1.必须实现两个接口之一
 *
 *
 * 反序列化
 *
 *
 * 序列化号
 * 每个类都会根据属性生成一个序列化号,SerializalID
 * 因此如果修改属性,就会导致序列化号改变,从而反序列化失败
 *
 * 因此需要手动给它一个序列化号
 */
public class T01 {
    @Test
    public void test1() throws IOException {
        Person p = new Person("石原里美", 20);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Test.txt"));
        oos.writeInt(10);//OK
        oos.writeObject(p);//java.io.NotSerializableException,因此要实现Serializable

        oos.close();
    }

    /*
    反序列化
     */
    @Test
    public void test2() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Test.txt"));
        System.out.println(ois.readInt());
        System.out.println(ois.readObject());
        ois.close();
    }
}
class Person implements Serializable {
    //Alt + Entry
    //主要是反序列化时需要用到
    @Serial
    private static final long serialVersionUID = 1280473325756659577L;//必须手动给一个序列化号,不然修改后就专不回来了

    private String name;
    private int age;


    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
