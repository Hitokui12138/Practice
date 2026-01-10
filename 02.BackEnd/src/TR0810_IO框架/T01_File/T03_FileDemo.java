package TR0810_IO框架.T01_File;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T03_FileDemo.java
 * @Description TODO
 * @createTime 2023年08月10日 09:58:00
 *
 * 使用list()listfile()的重载方法
 */
public class T03_FileDemo {
    /*
    访问者模式
    遍历时,会在遍历每个元素时调用一个方法,为true时表示这个元素时需要的
     */
    @Test
    public void test(){
        File f = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T01_File");
        //1.不推荐
        File[] files = f.listFiles(new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name) {//文件的父目录和文件名
                if(name.endsWith(".txt")){
                    System.out.println(name);
                    return true;//为true时才会返回
                }
                return false;
            }
        });
        //2,推荐
        File[] files2 = f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(pathname.isFile()&&pathname.getName().matches(".*\\.txt$")){
                    return true;
                }
                return false;
            }
        });
    }
}
