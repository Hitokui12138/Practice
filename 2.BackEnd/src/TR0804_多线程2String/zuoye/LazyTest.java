package TR0804_多线程2String.zuoye;
class Lazy {
    private Lazy(){}
    public static Lazy lazy = null;
    public Lazy getInstance(){
        if(lazy == null){
            synchronized (Lazy.class){
                lazy = new Lazy();
            }
        }
        return lazy;
    }
}
public class LazyTest {
}
