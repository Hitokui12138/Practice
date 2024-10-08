## 0830
- Session与Cookie
	- 免登录机制
	- 显示上次登录时间
- 转发与重定向
- JSP与Servelet的本质

## 总结:Session和Cookie的区别
- cookie和session都是用来跟踪浏览器用户身份的会话方式。
1. 存储位置不同cookie数据保存在客户端，session数据保存在服务端。
2. 存储的数据类型不同,cookie中只能保管ASCII字符串，并需要通过编码方式存储为Unicode字符或者二进制数据。,session中能够存储任何类型的数据
3. 存储容量大小不同cookie存储的容量较小，一般<=4KB。session存储容量大小没有限制
3. 存储有效期不同,cookie可以长期存储，只要不超过设置的过期时间，可以一直存储,session在超过一定的操作时间(通常为30分钟)后会失效
4. 安全性不同cookie存储在客户端，所以可以分析存放在本地的cookie并进行cookie欺骗，安全性较低。
session存储在服务器上，不存在敏感信息泄漏的风险，安全性较高。


### 1.Cookie与Session
- `Http请求没有状态`
- 为了`识别`出来自`同一个浏览器`的访问请求,需要有状态的会话
- 属于同一个会话中的请求消息都附带同样的标识号，而属于不同会话的请求消息总是附带不同的标识号
- 有两种实现机制,Cookie和Session

### 2.Cookie
- 浏览器第一次访问服务器某个资源时,Web`服务器`在HTTP`响应消息头`中返回附带的一个小文本文件
- `浏览器`保存这个Cookie之后每次访问服务器,都在HTTP`请求消息头`中增加Cookie回传给WEB服务器
- 一个Cookie只能标识一种信息,名称（NAME）和设置值(VALUE),也就是键值对
- 一个WEB站点可以给一个WEB浏览器发送多个Cookie,一个WEB浏览器也可以存储多个WEB站点提供的Cookie
1. 浏览器tools
    1. NetWork->login->Requiets Header 看一下有没有携带cookie
    2. Application->Storage->cookies 查看当前的cookie的值,超时时间
2. cookie的生命周期
    1. 创建Cookie
        1. (`会话cookie`)如果创建了一个cookie，并将他发送到浏览器，`默认`情况下它是一个`会话级别`的cookie; 存储在`浏览器的内存`中,用户`退出浏览器之后被删除`
        2. (`持久cookie`)若希望浏览器将该cookie存储在`磁盘`上，则需要使用`setMaxAge()`，并给出一个以秒为单位的时间;`关闭后再次打开浏览器`，这些cookie`依然有效`直到超过设定的过期时间。
        3. 将最大时效设为`0`则是命令浏览器`删除`该cookie
        4. 存储在`硬盘上的cookie`可以`在不同的浏览器进程间共享`，比如两个IE窗口
    2. 发送cookie
        1. 发送cookie需要使用HttpServletResponse(`响应端`)的`addCookie`方法
        2. 并不是修改之前的cookie,而是创建新的报头，因此将这个方法称为是addCookie
    3. 读取cookie
        1. 在JSP中调用`request.getCookies()`获得一个`Cookie[]数组`,再用遍历使用`getName()`取得需要的那个
