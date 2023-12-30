## 自定义IOC容器
- 框架 = 设计模式 + xml/注解 + 反射
1. 之前的JDBC使用`class.forname("com.mysql.cj.jdbc.Driver")`(创建一个Class对象),来获取一个Driver对象
2. 不使用new,而是把依赖关系放到工厂类里面,在使用工厂类加载这个对象
     1. 传统`DAO <- Service <- Controller`有明确的依赖关系
     2. 建一个配置文件`beans.property`文件存储service和dao的`类路径`(默认叫ApplicationContext,这里就叫Beans吧)
          ```yml
          empDao=com.gsd.dao.impl.EmpDaoImpl
          empService=com.gsd.service.impl.EmpServiceImpl
          ```
     3. 建一个工厂类`BeanFactory`
          1. 应该有一个 `Object getBean(String BeanName)`方法以根据反射机制获取对象
          2. Properties
               1. Properties是Map的子类,因为`配置文件`非常常用，所以Java集合库提供了一个Properties来表示一组`配置`
               2. prop.load(is);读入一个输入流,转化成BufferedReader,每次读一行,分割成两个字符串,分别存在K,V
               3. 提供了props.getProperty("Key");的方法
          3. Enumeration
               1. Enumeration接口中定义了一些方法，通过这些方法可以枚举（一次获得一个）对象集合中的元素
               2. 这种传统接口已被迭代器取代,但经常和Vector和Properties一起使用
          4. Spring的底层
               - spring就是一个IOC容器
               1. 读取xml或注解
               2. 通过反射的方式,加载创建对象
               3. 使用一个集合Map(IoC容器)保存创建好的对象
               - 通过反射的方式,将创建对象的控制权转换给IoC容器,从而减少了依赖关系,实现了解耦
          ```java
          public class BeanFactory{
               //1. 在静态代码块中读取property文件
               static Properties prop = new Properties();
               static HashMap<String,Object> hashMap = new HashMap<>();//这就是一个IOC容器,里面的value就是创建好的对象
               static{
                    InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("beans.Properties"); //读取配置文件
                    try{
                         prop.load(is);
                         Enumeration<?> names = prop.propertyNames();//这个方法会返回所有key的集合,用快速方法prop.propertyName().var生成的
                         //2. BeanFactory初始化时,按照配置文件生成对象,保存在Ioc容器里
                         while(names.hasMoreElements()){
                              String beanName = (String) names.nextElement();
                              String beanPath = prop.getProperty(beanName);
                              hashMap.put(beanName, Class.forName(beanPath).newInstance());//把创建的对象放在hashMap里,这样可以确保无论调用多少次,永远单例
                         }
                    } catch (Exception e) {
                         e.printStackTrace();
                    }
               }
               //3. getBean方法
               public static Object getBean(String beanName){
                    return hashMap.get(beanName);
               }
          }
          ```

## 补充一个基于Class或者ClassLoader的getResourceAsStream()的区别
1. class.getResourceAsStream
     1. path 不以’/'开头时默认是从此类所在的包下取资源，以’/'开头则是从ClassPath根下(即'/'代表src)获取
     2. 其只是通过path构造一个绝对路径，最终还是由ClassLoader获取资源
2. Class.getClassLoader.getResourceAsStream(String path) 
     1. 默认则是从ClassPath根下获取，且path不能以’/'开头
```java
//默认是从BeanFactory的类路径(com.gsd.bean)下面找配置文件,加了/表示从根目录
BeanFactory.class.getResourceAsStream("/beans.Properties")
//从classpath根目录寻找资源
BeanFactory.class.getClassLoader().getResourceAsStream("beans.Properties")
```
## idea中文乱码
- help -> Edit Custom VM Option -> 添加
- `-Dfile.encoding=UTF-8`

## 1.Spring介绍
- 用来替代Service层
### 1.1 Spring 是什么
1. Spring是分层的 Java SE/EE应用轻量级开源框架,主要提供两个机制:
     1. IoC, Inverse Of Control,控制反转
     2. AOP, Aspect Oriented Programming,面向切面编程
2. 除了IOC AOP以外,主要是充当`粘合剂`作用,整合了其他框架的各种操作
3. Spring的体系架构(看图)
     1. Data Access
          - JDBC
     2. WEB
          - Servlet
     3. AOP,Aspects等四个
     4. Core Container
          - Beans
          - Core
     5. Test
### 1.2 Spring的使用
1. 创建MAVEN项目
2. 导入JAR包
     - 
3. 创建Beans.xml(Spring配置文件)
### 1.3 代码的耦合
1. 解耦:依赖关系不能解除,只能降低,把编译时的依赖推迟到运行时
     1. 类和类之间产生依赖(使用IOC解决)
     2. 方法和方法之间产生依赖(用AOP解决)
