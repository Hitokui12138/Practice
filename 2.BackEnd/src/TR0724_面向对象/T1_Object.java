package TR0724_面向对象;

/**
 * cmd opt L：
 * 1.面向对象 三条学习主线
 * 1.1 Java类以及类的成员，属性，构造器，方法，代码块，内部类（不学）
 * 1.2 面向对象三大特征： 封装性（Encapsulation），继承性（Inheritance），多态性（Polymorphism）
 * 1.3 其他关键字： this,super,static,final,abstract,interface,package,import
 * <p>
 * 2.1 面向对象 Object Oriented Programming
 * 将功能封装进对象，强调具备了功能的对象，以类/对象为最小单位，考虑由谁来做
 * 2.2 面向过程 Procedure Oriented Programming
 * 强调的是功能行为，以函数为最小单位，考虑怎么做
 * <p>
 * 举例：把大象放冰箱，人，冰箱，大象
 * <p>
 * 3. 栈内存，堆内存
 * 方法在栈中运行，比如main（）方法
 * 对象在堆内存里面
 * 3.1 栈：Pet p，则堆内存创建一块地址（0x100），栈内存中的p指向堆内存的0x100，里面是一些属性
 * 每个对象都有独立的空间,Pet p1(0x200), p1 = p;则p1指向(0x100)
 * 3.2 新建一个对象，对象在堆内存里，因此成员变量在堆内存里
 * main（）中调用一个方法p.test()，则这个方法test(String name)入栈，在栈内存中初始化局部变量
 */

public class T1_Object {
    public static void main(String[] args) {
        //匿名对象，只想调用一下这个方法，不需要属性什么的
        new Person().shout();
    }
}
