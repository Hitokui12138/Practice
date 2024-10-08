## 0828
- GET与POST
	- 区别
	- Servlet的实现
	- 查看结果的方式
- JSP
	- 小脚本与表达式
	- 一览画面
	- 增/删/改的实现
### 1.HTTP请求,响应消息的结构
### 2.telnet与浏览器的本质
### 3.读取与修改报文
- 看一下HttpMessageServlet的几个方法
- 获取请求方式,请求地址等等
### 4.get与post的
- 第九周周考
	- 传输方式
	- 可传输的数据类型
	- 容量大小
	- 安全性
1. get与post
- GET:  `?param1=abc&param2=xyz`
    - 传输数据量有限,一般在1kb以下
- POST: 用报文传值
    - 设置Content-Type为表单传值
    - 理论上可以传无限大
    - 更安全
2. 举例
- 注意servlet读取从表单中获取的值的方法
```java
@WebServlet("/param")
public class ParamServlet extends HttpServlet{
	/**
	 * 1. GET
	 * 重写doGet()方法
     * 默认提交方式就是GET
	 * 405方法不允许错误
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.1 JSP的编码?背一下
		resp.setContentType("text/html;charset=utf-8");
        //1.2 获取表单参数,然后打印出来,只有字符串和字符串数组两种类型
        //获取一个参数
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		//获取一个字符串数组
		String[] hobbys = req.getParameterValues("hobby");
		//打印
		out.print("用户名:"+username);
		out.print("<br>密码:"+password);
		for(String hobby : hobbys) {
			out.print("<br>爱好:"+hobby);
		}
	}

	/**
	 * 2. POST
	 * 重写doPost()方法
	 * 注意POST使用请求报文传值的,默认是欧洲编码,因此也需要设置编码
	 * 可以通过在post里调用doGet()来减少代码冗余
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//2.1 在GET的基础上还需要请求报文的编码,获取参数的方法和GET完全一致
		req.setCharacterEncoding("utf-8");
		doGet(req,resp);
	}
}
```
3. JSP的表单和提交方法
- 上面那个servlet的值由jsp的表单提供,因此应该调用的是JSP文件`http://localhost/TestServlet/param.jsp`
```html
<body>
<h1>1. GET方式</h1>
<!-- 1.1 直接通过超链接写死来GET传值 -->
<a href="param?username=admin&password=123">Get方式</a>
<!-- 1.2  表单Get传值,注意method, 以及Action(Servlet的名字) -->
<form action="param" method="get">
    用户名:<input name="username"><br><!-- 在表单域中,不加name无法提交不能传值 -->
    密码:<input name="password" type="password"><br>
    爱好:<input type="checkbox" name="hobby" value="0">抽烟<!-- CheckBox有三个参数 -->
    <input type="checkbox" name="hobby" value="1">喝酒
    <input type="checkbox" name="hobby" value="2">烫头
    <button>提交</button>
</form>

<h1>2. POST方式</h1>
<!-- POST不能用URL传值 -->
<!-- 注意表单的method要改成post -->
<form action="param" method="post">
    用户名:<input name="username"><br>
    密码:<input name="password" type="password"><br>
    爱好:<input type="checkbox" name="hobby" value="0">抽烟
    <input type="checkbox" name="hobby" value="1">喝酒
    <input type="checkbox" name="hobby" value="2">烫头
    <button>提交</button>
</form>
</body>
```
- 表单Get提交结果: `param?username=admin&password=1234&hobby=0&hobby=1`
- 表单Post提交结果:`http://localhost/TestServlet/param`
    - `***`具体传值要看F12->网络Network->负载payload
    - 也可以在网络Network->Header里看到`Content-Type: application/x-www-form-urlencoded`,表示表单传值
### 补充
### 5. 一览画面
- 取得所有emp数据并显示在网页上
- DAO定义SQL语句,Servelet调用,然后把结果list传给JSP
	1. DAO
	2. Servlet,如何将ArrayList发送给JSP?
	3. JSP
