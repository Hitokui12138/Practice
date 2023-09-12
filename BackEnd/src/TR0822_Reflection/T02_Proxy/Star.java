package TR0822_Reflection.T02_Proxy;

/**
 * 声明BigStar有哪些方法需要代理
 * 用来给代理公司生成代理的
 *
 * BigStar已经有了sing和dance方法
 * java约定为了形成代理,核心类也应该实现这个接口
 *
 */
public interface Star {

    String sing(String name);
    void dance();

}
