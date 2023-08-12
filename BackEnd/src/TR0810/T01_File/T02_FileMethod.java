package TR0810.T01_File;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T02_FileMethod.java
 * @Description TODO
 * @createTime 2023年08月10日 08:57:00
 *
 * 常用方法
 *
 * File类的缺点:
 * 如果操作失败,没有提示(不会抛出异常)
 * 因此后面会使用NIO
 */
public class T02_FileMethod {
    @Test
    public void test(){
        File f1 = new File(".}}","2.txt");//一个不存在的文件和一个
        System.out.println(f1.getAbsolutePath());//这几个方法都是字符串拼接,很不靠谱
        System.out.println(f1.getPath());
        System.out.println(f1.getName());
        System.out.println(f1.getParent());
        //下面两个方法可行
        File f2 = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T01_File","1.txt");
        System.out.println(f2.length());
        System.out.println(f2.lastModified());//上次修改时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        System.out.println(sdf.format(new Date(f2.lastModified())));
    }

    /*
        遍历目录的方法 2种
        功能判断的一些方法
         */
    @Test
    public void test2(){
        File f1 = new File(".");
        //这个方法能拿到一个String数组
        String[] list = f1.list();
        for(String s:list){
            System.out.println(s);
        }
        //这个方法能拿到一个File数组
        File[] list2 = f1.listFiles();
        for(File s:list2){
            if(s.isFile()){
                System.out.println("文件"+s);
                System.out.println("存在?"+s.exists());
                System.out.println("可读?"+s.canRead());
                System.out.println("可写?"+s.canWrite());
                System.out.println("隐藏?"+s.isHidden());
            }else if(s.isDirectory()){
                System.out.println("目录"+s);
            }
        }
    }
       /*
        rename
        在当前目录下相当于改文件名,如果是在其他目录下,则相当于剪切粘贴
         */
    @Test
    public void test3(){
        File f1 = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T01_File","name2.txt");
        System.out.println(f1.renameTo(new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T01_File","name1.txt")));
    }

    /*
    创建相关的方法
     */
    @Test
    public void test4() throws IOException {
        File f = new File("3.txt");
        if(f.exists()==false){
            System.out.println("创建文件"+f.createNewFile());
        }else {
            System.out.println("文件已存在");
            System.out.println("删除该文件"+f.delete());//不走回收站,直接就没了
            //System.out.println("删除该文件"+f.deleteOnExit());这个方法不会立即删除,而是在虚拟机退出时删除
        }
        //mkdir()只能创建一层文件夹
        //mkdirs()可以创建多层文件夹
        File f2 = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T01_File/a/b/c");
        if(!f2.exists()){
            System.out.println("创建文件夹"+f2.mkdirs());
        }else {
            System.out.println("路径已存在");
        }
    }


    @Test
    public void test5(){
        deleteFile(new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T01_File/a/b/c"));
    }
    /*
    删除文件夹
    因为如果文件夹非空就不能删,需要一个递归操作
     */
    public static void deleteFile(File file){
        if(file.exists()){
            System.out.println("文件夹不存在");
            return;
        }
        File[] files = file.listFiles();
        for (File f: files){
            if(f.isFile()){
                f.delete();
            }else if (f.isDirectory()){
                deleteFile(f);
            }
        }
        file.delete();//递归操作
    }
}
