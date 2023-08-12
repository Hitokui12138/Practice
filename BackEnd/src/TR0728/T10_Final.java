package TR0728;
/**
 * 1. final class 不能被继承
 * String System StringBuffer等等
 * 2. final method() 不能被重写
 * Object的getClass()方法
 * native 本地方法调用,比如访问本地字节码文件(底层逻辑是使用C语言)
 * 3. final 修饰变量 初始化后不能被修改,被称为常量
 * 3.1 成员变量: 考虑赋值的位置:显示初始化,代码块中初始化,构造器中初始化
 * 3.2 局部变量: 一旦赋值后,只能在方法体内使用此形参,且不能重新赋值
 * 3.3 也可以形容形参,也是只能读不能改,如果是引用类型形参,则不能重新new,里面的值是可以改的
 * 
 * static final 全局常量(因为要赋予初期值)
 * 
 * @author Admin
 *
 */
public class T10_Final {

}
