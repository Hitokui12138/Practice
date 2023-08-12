package TR0811.T01_编码和转换流;

import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T01_.java
 * @Description TODO
 * @createTime 2023年08月11日 08:34:00
 *
 *
 * ASCII 美国标准信息交换码  1个字节的7位,128
 * ISO8859-1    拉丁码表,欧洲码表   1个字节的8位,256
 * GB2312,GBK,GB18030,(ANSI指的是系统编码,一些系统用的是GBK)   中文码表   都是 2~3万 2个字节16位 65536
 * GB2312有一些字没有收入,GBK是后来升级的,收入了更多的汉字
 *
 * Unicode  国际标准码,所有文字都用两个字节来表示 65536,  Unicode只是制定了一个码的标准,没有具体实现
 * UTF-8    二进制,变长的编码方式,可以用1-4个字节表示一个文字 每次8个传输数据
 *  0xxxxxxx    表示ASCII
 *  110xxxxx  10xxxxxx
 *  110xxxxx  10xxxxxx  10xxxxxx 一般是三个字节表示一个汉字
 *
 * UTF-16   十六进制,每次传输16个位
 *
 * GBK如何区分读到的是英文还是汉字?
 * 最高位是1,表示两个字节,最高位是0时表示一个字节,少了一位,相当于32768个字符,也够用了
 *
 *
 * 2.转换流(可以解决乱码问题)
 * 字符流操作起来更简单(可以一行一行地读),因此提供了字节流->字符流地方法
 * 也能对编码方式进行转换
 * 字符流FileReader继承InputStreamReader
 * InputStreamReader(字节流,编码):   InputStream->Reader
 * OutputStreamWriter:  OutputStream->Writer
 *
 * UTF-8->字节->字符->GBK->字符->字节
 */
public class T01_ZhuanHuanLiu {
    /**
     * 转换流解决编码问题
     *  1.思路
     *  UTF-8->字节流->通过转换流转字符流->GBK->字符->字节
     *
     *  2.使用输出流的注意点:
     *  输出流注意要写close,flush可以随便调用
     *  1.超过8kb时会输出,但最后不超过8kb地就不会输出
     *  2.随时调用flush
     *  3.最后调用close
     *
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        File f1 = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0811","dbcp_gbk.txt");
        File f2 = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0811","dbcp_utf_8.txt");

        /*
        1. 文件 -> 字节流
        2. 使用转换流的方法InputStreamReader(字节流,编码),获得一个转码后的字符流(字符流FileReader继承InputStreamReader)
        3. 把字符流包装成缓冲字符流便于操作
         */
        FileInputStream fis = new FileInputStream(f1);
        InputStreamReader isr = new InputStreamReader(fis,"gbk");//默认是UTF-8,为了读入不要有乱码,这里指定成GBK
        BufferedReader br = new BufferedReader(isr);

        //Writer用简略地写法
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f2),"utf-8"));//这里的utf-8也可以不写

        String content =null;
        while ((content=br.readLine())!=null){
            //System.out.println(new String(content));
            bw.write(content);
            bw.newLine();
            bw.flush();
        }
        br.close();//不关占内存
        bw.close();//不关不往里面写
    }
}
