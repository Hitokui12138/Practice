## MYBAITS
1. jdbc的缺点
    - 把对象的属性转成字段,和把表的字段转成对象属性的过程是手动的很麻烦
    - 每个方法都要trycatch,代码冗余
2. MYBAITS
    1. 是一个半自动的ORM框架(需要自己写sql),还有另一种jpa规范是全自动ORM
        - Object Relation Mapping,通过映射文件,配置`对象和表`,`属性和字段`之间的关系
        - `自动`实现sql和对象的转换,通过ORM实现持久化技术
    2. 底层也是jdbc,用来替代DAO层,持久层技术框架
        - 持久化:把对象保存到硬盘和数据库,或者把文件,数据库转换到内存的过程
3. MYBAITS的使用
    1. 导入mybaits JAR包
    2. 连接数据库的话还需要相关驱动包
        - mysql-connector-java
    3. 新建MyBaties的配置文件,`mybatis-config.xml`,这个可以直接从官网copy模板
        - 后面这个文件会被替换掉,但Driver的四个配置应该背下来
        1. `<environments default="XXX">`配置`数据源`
            - 可以配置多个数据源,但每次用的时候只会用default的值的那个
            - 问题在于只有一个链接,总不能让一个用户登录后一个用户等着吧
        2. `<mappers>`映射文件
    4. 新建mapper文件,也去找个模板
        1. `<mapper namespace="com.gsd.dao.empDao">`,相当于是一个类
        2. `<insert>`,`<select>`,每个标签都相当于是一个方法
            - id: 唯一的方法名
            - parameterType="com.com.gsd.entity.Emp",可以直接传进去一个对象
            - OGNL表达式 #{get方法}，底层使用PreparedStatement接口，先生成？，再替换
            ```xml
            <mapper namespace="com.gsd.dao.EmpDao">
                <!-- parameterType表示参数类型,如果是int之类的可以不写 -->
                <insert id="addEmp" parameterType="com.gsd.entity.Emp">
                    <!--   OGNL表达式    -->
                    INSERT INTO emp VALUES (0, #{ename}, #{job}, #{mgr}, now(), #{sal}, #{comm}, #{deptno}, 1)
                </insert>
                <!-- 写一个resultType表示返回类型 -->
                <select id="findAll" resultType="com.gsd.entity.Emp">
                    select empno,ename,job,mgr from emp
                </select>
            </mapper>
            ```
        3. 总结:如何让mapper知道要生成哪个方法?
            1. 让mapper的namespace指向接口 `<mapper namespace="com.com.gsd.dao.EmpDao">`
            2. 接口的方法名和参数name保持一致
    5. java
        1. 读入配置文件,Resources.getResourceAsStream(),`Resources是mybatis提供的工具类`
        2. 创建MyBatis的核心类,`SqlSessionFactory`,,后面会用Spring去创建
        3. 创建`SqlSession`,相当于之前的connection,MyBatis默认是`手动提交事务`
        ```java
        @Test
        public void test() throws IOException {
            //1. 获得sqlSession
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");//读取配置文件
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);//这种不能直接new的说明SqlSessionFactory是个接口
            sqlSession = factory.openSession(true);//无参或false时手动提交，true为自动提交

            //2. 执行sql
            Emp emp = new Emp();
            int rows = sqlSession.insert("addEmp", emp);//可以直接传一个对象进去,自动转换   
            System.out.println(rows==1?"添加成功":"添加失败");
        }
        ```
    6. 把初始化操作放到测试用例执行前后操作,@BeforeAll,@AfterAll
        ```java
        @BeforeAll
        public static void before() throws IOException {
            System.out.println("===========INIT============");
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
            sqlSession = factory.openSession(true);//无参或false时手动提交，true为自动提交
        }
        @AfterAll
        public static void close(){
            System.out.println("===========END============");
            //sqlSession.commit();
            sqlSession.close();
        }
        ```

## 日志框架
- 之前都是sout输出测试结果,以后用测试框架来打印
1. 两种
    1. 门面日志
        - 提供接口,JCL,SLF4J(myBatis用这个)
        - 会自动检测项目导入了什么实现,就用什么实现
    2. 具体日志实现
        - 我们这里用LOG4J2
