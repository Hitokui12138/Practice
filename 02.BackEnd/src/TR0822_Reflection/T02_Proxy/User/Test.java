package TR0822_Reflection.T02_Proxy.User;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName Test.java
 * @Description TODO
 * @createTime 2023年09月10日 17:13:00
 */
public class Test {
    @org.junit.jupiter.api.Test
    public void Test1() throws Exception {
        //正常调用
        UserService userService = new UserServiceImpl();
        userService.login("root","admin");
    }

    @org.junit.jupiter.api.Test
    public void Test2() throws Exception {
        //为业务对象创建一个代理对象
        UserService userServiceProxy = ProxyUtil.createProxy(new UserServiceImpl());
        userServiceProxy.login("root","admin");//代理对象在运行这个方法
    }
}
