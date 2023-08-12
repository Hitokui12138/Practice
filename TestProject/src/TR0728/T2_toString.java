package TR0728;


import org.junit.jupiter.api.Test;

public class T2_toString {
	@Test
	//基本类型,包装类型->String
	public void test() {
		//基本类型不能强制转换
		int i = 10;
		//String s = (String)i;
		String s = i+"";//1.字符串拼接
		String s1 = String.valueOf(i);//2.valueOf
		
		Double d = new Double(3.14);
		String s2 = d.toString(); //3.包装类型的toString
	}
	
	@Test
	public void test2() {
		String s = "123";
		Integer i = new Integer(s);//1.使用构造函数
		Integer i1 = Integer.parseInt(s);//2.使用自带的parse方法
	}
	
	//一些特殊情况
	@Test
	public void test3() {
		Object o1 = true?new Integer(1):new Double(2.0);
		System.out.println(o1);//1.0,因为运算符会自动转成最长的类型,如果是if语句就不会有这个问题
		
		Integer i = new Integer(1);
		Integer j = new Integer(1);
		System.out.println(i==j);//false
		
		//内部有一个IntegerCache,-128~127,在这个范围内有缓存,超出这个范围是new一个新的
		Integer i1 = 1;
		Integer j1 = 1;
		System.out.println(i1==j1);//true,内部有优化,推荐使用这种方式
		Integer i2 = 128;
		Integer j2 = 128;
		System.out.println(i2==j2);//false
		
	}
}
