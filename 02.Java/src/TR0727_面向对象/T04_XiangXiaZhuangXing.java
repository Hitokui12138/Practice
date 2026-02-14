package TR0727_面向对象;

/**
 * Casting
 * DisCasting
 * 多态的一些问题
 * Pet p = new Dog();//默认会向上转型，在编译时p只能看父类的方法，p看不到子类特有的方法和属性
 * p.bulk()//调用子类特有的方法时，编译可能不通过
 *
 * 基本数据类型：
 * 自动小类型转大类型 long g （大）= 20 (小)
 * 强制大类型转小类型：（int）int i (小)= (int)1200L (大)
 *
 * 引用数据类型：
 * 自动：子类转父类
 * 强制转型（造型）：父类转子类
 * 造型前可以使用insatnceof来测试以下这个类型
 */
public class T04_XiangXiaZhuangXing {
    public static void main(String[] args) {
        //多态默认向上转型
        Pet p = new Dog();
        //p.bulk();//会报错
        //引用类型强制向下转型
        Dog d = (Dog)p;
        d.bulk();//这样才能使用子类特有的属性方法
    }
}
