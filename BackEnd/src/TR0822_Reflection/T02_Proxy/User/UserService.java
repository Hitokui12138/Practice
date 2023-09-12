package TR0822_Reflection.T02_Proxy.User;

/**
 * 系统有一个用户系统,有三个功能
 * 要求统计每个模块的执行耗时,以便后期可以观察程序性能
 *
 */
public interface UserService {
    //登录功能
    void login(String loginName, String passWord) throws Exception;
    //删除用户
    void deleteUsers() throws Exception;
    //查询用户
    String[] selectUser() throws Exception;
}
