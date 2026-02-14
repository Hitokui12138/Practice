package TR0727_面向对象;

/**
 * Object 根父类，所有类默认继承这类
 * 没有属性，有十几个重要的方法
 */
public class T06_Object {
    public static void main(String[] args) {
        //ctrl shift T 查找类
        //ctrl o 查找类中方法
        Pet p = new Dog();
        Dog d = new Dog();
        System.out.println(p.equals(d));//false

        //1.要想使用clone，需要实现cloneAble接口
        //Pet p2 = (Pet)p.clone();
        //用的很少

        //finalize（）方法，释放资源时会自动调用
        p = null;//把对象变成垃圾对象，等待回收，但时间不确定
        System.gc();//强制释放空间


    }


}
