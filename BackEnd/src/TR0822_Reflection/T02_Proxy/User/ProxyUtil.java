package TR0822_Reflection.T02_Proxy.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ProxyUtil.java
 * @Description TODO
 * @createTime 2023年09月10日 17:02:00
 */
public class ProxyUtil {
    public static UserService createProxy(UserService userService){
        UserService userServiceProxy = (UserService)Proxy.newProxyInstance(ProxyUtil.class.getClassLoader(),
                new Class[]{UserService.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("login")||method.getName().equals("deleteUsers")){
                            //1. 计算开始时间
                            long startTime = System.currentTimeMillis();
                            //2. 调用业务方法
                            Object rs = method.invoke(userService, args);//调用业务方法
                            //3. 计算结束时间
                            long endTime = System.currentTimeMillis();
                            System.out.println(method.getName()+"耗时: "+(endTime - startTime)/1000.0 +"s");
                            return rs;
                        }else{
                            //比如调用的是toString()方法
                            Object rs = method.invoke(userService, args);//调用业务方法
                            return rs;
                        }
                    }
                });
        return userServiceProxy;
    }
}
