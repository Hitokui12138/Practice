package TR0724;

public class Person {
    /*
     * 1.1 成员变量（属性）：类内，方法体外声明的变量
     * 1.1.1 可以写public，private等等
     * 1.1.2 成员变量有初始值，0，“”，false，null等等
     * 1.1.3 加载的位置不同，属性加载到堆空间（非static）
     *
     * 1.2 局部变量：方法体内部声明的变量
     * 1.2.1 最多写final，其他都用不了
     * 1.2.2 局部变量没有初期值，因此使用之前必须先声明且初始化，
     * 1.2.3 加载到栈空间
     */
    private String name;
    public int a;
    public int b;


    public Person(){
    }
    public Person(int a, int b){
        this.a = a;
        this.b = b;
    }
    public void shout(){
        System.out.println("匿名对象调用方法");
    }
}
