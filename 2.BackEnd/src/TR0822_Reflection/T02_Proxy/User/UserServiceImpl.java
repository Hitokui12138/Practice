package TR0822_Reflection.T02_Proxy.User;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName UserServiceImpl.java
 * @Description TODO
 * @createTime 2023年09月10日 16:57:00
 */
public class UserServiceImpl implements UserService{
    @Override
    public void login(String loginName, String passWord) throws Exception {
        /**
         * 计算耗时的代码与login业务无关,且每个方法都有,冗余了
         * 业务类不应该去做统计时间这些事情
         */
//        long startTime = System.currentTimeMillis();
        Thread.sleep(1000);
//        long endTime = System.currentTimeMillis();
//        System.out.println((startTime - endTime)/1000.0 +"s");
    }

    @Override
    public void deleteUsers() throws Exception {

    }

    @Override
    public String[] selectUser() throws Exception {
        return new String[0];
    }
}
