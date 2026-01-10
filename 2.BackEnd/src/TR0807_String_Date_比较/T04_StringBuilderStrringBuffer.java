package TR0807_String_Date_比较;

/**
 * 常用类:1.类的构造器,2.类的常用方法
 * 1.底层是一个char[]数组
 * 2. 都继承自AbstractStringBuilder
 * 3. 效率对比:StringBuilder(1.5) > StringBuffer(1.0) > String(1.0)
 *
 * 2. 默认初始大小?
 * 2.1 无参,没有指定父类默认给一个16位的char[],
 * 2.2 指定长度的:
 * 2.3 直接给个字符串:字符串的长度+16
 *
 * 3. Append超出16位怎么办
 * 3.1 会调用一个ensureCapacityInternal()确认append之后的容量大小
 * 3.2 Arrays.copy方法,新的长度会左移一位,oldLength*2+2
 * 3.3 如果再大,甚至超出int范围,就抛出异常OutofMemory
 *
 * 3.4.1 Append扩容,原来的数组长度*2+2,然后把旧的copy进去
 * 3.4.2 这样的话如果原来的字符串特别长100万,扩容后200万,再复制,效率会很慢
 * 3.4.3 因此如果已知字符串长度,应该考虑使用带参的两个方法
 *
 * 3.5 常用方法
 * 3.5.1 增
 * append(""),insert(2,"ab")
 * 3.5.2 删
 * delete(int start, int end)左闭右开,deleteCharAt(2)
 * 3.5.3 改
 * replace(2,3,"hello"),reverse(),setCharAt(0,'a')
 * 3.5.4 查
 * indexOf("aa"),lastIndexOf("bb"),charAt()
 * 3.5.5
 */
public class T04_StringBuilderStrringBuffer {
    public static void main(String[] args) {
        //JDK 1.5
        /*

         */
        StringBuilder builder = new StringBuilder("hello");
        builder.append("world").append(true).append(10.0f).append('a');
        System.out.println(builder);//什么都可以往里面放,但会全部变成字符串

        //JDK 1.0 提供的
        //StringBuffer的所有的方法都带有一个同步
        /*

         */
        StringBuffer stringBuffer = new StringBuffer("abc");


        /*
           效率对比
         */
        long startTime = 0L;
        startTime = System.currentTimeMillis();
    }
}
