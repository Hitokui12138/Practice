package TR0728_包装类StaticFinal单例;
/**
 * 1.类的成员之四:代码块(用的不多)
 * 用来初始化类和对象的信息
 * 有两种:静态代码块,非静态代码块
 * 2.1非静态代码块
 * 2.1.1 内部可以输出语句
 * 2.1.2 随着[对象的创建]执行
 * 2.1.3 每创建一个对象,就执行一次非静态代码块
 * 2.2 作用:
 * 2.2.1.在创建对象时,对对象的属性进行初始化
 * 2.2.2.如果一个类中定义了多个非静态代码块,则按照声明的先后顺序执行
 * 2.2.3.非静态代码内可以调用静态属性,静态方法
 * 
 * 3.静态代码块
 * 内部可以输出语句
 * 随着[类的加载]而执行,且只执行一次
 * 作用:
 * 初始化类的信息
 * 如果一个类中定义了多个静态代码块,则按照声明的先后顺序执行
 * 先于非静态代码块执行
 * 
 * 4.由父及子,静态先行
 * 父静->子静->父非静->子非静->子构造
 * @author Admin
 *
 */
public class T9_Bloak {
	public static void main(String[] args) {
		Per p1 = new Per();
		System.out.println(p1.i);//30
		Per p2 = new Per(50);//
		System.out.println(p2.i);//50
		System.out.println(Per.j);//10
		System.out.println(Per.j);
		System.out.println(Per.j);
	}
}

class Per{
	{
		System.out.println("非静态代码块1");
		i = 20;
	}
	static {
		System.out.println("静态代码块1");//注意这个是有先后顺序的,且只执行一次
		j = 20;
	}
	int i = 10;
	static int j = 10;


	public Per() {
		i = 30;
	}
	public Per(int i) {
		this.i = i;
	}
	{
		System.out.println("非静态代码块2");
		i = 20;
	}
}
