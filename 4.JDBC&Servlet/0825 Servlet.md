## 0825
- Java Web Project
- Servlet
### 1.新建一个Dynamic Web Project
1. 先看一下Servlet规范
    - test 根目录
        - WEB-INF 
            - classes
            - lib (存放Web应用需要用到的jar包,jdbc等等)
            - web.xml(重要*,包含配置和部署信息)
        - 各种JSP文件
2. 新建一个Dynamic Web Project
- 带颜色的表示是后来加的
    - src/main
        - java
            - com
                - `DAO,Entity等`
            - `druid.properties`
        - webapp
            - `bootstrap`
            - META-INF
            - WEB-INF
                - lib
                    - `druid-0.1.jar等`
                - `web.xml`
            - `JSP文件`

- web.xml可以参考tomcat的conf下面的那个web.xml
- 建项目时有个`Create a new local server`选项,复制一份配置文件,这样就不用每次都去tomcat改了
- `Generate web.xml`
### 2. Servlet
1. Servlet的生命周期
    1.	实例化: 访问servlet的网址时实例化,仅一次
    2.  初始化: 实例化的同时初始化,仅一次
    3.	服务/Service: 每次调用都会执行一次
    4.	销毁: 关闭服务器后销毁所有servlet实例,仅一次
2. Servlet接口
- 创建一个类实现Servlet接口,重写以下方法:
    1. void init(ServletConfig config)
    2. void service(ServletRequest req, ServletResponse res)
    3. destroy(),销毁前调用
    4. doGet
    5. doPost
3. service()方法
    1. 网页输出
    ```java
    //通过字符流把数据显示到网页上
    PrintWriter writer = res.getWriter();
	writer.print("Hello Servlet");
    ```
    2. 如何让客户端来调用这个类?(Servlet的部署)
        - Servlet 应用程序位于路径 `<Tomcat-installation-directory>/webapps/ROOT` 下 
        - 类文件放在 `<Tomcat-installation-directory>/webapps/ROOT/WEB-INF/classes` 中
        - 假如`com.myorg.MyServlet`,那么这个 Servlet 类必须位于 `WEB-INF/classes/com/myorg/MyServlet.class` 中

    3. 通过web.xml来发布自己写的java类
        - `<Tomcat-installation-directory>/webapps/ROOT/WEB-INF/` 的 web.xml 文件
        - 分为注册和映射两步
         - 这样就可以通过`http://localhost/HelloServlet/test`调用这个servlet了
    ```xml
    <!-- 1.注册 -->
    <servlet><!-- 1.1 注册 -->
  	    <servlet-name>HelloServlet</servlet-name><!-- 最好和项目名一致 -->
  	    <servlet-class>com.servlet.HelloServlet</servlet-class>
    </servlet>
  
    <servlet-mapping><!-- 1.2 映射 -->
  	    <servlet-name>HelloServlet</servlet-name><!-- 与上面一致 -->
  	    <url-pattern>/test</url-pattern><!-- 必须以斜杠开头 -->
    </servlet-mapping>
    ```
    4. xml保存慢的问题
            - Windos->perference->Validation
    5. 重启servlet的三种情况
            1. 修改了web.xml
            2. 新建了新的Servlet类
            3. 导入了新的jar包

4. 多映射与生命周期
5. 注解开发,最佳实践(重要)
6. 为什么要重写doGet()方法
    - 父类的service()有调用请求的作用,如果重写之后,父类的service就失效了
    - 父类service有缓存协商功能,能避免反复调用静态资源
    - 因此只重写doGet和doPost

