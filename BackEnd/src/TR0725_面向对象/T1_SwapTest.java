package TR0725_面向对象;


public class T1_SwapTest {
    public static void main(String[] args) {
        //练习1
        int a = 10;
        int b = 10;
        method(a,b);
        //要求调用method之后，a=100，b=200
        System.out.println(a+":"+b);


    }
    /*
    用对象
     */
    public static void method(int a, int b){
        //如果只是设置a=100，则只是修改了method（）栈内存的值
        //方法
        System.out.println("100"+":"+"200");
        System.exit(0);//退出虚拟机
        //就不行呗
    }
}
