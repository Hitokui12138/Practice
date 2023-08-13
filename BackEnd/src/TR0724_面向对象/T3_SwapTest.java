package TR0724_面向对象;

public class T3_SwapTest {
    public static void main(String[] args) {
        T3_SwapTest test = new T3_SwapTest();
        Person p = new Person(1,2);
        test.swap(p.a, p.b);//无效，
        test.swap(p);//有效
    }

    public void swap(int a, int b){
        int temp = a;
        a = b;
        b = temp;
    }
    public void swap(Person p){
        int temp = p.a;
        p.a = p.b;
        p.b = temp;
    }
}
