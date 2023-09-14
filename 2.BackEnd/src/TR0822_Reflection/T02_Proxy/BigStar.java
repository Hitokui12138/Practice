package TR0822_Reflection.T02_Proxy;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName BigStar.java
 * @Description TODO
 * @createTime 2023年09月10日 15:37:00
 *
 *
 *
 * 动态代理:
 *  1.为什么需要代理
 *      1.如果要做的事情太多,可以通过代理来转移部分职责(经纪公司)
 *  2.对象有什么方法想被代理,代理也需要有对应的方法
 *      杨超越核心类:唱歌(),外界调用核心类时,先去找代理类
 *      那么经纪人代理类也需要有唱歌()方法,然后在代理类.唱歌()里面调用核心类的.唱歌()
 *  3.经纪公司怎么知道需要提供一个有唱歌()方法的经纪人实例?
 *      需要提供一个带有唱歌()方法的接口,中介公司根据这个接口生成经纪人实例
 *
 */

public class BigStar implements Star {
    private String name;

    public BigStar(String name) {
        this.name = name;
    }

    public String sing(String name){
        System.out.println(this.name+" 正在唱: "+name);
        return "唱完了谢谢";
    }

    public void dance(){
        System.out.println(this.name+" 正在跳舞");
    }
}