2. 使用日志
    1. 导包log4J-core
    2. 配置文件,用老师发的那个
        1. logger
            1. 注意name表示类
        ```xml
        <!-- 上面还能设置打印格式 -->
        <loggers>
            <!-- 自定义配置 ,注意类名-->
            <logger name="test.EmpTest" level="debug" additivity="false">
                <appender-ref ref="Console"/>
            </logger>
            <!-- 最低配置(默认配置) -->
            <root level="">
                <!-- 打印位置设置为控制台,也可以存文件,存数据库 -->
                <appender-ref ref="Console">
            </root>
        </loggers>
        ```
3. LOG4J2日志的级别
4. 使用
    ```java
    public class EmpTest {
        //1. 首先需要一个核心的Logger
        Logger logger = LogManager.getLogger(EmpTest.class);
        
        @Test
        public void test1(){
            logger.info("打印一个info级别的日志");
            logger.debug("这是一个{}级别的{}","debug","日志");//可以使用占位符
        }
    }
    ```

5. 增删改
    1. insert delete update 标签可以混用
        - `int rows = sqlSession.insert("addEmp", emp);`
    2. 查单条
        - `Emp emp = sqlSession.selectOne("detail",7369);`
    3. 查所有
        - `List<Emp> empList = sqlSession.selectList("findAll");`
    4. 动态代理模式
        - 动态映射mapper替代了DAO,DAO可以`只写接口`不写实现类了
        - 接口不能实例化
        - 之前的调用方法:`sqlSession.insert("addEmp", emp)`
        - 现在`empDao.addEmp(emp);`

        ```java
        @Test
        public void test() throws IOException {
            //EmpDao是个接口,运行时动态生成接口的实例
            EmpDao empDao = sqlSession.getMapper(EmpDao.class);
            Emp emp = new Emp();
            int rows = empDao.addEmp(emp);
            System.out.println(rows==1?"添加成功":"添加失败");
        }
        ```
## MyBatis和Spring结合
1. 现在的问题:
    - 去掉MyBatis包后都会报错,耦合
    - 一次只能连一个链接
2. 主要是学习用,用到很多设计模式
    1. SqlSessionFactory, 工厂模式
    2. build(),建造者模式
    3. mapper代理模式
3. 用Spring的思路:
- `myBatis提供`了和Spring整合的文档,看一下文档`https://mybatis.org/spring/`
    1. 应该由Spring创建`SqlSessionFactory`
        - 但是SqlSessionFactory是个`接口`,既没有构造器,也没有Set方法
        - 整合包提供了一个`SqlSessionFactoryBean`类,可以看一下这个实现方法
        ```java
        public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>{
            /*
                用Ctrl+F12看一下里面的方法
                里面有各种set方法
             */
        }
        ```
4. 具体
    1. 导入`整合包MyBatis-Spring`和`Spring`需要的包和`spring-jdbc数据源包`
    2. 新建配置文件beans.xml,
        - 原来的mybatis-config.xml定义了两个值,`数据源`和`mapper文件`
        1. 类就是那个整合包提供的实现类,注意这种第三方包提供的bean的id不能随便写
            - 去掉后面的Bean,首字母小写
        2. 关于数据源,既可以用Druid,也可以用Spring自带的数据源
            - JAR包名叫`spring-jdbc`,不止数据源,还能管理事务等等
            - 类名叫DriverManagerDataSource,实现了DataSource接口
            - 关闭连接等操作都可以托管给Spring
    ```xml
    <!-- 核心类 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--    1.数据源（DataSource接口），可以用druid或者spring自带的数据源    -->
        <property name="dataSource" ref="dataSource" />
        <!--    2.Mapper文件,value不会写可以参考官方文档    -->
        <property name="mapperLocations" value="classpath*:mapper/*.xml" />
    </bean>

    <!-- 数据源 -->
    <bean id="dataSource"  class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/myschool?serverTimezone=GMT&amp;useSSL=false"/>
        <property name="username" value="root"/>
        <property name="password" value="admin"/>
    </bean>
    ```
    3. 注册映射器
        - 现在还有个问题是没有`sqlSession`托管给Spring了,就不能用`sqlSession.getMapper(EmpDao.class);`方法
        - mybatis提供了一些方法,推荐使用注册映射器法
        - DAO是接口,用代理对象实例化接口对象
        ```xml
        <!-- 方法1 MapperScanner 生成扫描器 -->
        <!--  扫描value="com.gsd.dao"该包下的接口，生成`代理对象` -->
        <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
            <property name="basePackage" value="com.gsd.dao"/>
        </bean>

        <!-- 方法2 推荐 直接发现映射器-->
        <mybatis-spring:scan base-package="com.gsd.dao"/>
        ```