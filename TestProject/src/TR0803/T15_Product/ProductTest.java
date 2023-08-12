package TR0803.T15_Product;

public class ProductTest {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        new Thread(new Producer(clerk),"生产者1").start();
        new Thread(new Customer(clerk),"消费者1").start();
    }
}
class Clerk {//店员
    private int ProductsNum = 0;
    public synchronized void produceProduct() {
        if(ProductsNum<20){
            System.out.println(Thread.currentThread().getName()+":生产了#"+ ++ProductsNum+"商品");
            this.notifyAll();
        }else {
            try {
                this.wait();//一般同步方法的同步监视器为this
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void consumeProduct() {
        if(ProductsNum>0){
            System.out.println(Thread.currentThread().getName()+":消费了#"+ ProductsNum +"商品");
            ProductsNum--;
            this.notifyAll();
        }else {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class Producer implements Runnable{
    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println("开始生产");
        while(true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.produceProduct();
        }
    }
}


class Customer implements Runnable{
    private Clerk clerk;

    public Customer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println("开始消费");
        while (true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.consumeProduct();
        }
    }
}