3. 代码
- 做一个免密登录
    1. Servlet
    - HttpServletResponse(响应)接口中定义了一个addCookie()方法
    ```java
    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取用户名,记住我checkbox
		String username = req.getParameter("username");
		String jizhuwo = req.getParameter("jizhuwo");//小写on
		//2. 当用户名存在时
		if(username.equals("admin")) {
			req.setAttribute("username", username);
			
			//3. 当记住我勾选时,设置Cookie
			if("on".equals(jizhuwo)) {
				System.out.println("免密登录");
				Cookie c = new Cookie("username",username);//对象只能存在服务端,这就是个"username=username"的字符串.传给客户端
				c.setMaxAge(30);//Cookie存活时间设置为30秒
				//c.setPath("/hello1");//默认是当前网站,可以修改,不安全
				resp.addCookie(c);//把cookie通过响应报文返回给浏览器
			}else {
				System.out.println("普通登录");
			}
			
			// getRequestDispatcher -> 服务器转发,站内转发,只能在网站内部跳转,浏览器地址不变
			req.getRequestDispatcher("main.jsp").forward(req, resp);

		}else {
            //4. 若用户名不存在,跳转回来
            //演示另一种跳转方法,重定向
            //resp.setStatus(302);
            //resp.setHeader("Location","login.jsp");

            //4.1 使用相对路径跳转
			//resp.sendRedirect("login.jsp?message=1");//上面两个二合一
			//4.2 动态获取内容路径,并使用绝对路径跳转,重定向的绝对路径是服务器的根目录
			resp.sendRedirect(req.getContextPath()+"/login.jsp?message=1");//绝对路径
			}
		}
    ```

    2. JSP(login画面)
    - request是JSP内置的参数
    - `request.getCookies()`返回cookie[]对象数组,因为一个网站有很多cookie
    - HttpServletRequest接口中定义了一个getCookies方法
    - cookie有getName(),getValue(),setValue()方法
    ```html
    <body>
    <!-- 这种先写null的写法可以避免空指针 -->
    <%=null!=request.getParameter("message")&&"1".equals(request.getParameter("message"))?"用户名密码错误":"" %>
    <!-- 获取cookie,符合条件直接跳转到mian页面 -->
    <%
	//获取cookie,查看有没有当前cookie,有的话直接跳转到main.jsp,没有的话再登录
	Cookie[] cs = request.getCookies();
	if(cs!=null){//第一次没有cookie,当心空指针
		for(Cookie c: cs){
			if(c.getName().equals("username")){
				response.sendRedirect("main.jsp");//直接跳转到
			}
		}
	}

    %>
    <form action="login1" method="post">
	    用户名<input name="username"><br>
	    <button>登录</button>
	    <input type="checkbox" id="jizhuwo" name="jizhuwo" /><span>记住我?</span>
    </form>
    </body>
    ```

4. 转发与重定向的区别
    1. 转发是`服务器跳转`(客户端请求一次,服务器请求一次),重定向是`客户端跳转`(客户端请求两次)
    2. 转发因为在服务器内部可以传递`任意类型`的变量,重定向只能传字符串
    3. 转发的`绝对路径`是当前网站的根,重定向的`绝对路径`是服务器的根目录
    4. 转发只能`网站内`转发,重定向可以`任意`跳转(因为是用url跳转的)
### 2. 请求转发,重定向总结
```java
//在JSP中
    //1. 请求转发
    request.getRequestDispatcher("scope1.jsp").forward(request,response);
    //2. 响应重定向
	response.sendRedirect("scope1.jsp");
```
- (补充)转发可以传递任意类型的变量,重定向只能传字符串
- 转发的绝对路径是当前网站的根,重定向的绝对路径是服务器的根目录
1. 请求转发
- (定义不同)客户端发送`请求`,`服务器`收到请求,`代替客户端`将请求转发给目标地址,再将目标地址的返回结果转发给客户端
- (数据共享不同)`客户端`只发送了`一次请求`,请求数据在`服务器`中处理,因为整个过程只有一个request,因此`共享request数据`
- 一个请求会创建一个request对象,如果`一个请求`跨越了`多个Servlet`,这些Servlet可以通过request共享数据(前一个servlet使用setAttribute(),后一个使用getAttribute())
- (最终URL不同)`最终URL`还是之前的URL
- (请求方不同)请求转发是`服务器行为`
2. 请求重定向
- 客户端发送`请求`,`服务器`给客户端发送一个`临时响应头`,里面记载了`需要再次发送请求`(重定向)的`URL地址`,`客户端`收到临时响应头,又向该URL发送了`另一次请求`
- `客户端`发送了`两次`完全不同的请求
- `最终URL`是第二次请求的跳转地址
- 请求重定向服务器只是给客户端返回了一个URL,剩下的都是`客户端行为`
## 
5. 显示上次登录时间(在JSP中设置cookie)
- 登录后的main页面
```html
<body>
<%  
    // 1.初始化登录名和最后登陆时间
	String username = request.getAttribute("username")!=null?(String)request.getAttribute("username"):"";
	String LastLoginTime = null;

	// 2.初始化Cookie[]和当前时间
	Cookie[] cs = request.getCookies();
	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	String dataStr = sdf.format(new Date());//当前时间
	
	
	for(Cookie c:cs){
		if(c.getName().equals("username")){
			username = c.getValue();
		}
        // 3.如果上次登陆时间的cookie存在的话,format后设置给LastLoginTime
		if(c.getName().equals("LastLoginTime")){
			Date d = new Date(Long.parseLong(c.getValue()));//保存时间戳
			LastLoginTime = sdf.format(d);
		}
	}
	
    //4. 如果上次登陆时间的cookie不存在的话,把当前时间设给它
	if(LastLoginTime == null){
		LastLoginTime = dataStr;
	}
	
    //5. 更新(覆盖)之前的最后登录时间cookie
	Cookie c2 = new Cookie("LastLoginTime",System.currentTimeMillis()+"");
	c2.setMaxAge(1*60*60*24*365);
	response.addCookie(c2);
	
%>
	欢迎你 <%=username %><br>
	上次登录时间<%=LastLoginTime %><br>
	<a href="logout1">退出</a>
</body>
```
6. 注销(删除cookie)
- 用设置MaxAge为0的方法删除原来的cookie
- 别忘了把修改后的cookie`Add`到响应里
```java
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie c = new Cookie("username","1234");//后添加的会把前一个覆盖掉
		c.setMaxAge(0);//销毁
		resp.addCookie(c);
		resp.sendRedirect("login.jsp");
	}
```