2. 举例
- DAO层
```java
public class EmpDaoImpl implements EmpDao {
    @Override
    public void add() {
        System.out.println("ADD");
    }
}
```
- Service层
```java
public class EmpServiceImpl implements EmpService {
     //如果EmpDao不存在,编译时报错,因此EmpServiceImpl依赖于EmpDaoImpl
    EmpDao empDao = (EmpDaoImpl) BeanFactory.getBean("empDao");

    @Override
    public void add() {
        empDao.add();
    }
}
```
### IoC和DI
1. 控制反转IoC:
     - 使用new关键字会导致类与类之间产生依赖,导致程序耦合  
     - 创建对象时,不使用new关键字,而是让IOC容器来控制对象的创建,(不一定是工厂模式)
     1. <Bean>标签(XML文件)
     2. 注解
2. 依赖注入DI给控制反转的对象的属性赋值
     - 在Ioc容器运行期间,动态地将依赖属性和对象注入到当前对象地
     1. 构造器注入
     2. Setter注入
### XML文件
3. <Bean>标签(XML文件)的具体实现:
     1. <bean>标签概述
          - 参数相同时,使用`index`来只当调用哪个构造器
          - `scope`

     2. 构造器注入
          - 默认是空构造器
          - <constructor-arg>标签
     ```java
     //现在有两个构造器
     //1.
     public AccountService(int age, String name, Date date) {
        System.out.println("参数一样时,和顺序无关,构造器1");
        this.name = name;
        this.age = age;
        this.date = date;
    }
     //2.
     public AccountService(String name, int age, Date date) {
        System.out.println("用INDEX指定就是要用构造器2");
        this.name = name;
        this.age = age;
        this.date = date;
    }
     ```
     ```xml
     <!-- 引用类型Date也是一个BEAN -->
     <bean id="d" class="java.util.Date"></bean>
     <!-- 构造器1 -->
     <bean id="as1" class="com.gsd.service.AccountService" scope="singleton">
        <!--    对于基本类型和String类型,使用value直接注入    -->
        <constructor-arg name="name" value="石原里美" index="1"/>
        <constructor-arg name="age" value="25" index="1"/>
        <!--    对于引用类型,需要ref    -->
        <constructor-arg name="date" ref="d"/>
    </bean>

     <!-- 构造器2 -->
     <bean id="as2" class="com.gsd.service.AccountService" scope="singleton">
        <constructor-arg name="age" value="25" index="1"/>
        <constructor-arg name="name" value="石原里美" index="0"/>
        <constructor-arg name="date" ref="d"/>
    </bean>
     ```
     6. 补充:在java中取得这些值
          1. 使用顶层接口BeanFactory
               - 什么时候getBean什么时候创建对象
          ```java
          @Test
          public void test2() {
               ClassPathResource resource = new ClassPathResource("beans.xml");
               BeanFactory factory = new XmlBeanFactory(resource);
               HelloService hs = (HelloService) factory.getBean("hs");     
          }

          ```
          2. 建议使用`ClassPathXmlApplicationContext`这个子类
               加载Factory时立即加载对象
          ```java
          @Test
          public void test(){
               ClassPathXmlApplicationContext act = new ClassPathXmlApplicationContext("beans.xml");
               AccountService as1 = (AccountService) act.getBean("as1");
          }
          ```
          3. Spring结合Junit
               - junit本身需要一个junit5 JAR包
               - 每次测试都要写ClassPathXmlApplicationContext很麻烦
               - junit里面有个默认的Runner(),默认的Runner()是不会创建Spring容器的,因此要用Spring
               的Runner()来替换掉原始的这个
                    - 查找一个类:两下shift
                    - 查看这个类的继承关系: ctrl+H
               1. 导入 spring-test JAR包
               2. junit5的做法
               ```java
               @SpringJUnitConfig(locations = "classpath:beans.xml")//1.在测试类上面加这个注解
               public class SpringTest2 {
                    @Autowired
                    EmpController controller;//替代了之前的getBean()

                    @Test                    
                    public void test(){      //正常测试
                         controller.add();
                    }
               }
               ```



     3. Setter注入<bean><property>
     - 首先要确保各个属性都有`Setter`
     - 其次必须需要`空构造`,因为默认就是调用空构造,然后再Set
     - <property>标签,可以看作是一个setter方法
     ```xml
     <!--  构造器注入太麻烦了,建议使用set注入,默认是空构造器  -->
    <bean id="as2" class="com.gsd.service.AccountService" scope="prototype">
        <property name="name" value="泽尻绘里香"/>
        <property name="age" value="23"/>
        <property name="date"><!-- 使用内部类的方式 -->
            <bean class="java.util.Date"></bean>
        </property>
    </bean>
     ```
     4. Setter注入xmlns:p="http://www.springframework.org/schema/p" 的简写,P标签
          - 现在是有三个字段就要写三个Property
          1. 首先导入p空间
          - 默认空间只有一个,`:`相当于别名
          ```xml
          <beans xmlns="http://www.springframework.org/schema/beans"  //默认空间
             //名字改成p,前面再加个冒号P即可
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans 
          http://www.springframework.org/schema/beans/spring-beans.xsd">
          <beans>
          ```
          2. 修改之前的Setter注入
          - 注意引用类型`date-ref`的写法
          ```xml
          <bean id="as3" class="com.gsd.service.AccountService" scope="prototype" p:name="星野源" p:age="25" p:date-ref="d">
          </bean>
          ```
     5. Setter注入集合
          1. 对于[],List,Set,可以通用`<array>`,`<list>`,`<set>`结合`<value>`标签
          ```xml
          <bean id="other" class="com.gsd.service.OtherService">
               <property name="myStrs">
                    <list>
                         <value>123</value>
                         <value>456</value>
                    </list>
               </property>
          </bean>
          ```
          2. 对于Map和Properties,可以通用`<props><prop>`或者`<map><entry>`
          ```xml
          <bean id="" class="">
               <property name="myMap">
                    <props>
                         <prop key="aaa" value="bbb"></prop>
                         <prop key="ccc" value="ddd"></prop>
                    </props>
               </property>
          </bean>
          ```

     
     5. XML操作总结
          1. 对于基本类型和String类型,使用`value`直接注入
          2. 对于引用类型,需要`ref`
               1. 在外面建一个新的Bean
               ```xml
               <bean id="d" class="java.util.Date"></bean>
               ```
               2. 在原来的bean的内部使用内部类的方式
               ```xml
               <property name="date"><!-- 使用内部类的方式 -->
                    <bean class="java.util.Date"></bean>
               </property>
               ```

          3. 集合
               1. 对于[],List,Set,可以通用`<array>`,`<list>`,`<set>`结合`<value>`标签
               2. 对于Map和Properties,可以通用`<props><prop>`或者`<map><entry>`
     
    

