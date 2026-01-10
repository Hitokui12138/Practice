package TR0810_IO框架.T02_IO.T00;

import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName FileReadWrite.java
 * @Description TODO
 * @createTime 2023年08月10日 12:51:00
 *
 * 字符流Test,只能用于处理文本文件
 * 字符流=字节流+解码的过程
 *
 */
public class FileReadWrite {
    @Test
    public void test1() {
        /*
        1.创建File对象(文件必须存在)
        2.创建相应的输入流,将file对象作为参数
        3.具体读入过程 无参的read()方法一次读一个,读到-1为止
        4.关闭流资源
         */
        //1.创建File对象,创建空的输入流对象
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T02_IO\\T00", "1.txt");
        FileReader fr = null;
        try {
            //2.将File对象作为参数,实例化流对象
            fr = new FileReader(file);
            //3.具体读入过程
            int content = -1;
            while ((content = fr.read()) != -1) {   //当返回值为-1时表示读完了
                System.out.print((char) content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {  //4.在finally里面关闭流对象
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    有参的read()方法,把读到的数据放入一个数组
    推荐长度定为1024的倍数,因为内存是分页的,可以减少碎片减少内存浪费
    1.读取时应该按照真实长度来读取
     */
    @Test
    public void test2() throws IOException {
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T02_IO\\T00", "1.txt");
        FileReader fr = null;
        try {
            //2.将File对象作为参数,实例化流对象
            fr = new FileReader(file);
            //3.具体读入过程
            char[] c = new char[4];//如果长度过小
            int len = 0;
            while ((len = fr.read(c)) != -1) {  //把文件读取到char[]里面
                //错误写法方式1
//                for (int i = 0; i < c.length; i++) {
//                    System.out.print(c[i]);
//                }
                //正确写法1:应该用
//                for (int i = 0; i < len; i++) {
//                    System.out.print(c[i]);
//                }

                //错误方式2
                //System.out.println(new String(c));//hello file
                //正确方式2 应该按照真实长度来取值,不然上一次取得会被剩下在最后
                System.out.println(new String(c,0,len));//

            }
        } finally {
            fr.close();
        }
    }

/*
输出过程
也是4步操作
文件可以存在也可以不存在
一个字符是两个字节16位
close()底层是flush(),调用之后才会写入,一般调用close()
 */
    @Test
    public void test3(){
        File file = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T02_IO\\T00", "2.txt");
        FileWriter fw = null;
        try {
            //fw = new FileWriter(file);//每次close都是一次覆盖操作
            fw = new FileWriter(file,true);//在后面追加而不是覆盖,如果是一个参数或者append位false则为覆盖操作
            fw.write("1.你好GSD");
            fw.write("2.我想学习java");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fw.close();//只有调用了close才能真的把数据写入文件中去
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*
    仅用于TXT文件的copy,字符流读入内存后有一个解码的过程,两个字节是16位,能表示一个字符(汉字)2^16个字符(65,536)
    常用汉字也就2万个,这就意味着又4万多种字符会被转成?之类的,再转回去的话就不能完整复现4玩多种字符了
    对于图片文件(二进制文件),不能使用字符流,只能使用字节流
    边读边写,
    圆形内存,矩形硬盘
     */
    @Test
    public void test4(){
        File file1 = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T02_IO\\T00", "2.txt");
        File file2 = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T02_IO\\T00", "3.txt");
        myCopyFile(file1,file2 );
    }

    /**
     * 边读边写
     * @param srcFile 源文件
     * @param descFile 目标文件
     */
    public void myCopyFile(File srcFile, File descFile){
        FileReader fr = null;
        FileWriter fw = null;
        try {
            fr = new FileReader(srcFile);
            fw = new FileWriter(descFile);
            char[] c = new char[1024];
            int len = -1;
            while ((len=fr.read(c))!=-1){
                fw.write(new String(c,0,len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            /*
            先打开的后关闭原则,因此先关闭写入流
            另外注意两个close不能写到一个try里面,不然上面的如果失败,下面的就不工作了
             */
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
