package TR0822_Reflection.T02_Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ProxyUtil.java
 * @Description TODO
 * @createTime 2023年09月10日 15:46:00
 *
 *
 * 代理工具类来模拟经纪公司
 *
 */
public class ProxyUtil {
    /**
     * 生成的代理一定是一个实现了Star接口的对象,因此返回Star类型
     * @param bigStar
     * @return
     */
    public static Star createProxy(BigStar bigStar){
        /**
         * java.lang.reflect.Proxy 反射类下的代理
         * newProxyInstance(,,) 返回一个Object
         * 1. 三个参数
         *      1.ClassLoader loader,指定一个类加载器,固定写法,使用当前类的类加载器
         *      2.Class<?>[] interfaces,指定生成的代理长什么样,有哪些方法,可以接收接口数组
         *      3.InvocationHandler h,指定生成的对象要做什么事情
         * 2. InvocationHandler是一个接口,接口不能直接创建对象(Comparator)
         *      使用匿名内部类对象
         * 3. 重写invoke()方法,三个参数+回调方法
         *      代理做什么事情由invoke决定
         *  //Star starProxy = ProxyUtil.createProxy(bs);
         *  //starProxy.sing("好日子");//调用invoke,因为代理做什么由invoke决定
         *  //starProxy.dance();
         *      1.三个参数
         *          Object proxy,   starProxy代理人实例
         *          Method method,  sing()方法
         *          Object[] args   方法的参数,"好日子"

         */
        //创建代理人
        Star starProxy = (Star) Proxy.newProxyInstance(ProxyUtil.class.getClassLoader(),
                new Class[]{Star.class},
                new InvocationHandler() {   //new + 空格,选中接口,自动生成
                    @Override   //这是一个回调方法
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //代理人工作:
                        if(method.getName().equals("sing")){
                            System.out.println("代理人准备话筒,收钱20万");
                        }else if(method.getName().equals("dance")){
                            System.out.println("代理人准备场地,收钱100万");
                        }
                        //代理人工作完后,杨超越工作:
                        return method.invoke(bigStar, args);//返回"谢谢"
                    }
                });
        return starProxy;
    }
}
