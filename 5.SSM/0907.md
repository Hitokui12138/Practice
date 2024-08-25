
## 创建Maven项目
1. 变成web项目 Add FrameworkSupport ->Web
2. 把jar包放进去 Project -> Artifaact -> 右键 -> put into output root
    - 注意target和out两个文件
    - build后生成target,让java文件->.class文件
    - out是根据target发布生成的网站
3. 配置Tomcat
    - 解决UTF-8的问题
- 对于idea,debug运行才能热部署


## 自定义MVC(区分与SPRINGMVC的工作原理)
1. 框架
    1. 中央/派发处理器(SpringMVC的入口)
        - 创建一个DispatcherServlet以拦截并派发所有Http请求
        - 在xml里面配置这个servlet
        - 拦截所有.do的请求
        ```xml
        <!-- web.xml -->
        <!-- 配置成下面这样就能拦截所有.do请求? -->
        <servlet>
            <servlet-name>actionServlet</servlet-name>
            <servlet-class>com.gsd.mymvc.ActionServlet</servlet-class>
        </servlet>

        <servlet-mapping>
            <servlet-name>actionServlet</servlet-name>
            <url-pattern>.do</url-pattern>
        </servlet-mapping>
        ```
        ```java
        public class ActionServlet extends HttpServlet {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                System.out.println("中央处理器");
                //其他各种组件...
            }
        }
        ```
    2. 适配器映射器(处理器映射器)
        - 根据url拿到映射名
        - 用getServeletPath取得url,在用substring取得.前面的部分
        ```java
        //适配器映射器
        String servletPath = req.getServletPath();
        String servletName = servletPath.substring(1,servletPath.lastIndexOf("."));
        ```
    3. 适配器处理器
        - 根据映射名找到对应的controller
        - 根据映射器取得的结果来判断要new哪个controller
        ```java
        //适配器处理器
        Controller controller = null;
        //url带login时就新建logincontroller
        if(servletName.equals("login")){    
            controller= new LoginController();
        }else if(servletName.equals("reg")){
            controller= new RegController();
        }
        ```
    4. Controller要有execute()方法,能返回下一步要跳转的url
    ```java
    //一个负责登录的controller
    public class LoginController implements Controller {
        @Override
        public String execute(HttpServletRequest req, HttpServletResponse resp) throws   ServletException, IOException {
            System.out.println("登录操作");
            return "main.jsp";
        }
    }
    ```
    5. 视图解析器
    - 执行controller的方法,然后根据返回结果进行跳转
    ```java
    //视图解析器
    String result = controller.execute(req,resp);//返回结果是"main.jsp"    
    req.getRequestDispatcher(result).forward(req, resp);
    ```
    6. 结果是s调用`/login.do`时,自动跳转到`main.jsp`
2. 问题
- 现在servelt依赖于HttpServelt,Response等等
## SpringMVC
- 本身是Spring里的一个模块
1. 使用方法
    1. 导包 `spring-webmvc`
        - 包含aop,beans,context,core,expression,spring-web
    2. 在web.xml`注册中央/派发处理器`,和自定义的那个一样
        - 中央处理器 DispatcherServlet
        - 映射*.do
        - 用load-on-start让启动时就注册
        - springmvc也是个Ioc容器,容器启动需要一个配置文件,
        - 有默认路径,`Web-INF/dispatcherServlet`下面不太好,因此自己设置一下
    3. 指定配置文件位置
        - 使用初始化参数 init-param conextConfigLocation
        - 路径就放在resource里面,写作`classpath:`
        ```xml
        <servlet>
            <servlet-name>dispatcherServlet</servlet-name>
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <init-param><!--   指定配置文件,不要默认的   -->
                <param-name>contextConfigLocation</param-name>
                <param-value>classpath:springmvc.xml</param-value>
            </init-param>
            <load-on-startup>1</load-on-startup><!--启动就注册-->
        </servlet>
        ```
2. 创建controller
- 本身是取代servlet的,每一个方法就是一个请求
    1. 类上注解`@Controller`
    2. 方法上注解`@RequestMapping("hello.do")`,返回"main.jsp"
        - 访问hello.do后,会被这个方法拦截,然后跳转到main.jsp
    3. 注意一旦用了注解,就需要开启包扫描,以告知Spring这些类上有注解标签
    4. 你可以在下面的`SpringTab`里面查看有哪些controller加入容器了(创建成功)
    ```java
    @Controller
    public class HelloController {
        @RequestMapping({"hello","hello1"})
        public String hello(){
            System.out.println("hello");
            return "main.jsp";
        }
    }
    ```
