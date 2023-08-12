package TR0811.T02_标准输入输出流;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Locale;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T01.java
 * @Description TODO
 * @createTime 2023年08月11日 09:35:00
 *
 * System.in System.out表示标准输入输出设备
 * Scanner是jdk1.5提供的
 * 在之前是new InputStreamReader(System.in)
 * 也是字节流->字符流->缓冲流这样
 *
 * 看一下ppt吧
 * PrintStream
 *
 * 3. 数据流
 *
 *
 */
public class T01 {
    /*
    Idea不能在@Test里面通过控制台输入
     */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入:");
        String content = null;
        while ((content = br.readLine())!=null){
            if(content.equalsIgnoreCase("e")||content.equalsIgnoreCase("exit")){
                break;
            }
            content = content.toUpperCase();
            System.out.println(content);
        }
        br.close();
    }

    @Test
    public void test2() throws IOException {
        PrintStream ps = new PrintStream(new FileOutputStream("Test.txt"),true);
        System.setOut(ps);//重置输出流
        for (int i = 0; i < 255; i++) {
            System.out.print((char)i);
            if(i%50==49){
                System.out.println();
            }
        }
        ps.close();
    }
}
