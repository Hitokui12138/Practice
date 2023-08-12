package TR0727;

public class Person {
    public void feed(Cat cat){
        cat.eat();
    }
    //Person依赖于Cat和Dog
    //如果删除Dog类（或者说没有Dog），这里就会报错（或者说需要开发者对Person进行维护）
    public void feed(Dog dog){
        dog.walk();
        dog.eat();
    }

    //为了解耦
    //应该让Dog，Cat继承Pet
    //Person只提供feed（Pet pet）方法，具体实现放到各个类里面去
    //在这里，多态的好处是父类作为方法的形参，调用的是子类，程序运行时运行的是子类重写的方法
    public void feed(Pet pet){
        //具体实现放到dog和cat类里面
        //因为有多态的存在，虽然调用的是父类，但由于对父类的引用指向子类的对象
        //因此这里实际上执行的是子类的方法
    }
}