1. DAO
```java
//sql语句
SELECT * FROM emp ORDER BY empno DESC
//最终返回一个ArrayList<Emp>即可
```
2. Servlet(使用request.setAttribute()传对象)
- 查询画面用get就好
```java
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	resp.setContentType("text/html;charset=utf-8");
	//获取这个ArrayList<Emp>
	ArrayList<Emp> findAllEmp = new EmpDao().findAllEmp(params);
	/**
	 * 把ArrayList传送到JSP 
	 * 1. request本身是个Map,可以以键值对的形式存值
	 * 2. setAttribute(),第二个参数是个object
	 */
	req.setAttribute("empList", findAllEmp);

	//先学习这一种 请求转发 的方法,Servlet->JSP
	RequestDispatcher rd = req.getRequestDispatcher("empAll.jsp");//获取req的转发对象
	rd.forward(req,resp);//请求转发,从servlet跳转到JSP,同时带上req和resp两个对象
}
```
3.JSP,动态显示一个表格
- 小脚本`<% java代码; %>`,有分号
- 表达式`<%=ename%>`,里只存放一个值,不要分号
```html
<% 
	//1. JSP接收Servlet传值
	//1.1 使用request取得一个对象,request.getAttribute("")
	ArrayList<Emp> empList = (ArrayList<Emp>) request.getAttribute("empList");
	//1.2 对于一个值,request.getParameter("ename")
	String ename = request.getParameter("ename") == null ? "" : request.getParameter("ename");
%>

	<table class="table table-bordered"">
		<tr>
			<th>员工ID</th>
			<th>员工姓名</th>
			<th>职位</th>
			<th>经理</th>
			<th>入职日期</th>
			<th>工资</th>
			<th>奖金</th>
			<th>部门</th>
		</tr>
		<%
			List<Emp> empList = pb.getList();
			for (Emp emp : empList) {
		%>
		<!-- 这个叫表达式，替代out.print的，下面这个就是JSP的最佳实践 
			表达式里不要有分号，小脚本里一定要写分号
		-->
		<tr>
			<td>
				<%
					out.print(emp.getEmpno());
				%>
			</td>
			<td><%=emp.getEname()%></td>
			<td><%=emp.getJob()%></td>
			<td><%=emp.getMgr()%></td>
			<td><%=emp.getHiredate()%></td>
			<td><%=emp.getSal()%></td>
			<td><%=emp.getComm()%></td>
			<td><%=emp.getDept().getDname()%></td>
			<!-- 删除部分具体看后面 -->
			<td><a href="empDetail?empno=<%=emp.getEmpno()%>">修改</a>
				<button
					onclick="if(confirm('确认要删除吗?'))location='empDel?empno=<%=emp.getEmpno()%>'"
					type="button" method='get'>删除</button>
			</td>
		</tr>
		<%
			}
		%>
	</table>
```

