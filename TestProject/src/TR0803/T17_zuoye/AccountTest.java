package TR0803.T17_zuoye;

class Account{
    private double balance;
    public synchronized void deposit(double monery){
        balance+=monery;
        System.out.println(Thread.currentThread().getName()+"存入1000元"+",余额:"+balance);
    }
}
class Customer implements Runnable{
    private Account account;

    public Customer(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            account.deposit(1000);

        }
    }
}

public class AccountTest {
    public static void main(String[] args) {
        Account account = new Account();
        new Thread(new Customer(account)).start();
        new Thread(new Customer(account)).start();
    }
}
