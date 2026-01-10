package TR0720_数组;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T_Change.java
 * @Description TODO
 * @createTime 2023年08月13日 16:18:00
 */
public class T_Change {
    public static void main(String[] args) {
            /*
    A. 基本,包装,String
     */
        int i1 = 1;
        //1. 基本->包装
        Integer n1 = new Integer(2);//新建一个包装类优先考虑构造器
        Integer n2 = 3;//自动包装
        //1. 包装->基本
        int i2 = Integer.valueOf(n1);
        int i3 = n2;//自动拆箱

        //2. 基本->String
        String s1 = String.valueOf(i1);//各种重载方法,没有重载的构造器
        //2. String->基本
        int i4 = Integer.parseInt(s1);//用了Integer的静态方法

        //3. 包装->String
        String s2 = n2.toString();//既然都是类,一般都有toString()方法
        //3. String->包装
        Integer n3 = new Integer(s2);//新建一个包装类优先考虑构造器

    /*
    B. String,String[],char[]
     */
        //1. String->char[],String->String[]
        char[] charList1 = s2.toCharArray();
        String[] sList = s2.split("");
        //2.char[]->String
        String s3 = new String(charList1,0,charList1.length);//在字符流里用到过这个

    /*
    C. ArrayList,[]
     */
        //1. []->ArrayList
        //1.1 有一个 Arrays.asList(sList)方法,但不推荐,这个方法只能返回List类型的
        //List<String> stringList = Arrays.asList(sList);
        ArrayList<String> aList = new ArrayList<String>(Arrays.asList(sList));
        //1.2 推荐方法:Collections.addAll(arrayList, sList);
        ArrayList<String> aList2 = new ArrayList<String>(sList.length);//建一个等长的ArrayList
        Collections.addAll(aList2,sList);

        //2. ArrayList->[]
        String[] sList2 = new String[aList.size()];//注意长度相同
        sList2 = aList.toArray(sList2);
        //System.out.println(Arrays.toString(sList2));
    }
}