### 注解注入
- EmpDao <- EmpService <- EmpController
- 建议外部导入的类使用xml(因为你不能在别人的jar包里打注解),自己写的用注解
1. 用于创建对象(IoC)的常见注解
- EmpDao是底层,其他两个类依赖于它,因此应该创建EmpDao的对象
- 标注在这类上后,会生成一个单例放在IoC容器里,相当于配置了一个`<bean id="" class="">`
- 这四种注解作用和属性一样,只是用于区分
     1. @Component
     2. @Repository, DAO
     3. @Service
     4. @Controller
2. 默认`括号里()`使用的`id`是简单名称,当前类就是`class`
     - 简单名称: 类名首字母小写
     - 全限名称: com.gsd.dao.EmpDao,包名+类名

----

3. 用于注入数据(DI)的注解
- 相当于bean标签里的`<property name="" ref="">`
- 有两种方式
     1. 直接写在属性上面,属性注入(XML没有这个)
     2. 直接写在Setter上面,Setter注入
- Resource和Autowired注解
     1. @Autowired
          - `Spring提供的`注解,默认是`按类型`注入,且可以和Spring其他注解结合使用
          - 括号里填写truefalse,默认和true表示依赖检查,没有的话会报错
          - 假如现在一个接口有两个实现类,(注解打在`接口实例(EmpDao)的头上`)Autowired会因为不知道注入哪个而报错
               1. 可以使用@Primary提高某个实现类的优先级
               2. 可以在@Autowired下面加个`Qualifer("empDaoImpl2")`来改成`按名称`注入
     2. @Resource
          - `Java提供的`注解,默认`按名称`注入
          - 没有名称时按类型
          - 类型找不到或者多个类型时会报错

4. 加了注解之后,Spring怎么知道有哪些类上有注解?
- 需要使用包扫描
     1. 正统方式
     - 如果是XML的话,默认会扫描`beans`空间,也就是只有`<bean>`标签
     - 类似于上面的`p空间`,这里需要改的是`context`空间和`schemalocation`
     - 空间和schema的`规律`,上面空间导入一个,下面schema导入两个
     ```xml
     <beans xmlns="http://www.springframework.org/schema/beans"  //默认空间
          xmlns:context="http://www.springframework.org/schema/context"    //增加1  
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="
          http://www.springframework.org/schema/context  //增加2
          http://www.springframework.org/schema/beans/spring-context.xsd     //增加2
          http://www.springframework.org/schema/beans 
          http://www.springframework.org/schema/beans/spring-beans.xsd">
     <beans>
     ```
     2. 简洁操作
     - 输入`<co`,选择`componet-scan`
     - 会自动补全上面`beans`标签缺的东西
     ```xml
          <!-- 从这个类的子类开始扫描有注解的类 -->
          <context:component-scan base-package="com.gsd">
     ```
