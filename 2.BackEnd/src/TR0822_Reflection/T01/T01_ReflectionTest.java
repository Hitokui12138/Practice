package TR0822_Reflection.T01;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.annotation.ElementType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T01_ReflectionTest.java
 * @Description TODO
 * @createTime 2023年08月22日 09:42:00
 */
class Person {
    private String name;
    public int age;

    public Person() {
    }

    private Person(String name) {
        this.name = name;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private String getNationName(String name) {
        return "你好" + name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

public class T01_ReflectionTest {

    @Test
    public void test() throws Exception {
        //一般的方法
        //Person p1 =new Person();

        //使用反射new一个对象
        Class<Person> aClass = Person.class; //获得class对象
        Constructor<Person> constructor = aClass.getConstructor(String.class, int.class); //获得class对象的构造器 不带declare的只能拿到父类和子类共有的方法,带declare的能拿到子类所有方法
        Person p = (Person) constructor.newInstance("石原里美", 25);
        System.out.println(p);


        //使用反射修改对象参数
        Field field = aClass.getDeclaredField("age");
        field.set(p, 30);
        System.out.println(p.age);

        //使用反射调用方法
        Method method = aClass.getDeclaredMethod("getName");
        System.out.println(method.invoke(p));


        //以前不能调用私有构造器,现在可以了,私有属性也可以改了
        //Constructor<Person> privateCon = aClass.getConstructor(String.class); //获得class对象的私有构造器, NoSuchMethodException
        Constructor<Person> privateCon = aClass.getDeclaredConstructor(String.class);//上面的方法只能获得共有构造器,需要换成getDeclaredConstructor这个方法
        privateCon.setAccessible(true);//提高访问权限
        Person p2 = (Person) privateCon.newInstance("石原里美");
        System.out.println(p2);

        //使用反射修改私有对象
        Field field2 = aClass.getDeclaredField("name");
        field2.setAccessible(true);
        field2.set(p2, "新桓结衣");
        System.out.println(p2); //不能p2.name

//        //使用反射调用私有方法 NoSuchMethodException
//        Method method2 = aClass.getDeclaredMethod("getNationName");
//        method2.setAccessible(true);
//        System.out.println(method2.invoke(p2,"星野源"));

    }

    /*
        获取对象的四种方式
     */
    @Test
    public void test2() throws Exception {
        //方式一 无参构造器
        Class<Person> aClass1 = Person.class;
        Person person1 = aClass1.getConstructor().newInstance();

        //方式2 通过一个对象获取Class对象,用的比较少,本来目的就是要去造对象
        Person p = new Person();
        Class<? extends Person> aClass2 = p.getClass();
        System.out.println(aClass1 == aClass2);//结果为true,其实是同一个对象,一个类的CLASS对象只有一个
        Person person2 = aClass2.getConstructor().newInstance();

        //方式3 Class有个静态方法Class.forName(全路径),用的最多,推荐
        Class<?> aClass3 = Class.forName("TR0822_Reflection.T01.Person");
        Person person3 = (Person) aClass3.getConstructor().newInstance();

        //方式4,Classloader,类加载器,最不推荐
        ClassLoader classLoader =  this.getClass().getClassLoader();
        Class<?> aClass4 = classLoader.loadClass("TR0822_Reflection.T01.Person");
        Person person4 = (Person) aClass4.getConstructor().newInstance();

        /*
        1与3的区别
        1. 必须先有Person才能通过编译,但如果这个类存在的话,可以直接用new
        3. 创建的时候即使没有Person也没关系,参数是一个字符串路径,编译时这个路径可以不存在
        将来是把类放进配置文件,然后运行时调用生成Class,配置文件里都是字符串,方法3在编译时不会报错
         */
    }

    /*
    那些元素有Class?
     */
    @Test
    public void Test2(){
        Class c1 = Object.class;
        Class c2 = Comparable.class;
        Class c3 = String[].class;
        Class c4 = int[][].class;
        Class c5 = ElementType.class;
        Class c6 = Override.class;
        Class c7 = int.class;
        Class c8 = void.class;
        Class c9 = Class.class;

        int[]  a  =  new  int[10];
        int[]  b  =  new  int[100];
        Class  c10  =  a.getClass();
        Class  c11  =  b.getClass();
        //  只要元素类型与维度一样，就是同一个Class
        System.out.println(c10  ==  c11); //true
    }


    /*
    获取类加载器
    调用一个类时,会发生什么?类的加载过程
    不止可以加载类
     */
    @Test
    public void Test3(){
        //系统类加载器
        ClassLoader classLoader = this.getClass().getClassLoader();
        System.out.println(classLoader);

        //扩展类加载器
        ClassLoader parent = classLoader.getParent();
        System.out.println(parent);

        //引导类加载器是拿不到的,因为是虚拟机专用的
        ClassLoader parent2 = parent.getParent();
        System.out.println(parent2);//null
    }

    /*
        资源文件应该放在src类路径下面
     */
    @Test
    public void Test4() throws IOException {
//        Properties prop = new Properties();
//        prop.load(new FileInputStream(""));
//        String username = prop.getProperty("username");
//        System.out.println(username);

        //开发时用classloader加载这个文件
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        Properties prop = new Properties();
        //prop.load(new FileInputStream(""));
        prop.load(is);
        String username = prop.getProperty("username");
        System.out.println(username);
    }
}