### 6. empAdd/empDel
- 语法一直报错的问题:prtm.executeUpdate(),括号里不要带sql否则前面前面设值都没了
- 在一览画面里加一个按钮用来增加/删除用户
- 按钮有a标签和button标签两种写法
- 与上面相反,JSP传值给Servlet
1. empAdd
- 按下按钮后跳转到form画面,输入参数后调用执行Add语句的Servlet
- 表单验证
	1. DAO
	```sql
	insert into emp values(0,?,?,?,now(),?,?,?,1)
	```
	2. JSP
	```html
	<!-- action和method注意一下 -->
	<form id="form" action="empAdd" method="post">
		<div class="form-group">
			<label for="ename" class="col-sm-2 control-label">员工姓名</label>
			<div class="col-sm-10">
				<input class="form-control" name="ename" id="ename" placeholder="员工姓名"><!-- 需要id来便于选择 -->
				<span id="enameMessage"></span><!-- 如果出错就填充这个标签 -->
			</div>
		</div>
		<div class="form-group">
			<button>添加用户</button>
		</div>
	</form>
	<!-- JQuery -->
	<script src="js/jquery-3.3.1.min.js"></script>
	<script>
		$('#form').submit(function(){
			return checkedEname()//返回结果为true时才可以提交
		})
		function checkedEname(){
			//正则表达式的使用方法
			let regEname = /[\u4e00-\u9fa5a-zA-Z]{2,8}/
			let ename = $('#ename').val()
			console.log(ename);
			if(regEname.test(ename)){
				$('#enameMessage').html('<span style="color:green">员工姓名合法</span>');
				return true;
			}else{
				$('#enameMessage').html('<span style="color:red">2~8位字母汉字</span>');
				return false;
			}
		}
	</script>
	```
	3. Servlet
	- 注意JSP只能传String和String数组,需要相应的转换方法
	- 登录成功时,希望能返回上一页(一栏画面),失败时希望能回到Add画面(上一个画面)
	- 通过PrintWriter直接执行script
	```java
	@WebServlet("/empAdd")
	public class EmpAddServlet extends HttpServlet{
		EmpDao empDao = new EmpDao();	
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");//报文的编码

		String ename = req.getParameter("ename");
		//...获取其他参数
		Emp emp = new Emp();
		emp.setEname(ename);
		//...设置emp对象
		int rows = empDao.addEmp(emp);

		/**
		 * 1.成功: 返回一栏画面
		 * 2.失败: 返回上一个页面(Add画面): history.go(-1) / history.back 
		 * 但是要注意之前通过servlet执行的jsp文件,如果直接跳转jsp的话可能会发生空指针异常
		 */
		PrintWriter out = resp.getWriter();
		if(rows==1) {
			out.print("<script>alert('添加成功');location='empAll'</script>");
			}else {
			out.print("<script>alert('添加失败');history.go(-1)</script>");
			}
		}	
	}
	```
2. 删除与修改
- 主要看一下button和a标签两种写法
```html
	<!-- 删除部分具体看后面 -->
	<td>
		<!-- 1.修改 
		a标签使用 href="empDetail?empno=<%=emp.getEmpno()%
		来唤起Servlet并通过get方法传值
		-->
		<a href="empDetail?empno=<%=emp.getEmpno()%>">修改</a>
		<!-- 2.删除 
		表格之外的button标签通过onclick事件来唤起servlet
		-->
		<button
			onclick="if(confirm('确认要删除吗?'))location='empDel?empno=<%=emp.getEmpno()%>'"
			type="button" method='get'>删除</button>
	</td>
	<!-- 3. 补充
	一般表格内的button默认是sumbit,只需要在form里面定义action和method -->
	<form id="form" action="empAdd" method="post">
		<button>提交</button>
	</form>
```
### 7. Servlet的优化(仅学习思路)
- 一个表的增删改查各一个Servlet类,多个表甚至更多,希望能直接在一个里面实现
- Servlet <-- HttpServlet
- 调用一个Servlet,本质上是调用service方法
- 在Service方法里,先判断调用方式,GET就调用doGet方法,Post就调用doPost方法
1. 建一个BaseServlet类
- 这个父类不知道子类有哪些方法->`反射`
```java
public class BaseServlet extends HttpServlet{
	//重写最底层方法
	@Override
	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
		//把参数强制转换成子类,可以参考一下原本的service方法也是这样做的
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		/*
		 * 取得URL最后一部分, 比如/dept/Add的Add
		 * getServletPath(): /dept
		 * getRequestURL(): http://localhost/TestServlet/dept/add
		 * getRequestURI(): /TestServlet/dept/add
		 */	
		String uri = request.getRequestURI();
		String methodName = uri.substring(uri.lastIndexOf("/")+1);

		/*
		 * 通过反射调用这个方法(在运行时获取并执行这个方法)
		 */
		try {
			Method m = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			m.invoke(this,request,response);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
```
2. 比如DEPT表,建一个DeptServlet继承上面的BaseServlet
- 这个类没有重写service方法,因此每次访问`/dept/XXX`都会调用父类的service方法
```java
//表示各种以"/dept/"开头的连接都会调用这个servlet
@WebServlet("/dept/*")
public class DeptServlet extends BaseServlet{
	protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("添加");
	}
	
	protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("修改");
	}
}
```
3. 总结
- req.getRequestURL(): `http://localhost/TestServlet/dept/add`
- req.getRequestURI(): `/TestServlet/dept/add`
- req.getServletPath(): `/dept`