### 3. Session(会话)
- 这里的session指的是在`服务器端`保持 HTTP 状态信息的方案 
- `服务器`的session使用Map套Map的形式保存信息
- Map1<JSESSIONID,Map2>,Map2<Key,Value>
1. 生命周期
    1. Session的创建
        1. 某个客户端的请求创建一个session,`服务器`首先检查这个`客户端`的`请求报文`里是否包含了一个`JSESSIONID`的`Cookie`
        2. `有JSESSIONID`时,则说明以前已经为此客户创建过session,`服务器`检索sessionMap使用那个session
            - 如果id不存在(用户可能人为地加了一个`JSESSIONID`的参数)，服务器可能会新建一个,也可能不会,取决于代码
                1. `req.getSession()` 不写或者true,新建session
                2. `req.getSession(false)` false:返回null
            - session并不是客户端访问时就创建的,仅当`req.getSession()`被调用时才会创建session
        3. `没有JSESSIONID`时,则为此客户`创建一个session`并且生成一个与此session相关联的`sessionId`,这个`session id`将在本次响应中返回给客户端保存
    2. Session的超时
        1. WEB服务器无法判断客户是否已经离开或关闭了浏览器，WEB服务器`不能一直保留与之对应的HttpSession对象`。
        2. 如果某个客户端在`一定的时间之内`没有发出后续请求，WEB服务器则认为客户端已经停止了活动,结束与该客户端的会话并销毁相应的session
        3. 会话的`超时间隔`可以在`web.xml`文件中设置,一般是`30分钟`
        4. 也可以在代码中单独设置`超时间隔`,session.setMaxInactiveInterval(1*60*60*24*365),单位秒
