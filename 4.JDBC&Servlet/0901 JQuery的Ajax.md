## 0901
- AJAX
    - 使用jQuery AJAX 方法
    - 初始化下拉列表的值
- 使用FastJSON序列化对象

### 1. Ajax
- AJAX = Asynchronous JavaScript and XML（异步的 JavaScript 和 XML）。
- AJAX 不是新的编程语言，而是一种使用现有标准的新方法。
- ，网页应用能够快速地将增量更新呈现在用户界面上，而不需要重载（刷新）整个页面。
- “异步”性质，这意味着它可以与服务器通信、交换数据并更新页面，而无需刷新页面。
### 2. 原生的Ajax请求(仅学习原理)
1. 为了使用`javascript`向服务器发送HTTP请求,需要使用`XMLHttpRequest`对象
    - 所有现代浏览器均支持 XMLHttpRequest 对象
    - 用于在后台与服务器交换数据(部分刷新)
2. XMLHttpRequest 对象的三个重要的属性:
    1. `readyState`, 存储XMLHttpRequest 的状态。从 0 到 4 发生变化。
        - 0: 请求未初始化
        - 1: 服务器连接已建立
        - 2: 请求已接收
        - 3: 请求处理中
        - 4: 请求已完成，且响应已就绪(还需要结合`status`)
    2. `onreadystatechange`状态改变事件函数,每当 readyState 改变时，就会触发 onreadystatechange 事件
        - 在 onreadystatechange 事件中，我们规定当服务器响应已做好被处理的准备时所执行的任务。
    3. `status`,两种状态200:OK;400:没找到页面
        - 判断相应已就绪需要两个参数:`xhr.readyState===4 && xhr.status==200`
3. 发送请求的方法
    1. open(method,url,async)
    2. send(string)
4. 获得来自服务器的响应,使用 XMLHttpRequest 对象的 responseText 或 responseXML 属性
    - `responseText`,获得字符串形式的响应数据。
    - `responseXML`,XML形式的响应数据
```javascript
<script>
    document.getElementById('btn').onclick = function{
        let username = document.getElementById('username').value;
        let password = document.getElementById('password').value;
        //1. 创建异步对象XHR,同步是用Form发送的,异步通过这个对象发送
        let xhr = new XMLHttpRequest();
        //2. 定义onreadystatechange 事件
		xhr.onreadystatechange = function(){//状态改变事件
			console.log("客户端状态:"+xhr.readyState,"服务器状态:"+xhr.status)
			if(xhr.readyState===4 && xhr.status==200){
				console.log(xhr.responseText)//查看返回结果
				if(xhr.responseText=="1"){
					location='main.jsp'
				}else{
					document.getElementById("message").innerHTML='登陆失败'
				}
			}
		}        
        //3.发送请求
        xhr.open('get','login?username=' + username+'&password'+password);//
        xhr.send();
    }
</script>
```

### 3.使用jQuery AJAX 方法
- jquery提供了一组封装好的ajax方法(参考jquery文档)
- 底层方法:$ajax(url,[settings]),用于执行 AJAX（异步 HTTP）请求。
- 参数是个JSON,返回其创建的XMLHttpRequest对象,[settings]指的是一系列可选参数,参考文档
- 如果要处理$.ajax()的返回数据,需要用到回调函数,
    1. beforeSend,发送前
    2. error,不是200的情况
    3. dataFilter
    4. success,200
    5. complete
1. 需要哪些参数?
    1. 发送地址,url必须项目
    2. 发送方式: type,默认GET
    3. 发送的数据: data,可以是JSON或者"&par1=xx"这样的字符串
        - `data:'&username=admin&password=admin'`, 这样拼写很麻烦,最好传JSON
        - `data:{username:username,password:password}`,用F12看,JSON会自动变成&username=admin&password=admin的形式
        - jquery提供的工具方法:`serialize()`,会把`form`里带`name`的数据转换成字符串
        ```javascript
        let form = $('form').serialize()//username=admin&password=admin
        $.ajax({
            data:form,
            //...
        })
        ```
    4. 返回类型: `dataType`,不指定的默认TEXT,但也会根据响应报文的`ContentType`来判断
        - 请求和响应报文只能传`字符串`,因此传递`JSON格式的字符串(而不是JSON对象)`
        - 一般认为java对象<->json对象, 集合对象<->数组json对象
        ```JavaScript
        //1. servlet
        //JSON对象
        let user = {
            "username":"admin",
            "password":"admin"
        }
        //JSON对象字符串,使用模板字符串,加上反引号避免换行问题
        let user = `{
			"username":"admin",
			"password":"admin"
		}`
        ```
        ```java
        //2. JAVA
        //用Writer的print方法返回响应报文->success(data)
        resp.getWriter().print("{\"username\":\"admin\",\"password\":\"admin\"}")//注意双引号套双引号要加转义字符
        ```
        - dataType和ContentType的使用看下面
    5. 成功后的回调函数
        - 因为两边传的都是JSON对象的字符串,因此接收到之后也需要处理成`对象`,以方便使用
        ```javascript
        $.ajax({
            //...
            //2.1 方法2:设置dataType
            dataType:'json',//设置之后就不用手动转换了
            success(data){
					//1. 方法1:ES5提供的手动转换方法JS字符串->JS对象
					console.log(typeof data)//String
					data = JSON.parse(data)
					console.log(typeof data)//object,JS对象

					//2. AJAX提供一种方法,设置dataType或者ContentType
					//2.1 方法2:设置dataType
					console.log(data.username)
            }
        })
        ```
        ```java
        //2.2 方法3:js里面不给dataType,在java里设置ContentType,这样js会自动判断
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print("{\"username\":\"admin\",\"password\":\"admin\"}")
        ```
