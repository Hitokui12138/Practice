package TR0808;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * BigInteger BigDecimal
 * int 2^31-1
 */
public class T07_BigInteger {
    public static void main(String[] args) {
        double d = 10.0;
        System.out.println(d / 3);//3.3333333333333335,底层是二进制运算,有精度限制

        BigInteger i1 = new BigInteger("123456789");
        BigInteger i2 = new BigInteger("123456789");
        System.out.println(i1.multiply(i2));//15241578750190521

        BigDecimal d1 = new BigDecimal("10");
        BigDecimal d2 = new BigDecimal("3");
        System.out.println(d1.divide(d2,BigDecimal.ROUND_HALF_UP));
        System.out.println(d1.divide(d2,30,BigDecimal.ROUND_HALF_UP));//3.333333333333333333333333333333


    }
}
