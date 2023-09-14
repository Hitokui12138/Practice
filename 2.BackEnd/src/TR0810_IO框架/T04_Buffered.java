package TR0810_IO框架;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T04_Buffered.java
 * @Description TODO
 * @createTime 2023年08月10日 15:35:00
 *
 * 1.字节缓冲流
 * 处理流,是对字节流的包装,本身没有处理流的能力
 *
 * 里面定义了一个8kb的数组,先把数组先放到8kb的缓冲数组里,当8kb的数组满了,才会一次性取得,因此速度快(相当于水桶)
 * 这个8kb可以通过构造函数来修改
 * 之前是每次读取1024,取到内存里面,取得次数多了
 *
 * 与FileInputStream相比,这个明显更快
 *
 *
 * 2. 字符缓冲流
 *
 */
public class T04_Buffered {
    @Test
    public void test1() throws IOException {
        /*
        关闭时只要关闭BufferedInputStream就好,因为bis用的本来就是fis的close()
        里面
         */
        FileInputStream fis = new FileInputStream("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T02_IO\\T00\\1.txt");
        BufferedInputStream bis = new BufferedInputStream(fis);
        byte[] b = new byte[1024];
        int len =-1;
        while((len = bis.read(b))!=-1){
            System.out.println(new String(b,0,len));
        }
        bis.close();
    }

    @Test
    public void test2() throws IOException {
        FileOutputStream fos = new FileOutputStream("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T02_IO\\T00\\11.txt");
        BufferedOutputStream bos = new BufferedOutputStream(fos,16384);
        bos.write("你好GSD".getBytes(StandardCharsets.UTF_8));
        bos.close();
    }

/*
copy 操作,与FileInputStream相比,这个明显更快
 */
    @Test
    public void test3(){
        long start = System.currentTimeMillis();
        myCopyFile(new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T02_IO\\T00\\1.txt"),new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T02_IO\\T00\\12.txt"));
        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }

    public void myCopyFile(File srcFile, File descFile){

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(srcFile));
            bos = new BufferedOutputStream(new FileOutputStream(descFile));
            byte[] b = new byte[1024];
            int len = -1;
            while ((len=bis.read(b))!=-1){
                //fw.write(new String(c,0,len));
                bos.write(b,0,len);//只要没有解码就不会出现乱码
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            /*
            先打开的后关闭原则,因此先关闭写入流
            另外注意两个close不能写到一个try里面,不然上面的如果失败,下面的就不工作了
             */
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    BufferedReader很推荐
    这个方法不需要预先定义数组
    以行为单位读取字符串,不会返回换行符
     */
    @Test
    public void test4() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T02_IO\\T00\\1.txt")));
        String content = null;
        while ((content = br.readLine())!=null){    //以换行符为单位读取文件
            System.out.println(content);
        }
        br.close();
    }

    /*
    也是8kb数组,比FileReader要快,
     */
    public void myCopyFile2(File srcFile, File descFile){

        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader(srcFile));
            bw = new BufferedWriter(new FileWriter(descFile));
//            byte[] b = new byte[1024];
//            int len = -1;
//            while ((len= br.read(b))!=-1){
//                //fw.write(new String(c,0,len));
//                bw.write(b,0,len);//只要没有解码就不会出现乱码
//            }
            String content = null;
            while ((content = br.readLine())!=null){
                bw.write(content);
                bw.newLine();//只是替换掉sout(换行符)而已
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            /*
            先打开的后关闭原则,因此先关闭写入流
            另外注意两个close不能写到一个try里面,不然上面的如果失败,下面的就不工作了
             */
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
