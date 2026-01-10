1. 基础部分1~11
    1. 什么是JS
        1. JS的三个部分
            1. ECMAScritp,JS的核心
                1. 2015年发布的ECMA第六版被称为ES6或ES2015
            2. DOM,文档对象模型
                1. 是一组应用接口api,可以直接控制整个页面的节点
                2. 可以吧html代码转换成一组分层的节点
            3. BOM,浏览器对象模型,用来操作浏览器窗口
                1. 比如Windows
    2. HTML中的JS
        1. 使用script标签的方法
            1. 建议在body里面的最下面使用script标签
        2. 行内脚本金额外部脚本
            1. 行内脚本:直接吧js写到html里面
            2. 外部标签,使用外链
        3. js不可用时,使用noscript标签保证用户体验
    3. 语言基础
        1. typeof 操作符,数据的类型
            1. undefined,未定义
            2. boolean,布尔值
            3. string,字符串
            4. number,树枝
            5. object,对象
                1. typeof null === 'object'
            6. function,函数
            7. symbol符号
        2. 位操作符号
            1. 以32位整数为例
                1. 10010 = 2^4*1 + 2^1*1 = 16+2 =18
            2. 按位操作
                1. 按位非
                    0. 一个数值进行计算
                    1. let num2 = ~num1
                    2. 1-》0,0-》1
                2. 按位与
                    1. 两个数值进行计算
                3. 按位或 |
                    1. 两个数值
                4. 按位异或 ^
                    1. 有且仅有一位是1(两个数字不同)的时候为1
                    2. let result = 25^3
                    |第一个|第二个|结果|
                    |----|----|----|
                    |1|1|0|
                    |1|0|1|
                    |0|1|1|
                    |0|0|0|
                5. 左移 << 
                    1. oldValue << 5,
                6. 有符号右移 >>
                    1. 会保留相应的符号
                7. 无符号右移 >>>
                    1. 不保留符号
        3. with语法
            1. 改变一段代码的作用域空间
                ```javascript
                //下面这段都使用了location
                let qs = location.search.substring(1);
                let url = location.href;
                //下面这段使用with作简化
                with(location){
                    let qs = serach.substring(1);
                    let url = href;
                }
                ```
            2. 在with内部
                1. 认为每个变量都是局部变量
                2. 局部变量没找到,再搜索location对象,看看有没有同名属性
    4. 变量,作用域,内存
        1. 变量
            1. 按类型划分
                1. 原始值
                    1. Undefined
                    2. Null
                    3. Boolean
                    4. Number
                    5. String
                    6. Symbol
                2. 引用值
                    1. 对象
                    2. 函数
                    3. ...
            2. 按内存划分
                1. 栈内存
                    1. 原始数据类型都会般存在栈内存里
                2. 堆内存
                    1. 引用数据类型会放到堆内存里面
                    2. 栈内存中只保存这个对象的引用
        2. 作用域上下文
        3. 内存,垃圾回收机制
            1. 引用计数
                1. 当引用数 = 0的时候会被回收
                2. 在某些循环引用中会导致引用数总是大于0,最终可能导致内存泄漏
            2. 标记清除
                1. 标记当前变量是否可以被访问,不可访问时回收
    5. 基本引用类型
        1. 概念
            1. 基于一些基本的构造函数
                1. let now = new Date()
        2. 四种常用的
            1. Date
            2. RegExp
            3. 原始包装类,Boolean,Number,String
            4. 单例内置对象
                1. Global
                    1. eval()方法,ECMAScript解释器
                        1. eval("console.log('hi')")
                        2. 接收一个字符串,字符串会被当作js代码运行
                    2. window
    6. 集合引用类型
        1. 对象Object
        2. 数组Array
        3. 其他引用类型
            1. ArrayBuffer,在内存中分配指定数量的空间 
            2. DateView,配合ArrayBuffer使用,用于读写ArrayBuffer
            3. 定型数组,包装一下ArrayBuffer
                1. Int32Array(abuf)
            4. Map
                - 
                    ```javascript
                    const m = new Map();
                    m.set("Key","Value");
                    m.get("Key");
                    ```
            5. WeakMap
                - 表示不会影响js的垃圾回收机制
            6. Set 集合,不重复的数组
                ```javascript
                const s = new Set();
                s.add("ABC")
                ```
            7. WeakSet
    7. 迭代器与生成器,都是ES6新增的
        - 目的是为了更方便实现迭代
        1. 迭代器
            1. 概念
                1. 按顺序反复多次执行,有明确的终止条件
                2. 比如for循环
                3. 由于循环增多会导致代码复杂,因此用迭代器模式
            2. 迭代器模式
                1. 是一种方案
                2. 可迭代对象:实现了Iterator接口,且可以通过Iterator接口消费
                ```javascript
                //数组是一个可迭代对象
                let arr = ['foo','bar'];
                let it = arr[Symbol.iterator]();//迭代器的工厂函数
                console(it.next());
                ```
        2. 生成器
            1. 概念
                1. 本质上是一个函数
                ```javascript
                //生成器函数声明
                function * generator(){
                    //...
                }
                //但是箭头函数不能定义生成器函数

                const g = generator();
                console.log(g.next);
                ```
                2. 具有next方法
            2. yield关键字,配合迭代器的next停滞和开始使用
                ```javascript
                function * generator(){
                    yield 'foo';
                    yield 'bar';
                    return 'baz';
                }
                ```
    8. 对象,类,面向对象编程
        1. 对象的创建过程
            1. 工厂模式,一个工厂方法,可以返回一个对象 
            2. 构造函数模式,通过new来获得实例
            3. 原型模式,每个函数都包涵一个prototype的实例属性
                ```javascript
                function Person(){}

                Person.prototype.name = "Nichoas";//这就是一个原型

                let p1 = new Person();
                console.log(p1.name);//类似于final static?
                ```
            4. 对象迭代
                ```javascript
                Person.prototype = {
                    constructor: Person,//要确保构造函数的指向
                    name:  "Nichoas"
                };
                ```
        2. js的继承机制
            1. 主要是通过原型链的方法,原型继承多个引用类型的属性和方法
                ```javascript
                //super
                function SuperType(){
                    this.property = true;
                }
                SuperType.prototype.getSuperType = function (){
                    return this.property;
                }
                //sub
                function SubType(){
                    this.property = false;
                }
                SubType.prototype = new SuperType();//继承
                //这样子类实例就可以用父类的方法了
                ```
        3. js中的类(推荐使用)
            1. 通过类进行继承 ES6之后新增的语法糖
                1. 类声明
                    - class Person{}
                2. 类表达式
                    - const Animal = class{}
    9. 代理与反射
        1. 代理proxy
            1. 代理目标对象的抽象
        2. 反射reflect 
    10. 函数,也是一个function类型的对象
        1. 函数的定义
            1. function
                ```javascript
                function sum(num1,num2){
                    return num1 + num2;
                }
                ```
            2. 函数表达式
                ```javascript
                let sum = function(num1, num2){
                    return num1 + num2;
                }
                ```
            3. 箭头函数
                ```javascript
                let sum = (num1,num2) => {
                    return num1 + num2;
                }
                ```
            4. Function构造函数(不推荐)
        2. 递归,自身调用自身的函数
            1. if(i = 0){...}
            2. func(i-1)
        3. 闭包,引用了另一个函数作用域中变量的函数
            ```javascript
            function func1(propertyName){
                //func1返回一个函数
                return function(obj1){
                    let value1 = obj1[propertyName];
                    //...
                }
                //在func2中使用了func1作用域中才有的变量,因此func2是一个闭包函数
            }
            ```
    11. 期约与异步函数
        1. 异步编程
            1. 早期JS只支持通过回调函数来进行异步操作
                1. 会发生像下面这样的多个异步函数的串联(回调地狱)
                ```javascript
                A(function(res){
                    console.log(res);
                    B(function(res){
                        console.log(res);
                    })
                })
                ```
        2. 期约Promise,ES6
            1. 解决回调地狱
                1. 三种状态
                    ```javascript
                    //1. 创建Promise实例
                    return new Promise((resolve, reject)=>{
                        //2.刚进入Promise,待定状态Pending
                        console.log('执行A接口的逻辑');
                        setTimeout(()=>{
                            if(isA){
                                //兑现Resolve
                                resolve('接口A执行成功');
                            }else{
                                //拒绝Reject
                                reject('接口A执行失败');
                            }
                        },5000);
                    })
                    ``` 
                2. Pending,Resolve,Reject    
            2. 两个常用的实例方法:then,catch
                ```java
                let p1 = Promise.resolve();
                p1.then(()=>{
                    console.log("resolve then")
                })
                let p2 = Promise.reject();
                p2.catch(()=>{
                    console.log("reject catch")
                })
                ```
        3. 异步函数async和await
            1. 主要是配合Promise使用的
            2. async声明异步函数,await必须在异步函数里面使用
                ```javascript
                //1.声明异步函数
                async function foo(){}
                let bar = async function foo(){};
                let buz = async()=>{}
                ```
        4. 对比图
            - 看一下另一个文档