2. 总结
- 使用方法3
- Servlet:
```javascript
<script src="js/jquery-3.3.1.min.js"></script>
<script>
    //注册一个点击事件
    $('#btn').click(function(){
        let data = $('form').serialize()
		$.ajax({
			url:'login',
			data,
            success(data){
                console.log(data.username)
            }
        })
    })
</script>
```
- java:现在问题在于Java这边拼接字符串太麻烦
```java
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String username = req.getParameter("username");
	String password = req.getParameter("password");
    resp.setContentType("application/json;charset=utf-8");
    resp.getWriter().print("{\"username\":\"admin\",\"password\":\"admin\"}");
}
```
### 4. 使用FastJSON
- 比较有名的转换工具:Gson(Google),FastJson(Alibaba),Jackson(MVC自带)
- 把fastjson的JAR包粘在项目里
```java
	@Test
	public void test() {
        //一个对象
		Admin admin1 = new Admin();
		admin.setUsername("admin");
		admin.setPassword("1234");
		//对象集合
		List<Admin> adminList1 = new ArrayList<>();
		for(int i =0; i<5;i++) {
			Admin admin2 = new Admin();
			admin2.setUsername("admin"+i);
			admin2.setPassword("1234"+i);
			adminList.add(admin2);
		}
		
		//1.对象转JSON
		String adminStr1 = JSON.toJSONString(admin1);
		System.out.println(adminStr1);//{"password":"1234","username":"admin"}
		
		//2.JSON转对象,parseObject
		Admin admin2 = JSON.parseObject(adminStr1,Admin.class);
		
		//3.集合->字符串,也是toJSONString(),JSON数组
		String adminListStr1 = JSON.toJSONString(adminList1);
		System.out.println(adminListStr1);
        /*
        //[{"password":"12340","username":"admin0"},
        {"password":"12341","username":"admin1"},
        {"password":"12342","username":"admin2"},
        {"password":"12343","username":"admin3"},
        {"password":"12344","username":"admin4"}]
		
        */

       //4.字符串->集合,parseArray
		List<Admin> adminList2 = JSON.parseArray(adminListStr1,Admin.class);
	}
```

5. 实现下拉列表从数据库取值
```java
@WebServlet("/empSelections")
public class EmpSelectionServlet extends HttpServlet{
	
	EmpService empService = new EmpService();
	DeptService deptService = new DeptService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/**
		 * 0 部门编号
		 * 1 岗位
         * 2 经理
		 */
		List<Dept> deptList = deptService.findAllDept();
		List<String> jobList = empService.findAllSelection("job");
		List<String> mgrList = empService.findAllMgr("mgr");

		StringBuilder jsBuilder = new StringBuilder();
		jsBuilder.append("[")
				.append(JSON.toJSONString(deptList)+",")	//对象集合
				.append(JSON.toJSONString(jobList)+",")		//String集合
				.append(JSON.toJSONString(mgrList))
				.append("]");
		
		resp.setContentType("application/json;charset=utf-8");
		resp.getWriter().print(jsBuilder.toString());
	}
}
```
```html
<form id="form" action="empAdd" method="post">
    <!-- 经理名 -->
    <div class="form-group">
		<label for="mgr" class="col-sm-2 control-label">经理名</label>
		<div class="col-sm-10">
			<select class="form-control" name="mgr" id="mgr">
                <!-- 修改之前是一些option,修改之后option让js生成 -->
                <!--
                <option value="0">===请选择===</option>
                <option ${param.sal=="0~1000"?"selected":""} value="0~1000">0~1000</option>
                -->
			</select>
		</div>
	</div>
    <!-- 部门 -->
    <div class="form-group">
		<label for="deptno" class="col-sm-2 control-label">部门编号</label>
		<div class="col-sm-10">
            <!-- 有SELECT标签就行 -->
			<select class="form-control" name="deptno" id="deptno">	</select>
		</div>
	</div>
    <!-- 按钮 -->
    <div class="form-group">
		<button>添加用户</button>
	</div>
</form>
```
- 在javascript中使用EL表达式的注意点
    1. append里面最好用模板字符串,反引号
    2. EL表达式可能无法识别,需要在$前加上转义字符反斜杠`\`
```javascript
<script>
	//通过Ajax加载下拉列表
    //部门,传进来的是对象集合
	$.ajax({
		    url:'empSelections',    //请求这个servlet
		    success(data){
			    data[0].forEach(function(dept,i){   //使用ES6的遍历
				    console.log(dept)
				    //模板字符串
				    //默认认为是java代码,想用EL表达式要加斜杠
                    //append里面用反斜杠
				    $('#deptno').append(`<option value="\${dept.deptno}"> \${dept.dname} </option>`)
			})
			//$('#deptno').append()
		}
	});

    //经理,传进来的是字符串集合
    $.ajax({
			url:'empSelections',
			success(data){
				data[1].forEach(function(mgr,i){
					$('#mgr').append(`<option value="\${mgr}"> \${mgr} </option>`)
			})
		}
	})
</script>
```
