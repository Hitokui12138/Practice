package TR0810_IO框架;

import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T03_zijieliu.java
 * @Description TODO
 * @createTime 2023年08月10日 15:12:00
 *
 * 字节流Test,没有解码过程
 * 一个汉字是3个字节?对于文本文件建议使用字符流
 *
 * 字节流既可以处理二进制文件,又可以处理文本文件
 * 但字符流的功能和用法会更简单一些,而且如果需要解码,只能用字符流
 *
 */
public class T03_zijieliu {
    @Test
    public void test1() throws IOException {
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T02_IO\\T00", "1.txt");
        FileInputStream fis = new FileInputStream(file);
        byte[] b = new byte[4];
        int len = -1;
        while ((len = fis.read(b)) != -1){
            System.out.println(new String(b,0,len));//这个方法有一个自动解码的过程
        }
    }

    @Test
    public void test2(){

    }
    public void myCopyFile(File srcFile, File descFile){
        FileInputStream fir = null;
        FileOutputStream fiw = null;
        try {
            fir = new FileInputStream(srcFile);
            fiw = new FileOutputStream(descFile);
            byte[] b = new byte[1024];
            int len = -1;
            while ((len=fir.read(b))!=-1){
                //fw.write(new String(c,0,len));
                fiw.write(b,0,len);//只要没有解码就不会出现乱码
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            /*
            先打开的后关闭原则,因此先关闭写入流
            另外注意两个close不能写到一个try里面,不然上面的如果失败,下面的就不工作了
             */
            try {
                fiw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fir.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
