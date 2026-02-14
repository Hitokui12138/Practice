package TR0810_IO框架.T01_File;

import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T01_File.java
 * @Description TODO
 * @createTime 2023年08月10日 08:37:00
 *
 * File对象
 * 表示一个文件或文件目录
 * 常作为参数传递到流的构造器里
 *
 *
 *
 */
public class T01_File {
    @Test
    public void test1(){
        /*
        1.相对路径:相较于某个路径下,只能是当前项目的盘符下
            注意这里时@Test因此相对路径在当前文件夹下,main函数的话就是src下面了
         */
        File f1 = new File("1.txt");
        System.out.println(f1);
        File f12 = new File("2.txt");
        System.out.println(f12);//文件也可以不存在
        File f2 = new File("../","3.txt");
        System.out.println(f2.getName());//文件也可以不存在
        /*
        2.绝对路径:包含盘符在内的路径,可以指定任意盘符
         */
        File f3 = new File("C:\\Users\\Admin\\IdeaProjects\\TestProject\\src\\TR0810\\T01_File","1.txt");

        /*
        这种构造方法用的比较多
         2.根一个常量,自动提供分隔符 File.separator
         Windows不区分正反斜杠默认反斜杠,而Linux只能用正斜杠/
         */
        File f4 = new File(new File("C:"+File.separator+"Users/Admin/IdeaProjects/TestProject/src/TR0810/T01_File"),"1.txt");
        System.out.println(f4.getAbsolutePath());//C:\Users\Admin\IdeaProjects\TestProject\src\TR0810\T01_File\1.txt

    }


}