3. @RequestMapping的用法(重要)
    - 建立请求URL和方法之间的对应关系
    1. 可以写多个映射
        - @RequestMapping({"hello.do","hello1.do"})
    2. 可以再配置一下web.xml文件来不用写do
        1. `*.XXX`
        2. `/ `  拦截静态资源 js css png jpg(把之前的.do改成/)
        3. `/* ` 还能拦截JSP
        4. 改成/后直接调用/hello

    3. 接收参数(`jsp -> controller`)
        1. @RequestMapping("param")写在类上表示目录
            - /param/hello
            - 注意之前返回的`main.jsp`是相对于根目录,加了这个会相对于/param
            - 可以改成绝对路径`/main.jsp`
            1. 每个都改斜杠冗余,可以注册一个`视图解析器`,springmvc.xml
                - `InternalResourceViewresolver`,Prefix,suffix
                - 然后只要返回"main"就好
        2. 以前get请求只能传String,但mvc内置了转换器,可以解析String和int
            - ?username=xxx&age=18
            - 直接在方法上就能拿到请求参数
            ```java
            @RequestMapping(value = "param1",method={RequestMethod.GET})
            public String param1(String username, int age){}
            ```
        3. 内置没有`日期`转换器
            - 需要注意像是Date类型,并没有内置转换器,直接转换会报400的错误
            1. 打开日期转换器,需要提供一个标签
                - 在mvc空间xml里提供`<mvc:annotation-driven>`
            2. 然后需要日期格式,在entity日期属性上加注解
                - @DateTimeFormat(pattern="yyyy-MM-dd")
                ```java
                @DateTimeFormat(pattern = "YYYY-MM-dd")
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
                private Date birthday;
                ```
        4. `数组[]`可以直接接收,`集合<User>`的写法比较特殊
            1. JSP需要list[0].username,list[0].age
            2. 后台不能直接拿`集合<User>`来接收,需要封装一个实体类VO
            ```java
            //VO
            public class UserVO{
                private List<user> list;
            }
            //Controller
            @RequestMapping("param3")
            public String param3(UserVO vo){}
            ``` 
        5. 默认是get
            - 用@GetMapping和@PostMapping简化
                - Post的需要加一个中文过滤器于web.xml    
        6. 现在没有了req,如何取得请求头?
            - 别忘了导入Servlet,jsp,jstl相关的包
            1. Autowired一个HttpServletRequest,但这样会引入依赖
                - request.getHeader
            2. 推荐写法
            ```java
            @RequestMapping("param6")
            public String param6(@RequestHeader("Host") String host){}
            ```
        7. 操作Session
            1. @Autowired HttpSession,不行会耦合
            2. 在Controller上加个注解`@SessionAttribute`,表示想操作的变量,再使用`ModelMap`
            ```java
            @Controller
            @RequestMapping("result")
            @SessionAttribute({"username","user"})//声明想操作的session变量
            public class ResultController{
                //设置session
                @GetMapping("result3")
                public String result3(Model model){
                    model.addAttribute("username","admin");//同时设置request和session
                    return "main";
                }
                

                //从session中获取
                @GetMapping("result4")
                public String result4(ModelMap modelmap){
                    String username = (String)modelMap.getAttribute("username");
                }
            }
            ```

    4. 返回结果(`controller -> jsp`)
    - 一般方法是request.setAttribute,为了不使用@autowired而使用下面方法比较多
        0. 重要：
        - 单个值可能想到`@RequestParam(value="id",required = false,defaultValue = "10086") Integer id`
        - @RequestParam 不支持传递实体类
        - 值比较多了考虑将请求参数封装成实体类，然后直接传对象
        - 如果两个实体类中有相同的属性,那么前台传入的参数值会同时封装进入两个实体类中,
            - 例如前台传入一个 id,而 Saleman、Product 两个实体类都有 id 这个属性,那么 id 对应的参数值就同时封装进了 saleman、product
            - 也就是说会生成两个对象
        1. SpringMVC本质上返回一个ModelAndView
            - 使用`Model`传值,Model处于Request作用域,本身是个Map
            ```java
            //早期标准写法
            @GetMapping("result")
            public ModelAndView result(){
                ModelAndView mav = new ModelAndView();
                mav.setViewName("main");//跳转哪个页面
                mav.addObject("username","admin");//本质是Map
                return mav;
            }    
            
            //简略写法,就返回String
            @GetMapping("result2")
            public String result2(Model model){
                model.addAttribute("username","admin");//本质是Map
                return "main";
            }
            ```

    5. 常用注解
        1. @RequestParam 默认值,必须,用引用类型防止null
            ```java
             public String other(@RequestParam(value = "curPage",required = false,defaultValue = "1") Integer curPage,
                        @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize){
                            //...
                        }
            ```
        2. @GetMapping("other1/{id}") + @PathVariable()
            - Restful,路径传值
            ```java
                @GetMapping("other1/{id}")
                public String other1(@PathVariable(value = "id",required = false) String empno){
                    System.out.println("empno = " + empno);
                    return "main";
                }
            ```
        3. @CookieValue获取cookie
            ```java
            public String cookie(@CookieValue("JSESSIONID") String id){
                //...
            }
            
            ```
        4. 异步请求 @ResponseBody
            - 为了传json,使用jackson
            - 日期还需要注意时区
            ```java
            @GetMapping("json")
            @ResponseBody//把返回结果直接返回客户端,不再是返回一个页面
            public User json(){
                User user = new User();
                user.setUsername("admin");
                user.setAge(18);
                user.setBirthday(new Date());//默认返回时间戳
                return user;//直接就在response报文里面可以看到
                //{"username":"admin","age":0,"birthday":null}
            }
            ```

## SSM整合
1. 把之前做的Mapping文件和MyBaits.xml复制过来
2. (官方推荐)做两个容器    
    1 DispatcherServlet 入口
    2 Servelt WebApplicationContext
        1. Controllers
        2. ViewResolver
        3. HandlerMapping
    3 Root WebApplicationContext
        1. Service
        2. Repositories
3. 步骤
    1. 导包
    2. 增加web.xml文件
    3. 配置文件
        1. springmvc
        2. bean
        3. log4j2
        4. 数据源配置文件
    4. 相关java类,Controller Service dao 实体类,Mapper文件
4. springmvc容器进来了,bean还没有进来
    - 使用监听器,这个监听器会在在网站启动销毁的时候执行,创建WebApplicationContext,就是Spring容器
    - org.springframework.web.context.ContextLoaderListener
    - 在web.xml里面注册一下
    - 建容器需要配置文件,也有个默认位置,手动设置位置