2. api部分,浏览器~DOM,12~20
    1. BOM
        1. window对象(核心)
            1. 两个身份
                1. ECMAScript的`Global`对象
                2. 浏览器窗口的`JavaScript`对象
                ```javascript
                //很多API都是window的API
                var age = 29;//全局变量,let和const的不会被放到window里面
                console.log(window.age);
                ```
        2. location对象
            1. 提供当前窗口加载文档的信息以及通常的导航功能
            2. location.很多种属性
        3. navigator对象
            1. 浏览器的类型对象
            2. 每个属性都代表当前浏览器的的属性
        4. history对象
            1. 当前窗口被使用以来,用户的导航记录
                ```javascript
                //后退一页
                history.go(-1);
                hitsory.back();
                //前进一页
                history.go(1);
                history.forward();
                ```
    13. 客户端检测
        1. 能力检测
            1. 检测浏览器是否具有某种功能,有的话再使用这个功能
        2. 用户代理检测
            1. 通过浏览器的用户代理字符串来确定使用的是什么浏览器
        3. 软件硬件检测
            1. 浏览器,操作系统,硬件以及周边设备信息
    14. DOM
        1. 节点层级
            1. HTML和XML可以通过DOM表示为层级结构
            2. 标签,文本,属性,都可以被称为一个节点
        2. DOM编程
            1. 通过JS操作DOM
                1. 创建标签并插入HTML里面
                    ```javascript
                    let s = document.createElement("script");   //创建标签
                    s.src = "foo.js";   //设置标签的属性
                    document.body.appendChild(script);  //插入标签

                    //效果等同于
                    <script src="foo.js"></script>
                    ```
        3. MutationObserver接口
            1. 可以在DOM被修改时异步执行回调
                1. 构造函数
                    - new MutationObserver()
                2. observe方法
                    - 监听指定的DOM,然后调用相应的方法
    15. DOM拓展
        1. Selectors API,获取DOM的方法(3个)
            1. querySelector(),获取单个DOM
                ```javascript
                let body = document.querySelector("body");
                let myDiv = document.querySelector("#myDiv");
                ```
            2. querySelectorAll()
                ```javascript
                let ems = document
                ```
            3. matches()
                ```javascript
                if(document.body.matches){}
                ```
        2. HTML5 DOM拓展(很常用)
            1. CSS类拓展
                1. document.getElementByXX()
                2. 
            2. 焦点管理
                - dom.focus()
            3. HTMLDocument拓展
                - document.head
            4. 字符集属性 
                - document.characterSet
            5. 自定义数据属性
                - data-
            6. 插入标记
                - innerHTML
            7. scrollIntoView()
                - document.getElementById('nav-footer').scrollIntoView();
                - 滚动到选中元素的位置
    16. DOM2,DOM3
        1. DOM2到DOM3的变化
        2. 操作样式的DOM API
            1. 任何支持style属性的HTML元素,在JS中都会有一个对应的style属性
                1. background-image   style.backgroundimage
                2. color    style.color
                3. display style.display
                4. font-family style.fontFamily
        3. DOM遍历与范围
            1. 对DOM结构的深度优先遍历,至少允许炒两个方向遍历
                1. 以深度优先,也就是说会先遍历子标签,全部遍历玩后,再遍历下一个同级标签
    17. 事件
        1. 事件流
            1. 事件冒泡
            2. 到达目标
            3. 事件捕获
        2. 使用事件处理程序
            1. 事件
                1. 用户或浏览器执行的某种动作,click,load 
            2. 事件处理程序
                1. 调用的函数onClick(),onLoad() 
        3. 事件对象
            1. event,作为事件处理函数的形参,可以拿到事件类型等等
        4. 事件类型
            1. UIEvent
            2. FocusEvent
            3. MouseEvent
            4. WheelEvent
            5. InputEvent
            6. KeyboardEvent
            7. CompositionEvent
        5. 事件委托
            1. 背景
                1. 每个函数都是一个对象,函数越多,占用内存越大
                2. 指定事件访问DOM的次数会导致页面加载延迟
            3. 尝试用一个事件处理函数处理多个同类标签
                1. 子节点会把事件冒泡给父节点,因此可以只监听父节点的行为
                2. 通过event来判断当前是哪个节点被触发了
    18. 动画与Canvas图形
    19. 表单脚本
        1. 表单基础
            1. 获取表单,let from = document.getElementById("form1");
            2. 表单提交
            3. 表单验证
            4. 重置表单
        2. 一些表单验证和交互方法
        3. 富文本编辑
    20. 
3. 开发实用技术
