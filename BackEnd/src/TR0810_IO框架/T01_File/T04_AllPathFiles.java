package TR0810_IO框架.T01_File;

import java.io.File;
import java.util.Arrays;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T04_AllPathFiles.java
 * @Description TODO
 * @createTime 2023年08月10日 10:15:00
 *  遍历所有子路径下的文件
 *
 *  使用递归方法
 *  如果想打印所有盘符,可以使用 File.listRoots()
 */
public class T04_AllPathFiles {
    public static void main(String[] args) {
        long size = showFile(new File("."), "");
        System.out.println(size/1024+"kb");
        System.out.println(Arrays.toString(File.listRoots()));
    }
    public static long showFile(File file, String sep){
        long size = 0;
        File[] files = file.listFiles();
        for(File f : files){
            if(f.isFile()){
                System.out.println("文件: "+sep+f.getName());
                size += f.length();
            }else if(f.isDirectory()){
                System.out.println("路径: "+sep+f.getName());
                size+=showFile(f,sep+"   ");
            }
        }
        return size;
    }
}


