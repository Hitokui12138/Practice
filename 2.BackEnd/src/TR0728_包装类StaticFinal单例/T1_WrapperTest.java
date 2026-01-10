package TR0728_包装类StaticFinal单例;

import org.junit.jupiter.api.Test;

public class T1_WrapperTest {

	// 需要导入JUNIT5
	@Test
	// 基本类型->包装类型
	public void test() {
		int i = 10;
		Integer i1 = new Integer(i);//原始方法
		Integer i2 = 10;//JDK1.5 自动装箱
		System.out.println(i1);
		
		Double d = new Double("123");//一般都有 数字,纯数字字符串 两个构造函数
		
		Boolean b = new Boolean("abc");//只要不是"true"就是false,有内部优化
		System.out.println(b);//false
	}
	
	@Test
	//包装类->基本类型
	public void test1() {
		Integer i = new Integer(10);
		int i2 = i.intValue();//原始方法
		int i3 = i;//JDK1.5自动拆箱
		
		Boolean b = new Boolean(true);
		Boolean b1 = true;
		boolean b2 = b.booleanValue();
	}
	
	@Test
	public void test2() {
		calc(3.14);//JDK1.5提供的自动装箱
	}
	//想写一个int,double等等都能传的方法
	public void calc(Object o) {
		
	}
}
