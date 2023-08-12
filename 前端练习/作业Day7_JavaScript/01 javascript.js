alert('JavaScript2');
// 1. 常量 5种
// 1.1 字符串常量,单引号或双引号
document.write("hello");
document.write('hello2<br>');
// 1.2 数值常量
document.write(3.14 + "<br>");
// 1.3 布尔类型常量
document.write(true + "<br>");
// 1.4 null 空类型,这个变量不指向任何地址,引用类型
document.write(null + "<br>");
// 1.5 undefined 变量未定义
document.write(undefined  + "<br>")

// 2. 变量 本质是一块内存地址
var a = 10;
a = 20;
document.write(a);
document.write(typeof(a));
document.write("<br>");

var b = "hello";
b += " world" + a;
document.write(b);
document.write(typeof(b));
document.write("<br>");

// 引用类型
var c = null;
document.write(c);
document.write(typeof(c));
document.write("<br>");

var d;
document.write(d);
document.write(typeof(d));
document.write("<br>");

// 3. 比较运算符
document.write("比较运算符");
document.write(5 == "5");//== JS会自动把两边转换成同一类型
document.write("<br>");
document.write(5 === "5");//=== 则会同时先判断类型,在比较值
document.write("<br>");

// 4. 逻辑运算符
// 4.1 对于数字类型,0 和 NaN 都是假,其他为真
// 字符串类型 空字符串""为假
// null undefined 都是假
// 其他引用类型都是true
document.write("逻辑运算符");
document.write(false && true);// 返回false



// 4.2 注意以下特殊规则,与规则,俩个都为true时,返回第二个
document.write(123 && 456);// 返回右边的456????
document.write(0 && 456);// 返回0
document.write("" && "hello1"); //返回空字符串
document.write(" "&& "hello2"); //返回右边的字符串
document.write(null && "hello3"); //返回null
document.write({} && "hello4"); //返回右边

// 4.3 或规则,两个都为true时,返回第一个
document.write(0 || 456);//返回true的那个
document.write({} || "hello4"); //返回左边
document.write("<br>");


// 短路操作
// true || num++ 右边不运算
// false && num++ 右边不运算
var num = 0;
document.write(123 || num++);// 打印123
document.write(num);// 打印0