2. 代码
- 免密登录
    1. Servlet
    ```java
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
        //1. 把输入的用户名密码查询数据库
		User user = new UserDao().getPassword(username,password);
        //2. 用户名密码正确时
		if(null!=user) {
			//3. 获取session,和sessionID的cookie
			HttpSession session = req.getSession();//获取session,至于判断有没有JSESSIONID之类的操作,这个方法会代办
			Cookie c = new Cookie("JSESSIONID",req.getSession().getId());//固定写法

            //4. Cookie的生命周期和session的周期一般是保持一致的
			session.setMaxInactiveInterval(1*60*60*24*365);//一般是半个小时,给session设一年
			c.setMaxAge(session.getMaxInactiveInterval());//JSESSIONID的Cookie也保持一致
			resp.addCookie(c);
			
			//5.因为session是个map,所以用setAttribute()操作
			req.getSession().setAttribute("user", user);//可以存储对象,因为在服务器里
			req.getRequestDispatcher("main.jsp").forward(req, resp);
		}else {
			resp.sendRedirect("login.jsp?message=1");
		}
	}
    ```
    2. JSP
    - JSP中可以直接使用session来获取当前session
    - 使用session.getAttribute()获取Object(注意转型)
    ```html
    <body>
    <%
    //若user存在,无需登录直接跳转
	if(session.getAttribute("user")!=null){
		response.sendRedirect("main.jsp");
	}
    %>
    <%=null!=request.getParameter("message")&&"1".equals(request.getParameter("message"))?"用户名或密码错误":"" %>
	<form action="login" method="post">
		用户名:<input name="username"><br>
		密码:<input name="password" type="password"><br>
		<button>登录</button>
	</form>
    </body>
    ```
    3. 登陆后的页面(session的各种方法)
    ```html
    <body>
    <%!
    //带感叹号的叫做"声明",可以创建一些全局方法,成员变量
	public String formatDate(long dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(dateTime));
	}
    %>

    <%
    //小脚本的本质是个方法,里面的参数都是局部变量
	User user = (User)session.getAttribute("user");
    %>
    主页面<%="欢迎你 "+ user.getUsername() +"<br>"%>
    <%="sessionID: "+ session.getId() +"<br>"%>
    上次登录时间<%=formatDate(session.getLastAccessedTime()) %><br>
    <a href="logout">退出</a>

    <%
	//session可以使用的各种方法
	out.print(session + "<br>");
	out.print(session.getId() + "<br>");
	out.print("是否是新建的: " + session.isNew() + "<br>");
	out.print("创建时间: " + formatDate(session.getCreationTime()) + "<br>");
	out.print("最后访问时间: : " + formatDate(session.getLastAccessedTime()) + "<br>");
	out.print("生命时间: " + session.getMaxInactiveInterval() + "<br>");
    //对session内部值的操作
	session.setAttribute("username", "admin");
	out.print("username: " + session.getAttribute("username") + "<br>");
	session.removeAttribute("username");
	out.print("username: " + session.getAttribute("username"));
	%>
    </body>
    ```
	
    4. 注销(session的销毁)
    - 使用session.invalidate();方法
    - session和JSESSIONID的COOKIE两个都要删
    ```java
    @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //先删session,再删cookie
		HttpSession session = req.getSession();
		session.invalidate();//销毁当前session
		Cookie c = new Cookie("JSESSIONID",req.getSession().getId());
		c.setMaxAge(0);//销毁sessionID的cookie
		resp.addCookie(c);

		resp.sendRedirect("login.jsp");
	}
    ```
### 4. JSP的本质
- JSP本质上也是一个servlet,可以在里面写一些html,解决了servlet写html不方便的问题
- JSP = servlet + 模板(html)
1. Servlet生命周期
    - 实例化
    - 初始化
    - 服务
    - 销毁

2. JSP生命周期
    1. 将JSP翻译成java文件(仅第一次,在tomcat->work->Catalina->hello->org->apache->jsp)
        - C:\DEV\apache-tomcat-9.0.73\work\Catalina\localhost\TestServlet\org\apache\jsp
    2. 将java文件编译成class文件(也在上面的路径)
    3. 实例化
    4. 初始化
    5. 服务
    6. 销毁

3. Servlet的本质
    1. Servlet继承 `HttpJSPBase`,HttpJSPBase 继承 `HttpServlet`(所以JSP是一个serlvet)
    2. 最后会把小脚本和html里面的代码一行一行汇总到`__jspService()`方法里面
4. 声明与小脚本
    1. 声明`<%! %>`的里面是全局变量,定义的变量是成员变量,成员方法
    2. 小脚本`<% %>` 汇总到_jsp_Service()方法里面的局部方法,局部变量
    ```html
    <body>
	<%!
	int i = 10;//成员变量
	%>
	
	<%
	int j = 10;//局部变量变量
	out.print("i: " + ++i + "<br>");//每次调用JSP都会自增
	out.print("j: " + ++j + "<br>");//永远是11
    %>
    </body>
    ```