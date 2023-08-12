package TR0802;

public class zuoye15_Test {
	public static void main(String[] args) {
		zuoye15_abstract a = new zuoye15_abstract() {
			@Override
			public void Test() {
				// TODO Auto-generated method stub
				System.out.println("匿名类实现抽象类");
			}	
		};
		a.Test();
	}
}
