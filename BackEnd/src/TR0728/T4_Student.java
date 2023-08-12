package TR0728;
/**
 * 1.什么时候使用statci
 * 1.2 静态属性
 * 
 * 1.3静态方法
 * 操纵静态属性的方法,工具类的方法
 * @author Admin
 *
 */
public class T4_Student {
	String name;
	int age;
	static String  country = "日本";
	public T4_Student(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Student:"+name+":"+age;
	}
	
	public static void main(String[] args) {
		T4_Student s1 = new T4_Student("abc",18);
		T4_Student s2 = new T4_Student("def",20);
		System.out.println(s1.country);
		T4_Student.country ="中国";
		System.out.println(s1.country);
		System.out.println(s2.country);
	}
	
}
