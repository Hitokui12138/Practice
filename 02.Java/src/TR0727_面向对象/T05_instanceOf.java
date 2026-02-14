package TR0727_面向对象;

/**
 * 用instanceof来测试以下对象的类型
 * 小心 java.long.ClassCastException
 * 这个其实更多用在父类的方法里面吧？
 */
public class T05_instanceOf {
    public static void main(String[] args) {
        Pet p = new Dog();
        if(p instanceof Object){
            System.out.println("p是Pet对象");
        }
        //转型前测试一下
        if(p instanceof Cat){
            System.out.println("Yes");
            Cat c = (Cat)p;
        }else {
            System.out.println("No");
        }
    }
}
