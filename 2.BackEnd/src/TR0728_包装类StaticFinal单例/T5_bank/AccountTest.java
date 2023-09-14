package TR0728_包装类StaticFinal单例.T5_bank;

public class AccountTest {
	private int ID;
	private String password;
	private double balance;
	private static double interestRate = 3.14;
	private static int minBalance = 1;
	private static int count = 1000;
	
	public AccountTest() {
		this.ID = count++;
	}

	public AccountTest(String password, double balance) {
		super();
		this.password = password;
		this.balance = balance;
		this.ID = count++;
	}

	@Override
	public String toString() {
		return "AccountTest [ID=" + ID + ", password=" + password + ", balance=" + balance + "]";
	}

	public static void main(String[] args) {
		AccountTest a1 = new AccountTest("1234",10000);
		AccountTest a2 = new AccountTest("1234",10000);
		System.out.println(a1);
		System.out.println(a2);
	}
	
}
