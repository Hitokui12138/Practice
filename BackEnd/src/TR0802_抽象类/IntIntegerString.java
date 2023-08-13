package TR0802_抽象类;

public class IntIntegerString {
	public static void main(String[] args) {
		//1.基本
		int i = 1;
		//1.1 基本->包装 构造器或自动转型
		Integer i1 = i;
		//1.2 基本->String String.valueOf();
		String s1 = String.valueOf(i);
		//2.包装
		Integer i2 = 2;
		//2.1 包装->基本	xxxValue();
		int i3 = i2.intValue();
		//2.2 包装->String toString();
		String s2 = i2.toString();
		//3.String
		String s3 = "3";
		//3.1 String->基本 包装类.parseXXX();
		int i4 = Integer.parseInt(s3);
		//3.2 String->包装	包装类.valueOf();
		Integer i5 = Integer.valueOf(s3);
	}
}
