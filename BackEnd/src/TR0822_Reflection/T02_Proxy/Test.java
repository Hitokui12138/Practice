package TR0822_Reflection.T02_Proxy;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName Test.java
 * @Description TODO
 * @createTime 2023年09月10日 16:20:00
 */
public class Test {
    public static void main(String[] args) {
        BigStar bs = new BigStar("杨超越");
        Star starProxy = ProxyUtil.createProxy(bs);

        String rs = starProxy.sing("好日子");
        System.out.println(rs);

        starProxy.dance();

        /**
         * 当调用starProxy的sing()方法时,sing()方法会去调用invoke()方法
         *
         */
    }
}
