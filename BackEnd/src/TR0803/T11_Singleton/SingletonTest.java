package TR0803.T11_Singleton;

/**
 * 线程安全的懒汉模式
 * 1.私有构造器
 * 2.公共静态实例
 * 3.getInstance(),若为null,则同步代码块实例化该对象
 */
public class SingletonTest {

}

class Singleton{
    private Singleton(){

    }
    public static Singleton singleton = null;

    public static Singleton getInstance(){
//        //效率低,所有线程来这里都会变成单线程方法
//        synchronized (Singleton.class){
//            if(singleton == null){
//                singleton = new Singleton();
//            }
//        }

        //效率高,进第一次是单线程,之后便不再执行这个单线程方法
        if(singleton == null){
            synchronized (Singleton.class){
                singleton = new Singleton();
            }
        }

        return singleton;
    }
}
