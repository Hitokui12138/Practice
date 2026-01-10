package TR0728_包装类StaticFinal单例;
/**
 * 1.设计模式
 * 设计模式是一种代码风格,编码规范,Java有23种设计模式 由GOF四个人研究出来的
 * 1.1 创建型5
 * 1.2 结构性7
 * 1.3 行为型11
 * 
 * 2.单例模式 希望一个类的实例在程序里只有一个(网站计数器,系统回收池)
 * 2.1 首先构造方法应该是私有的,使用类里面的静态方法返回该对象
 * 2.2.1 饿汉模式: 加载类时直接new实例化,静态方法返回这个实例
 * 2.2.2 懒汉模式: 加载类时仅private声明,静态方法先判断是否存在,没有的话先new再返回

 * 
 * 2.3 饿汉式
 * 	坏处:对象加载时间长,(一上来就new了这个实例,但不一定会用,因此这个对象可能一直占用内存)
 * 	好处:饿汉式是线程安全的
 * 2.4 懒汉式(因为缺点能避免,因此推荐使用)
 * 	好处:延迟对象的创建,仅在使用时创建
 * 	坏处:线程不安全(两个线程同时进入if(null)中,这样会导致产生多个对象)->到多线程时在修改
 * 
 * 
 * @author Admin
 *
 */
public class T6_Singleton {
	public static void main(String[] args) {
		Bank b1 = Bank.getInstance();
		Bank b2 = Bank.getInstance();
		System.out.println(b1 == b2);//true
		
		Order o1 = Order.getInstance();
		Order o2 = Order.getInstance();
		System.out.println(o1 == o2);//true
	}
}
/*
 * 饿汉式:加载类时直接new实例化,静态方法返回这个实例
 */
class Bank{
	//1.私有化构造函数
	private Bank() {
		// TODO Auto-generated constructor stub
	}
	//2.内部创建该对象
	private static Bank bank = new Bank();
	//3.提供公共静态方法
	public static Bank getInstance() {
		return bank;
	}
}

/*
 * 懒汉式:加载类时仅private声明,静态方法先判断是否存在,没有的话先new再返回
 */
class Order{
	//1.私有化构造函数
	private Order() {
		// TODO Auto-generated constructor stub
	}
	//2.内部 仅声明 该对象
	private static Order order = null;
	//3.提供公共静态方法
	public static Order getInstance() {
		if(order==null) {
			order = new Order();
		}
		return order;
	}
}

