package TR0724_面向对象;

/**
 * JDK1.5 新特性
 * 可变形参 Varags，variable　number of arguments
 * 可以定义多个与实参相匹配的形参，以一种更简单的方式传递个数可变的实参
 * 1. 数据类型 。。。 变量名，相当于【】
 * 2.传入的参数可以是0个
 * 3.与【】不能构成重载
 * 4.可变形参必须声明在参数的最末尾，因为可以直接（1，2，3，4）不放到后面会混淆
 * 5.一个方法最多只能声明一个可变形参，
 *
 * import static
 *
 * 自动装箱
 */
public class T2_Varargs {
    //传入多个统一类型的变量
    public static void test1(int[] arr) {
    }

    //三个点和中括号一样表示数组，因此不能构成重载
    public static void test2(int... arr) {
    }

    public static void main(String[] args) {
        T2_Varargs v = new T2_Varargs();
        int[] arr = {1, 2, 3, 4};
        //1.5 之前
        v.test1(arr);
        //v.test1(); 不可以不写
        //v.test1(1,2,3,4); 不可以直接1，2，3，4
        //1.5 之后
        /*
        1. 可以不初始化，直接传null
        2. 也可以直接test2（1，2，3，4）
         */
        v.test2();//可以不写
        v.test2(1, 2, 3, 4);//可以传1，2，3，4 语法糖
    }
}
