## 0831
- JSP内部对象
	- 传递参数的方法总结
- EL表达式,JSTL
- Filter过滤器

## EL表达式,JSTL
### 1.JSP的内部对象
1. 作用域对象
- pageContext < request < session < application
```java
//1. 在JSP中作用范围从小到大
	pageContext.setAttribute("pageContext","pageContext");//仅当前页面,*跳转*后无效
	request.setAttribute("request","request");//仅在请求范围内有效,*重定向*后无效
	session.setAttribute("session","session");//会话范围,当前*浏览器*有效
	application.setAttribute("application","application");//servletContext,全局对象,网站启动时就会产生这个变量,*重启服务器*后无效

//2. 都可以使用setAttribute(),getAttribute()
	//out也是JSP自带的对象
	out.print(pageContext.getAttribute("pageContext")+"<br>");
	out.print(request.getAttribute("request")+"<br>");
	out.print(session.getAttribute("session")+"<br>");
	out.print(application.getAttribute("application")+"<br>");
```

2. 其他内部对象（一共9个）
	1. 四个作用域对象
		1. pageContext
			- 主要给自定义标签使用，`pageContext.(其他八个对象)`
		2. request, HTTPServletRequest
		3. session, HttpSession
		4. application, ServletContext
	2. 3个单独的
		1. config, ServletConfig
		2. response, HttpServletResponse
		3. out, JspWriter
			- 比起一般的那个Writer，out有缓存，只在最后或者flush()之后才会一并打印
			- 类似于BufferWriter，out的效率更高
	3. 2个用的少的
		1. page Object，没用
		2. exception， 用于错误页面，国内前后端分离，因此这个用的少


### 3. 获取/传递参数方法总结
- 之前一般都是request域传值
1. Servlet
```java
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //1.获取Request域的数据,获取JSP传过来的所有参数,返回一个Map
    Map<String, String[]> parameterMap = req.getParameterMap();//有的id有多个value
    Map<String,String> params = new HashMap<>();//把String[]拆成单个String,存到一个新的Map里
    //1.1 一个参数处理的例子
    String[] ename = parameterMap.get("ename");
	if(null!=ename && !"".equals(ename[0])) {//做非空判断
		params.put("ename", ename[0]);
	}
	//1.2 获取Session域的数据
	req.getSession().getAttribute("user", user);//session是服务器的对象,可以传各种数据

    //2.把处理好的数据传给JSP
	EmpService empService = new EmpService();
	PageBean pb = empService.page(params);//一个分页服务需要参数map
	req.setAttribute("pb", pb);//把这个分页服务对象穿给JSP
	//3.给Session域传值
	req.getSession().setAttribute("user", user);
}
```
2. JSP
	1. http传值
	```html
	<!-- 1. form传值 -->
	<form action="param" method="post"><!-- 或者Get -->
		用户名： <input name="username">
		<button>提交</button>
	</form>
	<!--2. a标签跳转传值-->
	<a href="empAll?curPaeg=${pb.curPage-1}&ename=${param.ename}"></a>

	```
	2. <% %>小脚本传值
	```java
	//传对象
	User admin = new User();
	session.setAttribute("username",admin);
	```
	3. 获取Servlet的值（为了避免空指针加了很多非空判断）
	```java
	//接受对象
	if(session.getAttribute("user")!=null){
		User a = (User)session.getAttribute("user");
	}
	//接受字符串
	String ename = request.getParameter("ename")==null?"":request.getParameter("ename");

	```
### 4. EL表达式
- Expression Language
- `${作用域/隐含对象.对象名.参数名}`
- 其中参数名其实是`隐式的get()`方法，要求必须有get()方法，且第一个字母小写
- 无需非空判断，若为空则不显示
1. 作用域
- 表示的就是`String username = (String)session.getAttribute("username");`
	1. ${pageScope.username}
	2. ${requestScope.username}
	3. ${sessionScope.username}
	4. ${applicationScope.username}
- 可以不写，默认从作用域小到大去查
2. 隐含对象
	1. 四个作用域对象 XXXScope
	2. 与输入有关的两个
		1. param
			- `request.getParameter(String)`,只能回传String
		2. paramValues
			- 和上面差不多，可以传String[]
			- 需要结合JSTL来遍历
	3. 其他
		1. cookie
			- `request.getCookies`
			- 例：`${cookies.JSESSIONID.value}`
		2. header
			- `header.Host`
			- `headerValues.Cookie`
		3. pageContext
			- `${pageContext.request.contextPath}`
3. 运算
- 可以自动把字符串转成数值类型`${curPage + 1}`
- 但是不支持自增自减，错误写法`${curPage++}`

### 5. JSTL标签
- JavaServer Pages Standard Tag Library
- 使用前必须先导入JSTL标签库
- `<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>`
- core表示核心标签库，前置名称为c
1. 初始化对象（JSP标签）
```html
<jsp:useBean id="user" class="com.entity.User" scope="session"></jsp:useBean>
<jsp:setProperty name="user" property="username" value="admin"/>
```
2. c:set
```html
<!--一般的值-->
<!--别写成${i}+1了，结果个字符串“1+1” -->
<c:set var=i value="1"></c:set>
<c:set var=i value="${i+1}"></c:set>
${i}
<!--对象-->
<!--需要事先初始化-->
<c:set target="user" property="username" value="haha">
${user.username}
```
3. c:remove
- 可以删对象
```html
<c:remove var="user">
```

4. c:if
- 没有else，只能一个一个写
```html
<c:if test="${user!=null}">
	欢迎你 ${user.username}<br>
</c:if>
```
5. c:choose,c:when
- switch功能
```html
<c:choose>
	<c:when test="${user.username=="admin"}">
		管理员用户<br>
	</c:when>
	<!-- .... -->
</c:choose>
```

6. c:forEach
- 没有break，continue功能
- 有个类似于伪列的功能
```html
<!-- fori -->
<c:forEach begin="1" end="10" var="i">
	${i}<br>
</c:forEach>
<!-- iterator -->
<c:forEach items="${users}" var="u" varStatus="status" >
	<li>${status.count}--${status.index}--${u.username}</li>
</c:forEach>
```

### 6.Filter过滤器
### 7.用Javascript修改高度宽度
### 绝对路径与相对路径


	
