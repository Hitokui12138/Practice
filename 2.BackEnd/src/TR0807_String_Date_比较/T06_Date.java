package TR0807_String_Date_比较;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 1. 1.8之前 Calendar
 * 1.1 util.Date
 * 1.1.1 构造器 new Date(),new (1691376169832L)
 * 1.1.2 指定日期需要和DateFormat结合使用,new Date(yyyy...)那个方法弃用了,因为结果差了1900
 *
 * 1.2 sql.Date
 * 1.2.1 构造器
 * 1.2.1.1 java.sql.Date.valueOf("2023-08-05");
 * util转sql
 * 1.2.1.2 (java.sql.Date) d2;
 * 1.2.1.3 new java.sql.Date (d2.getTime())
 *
 * 1.3 DateFormat
 * 1.3.1 new SimpleDateFormat()默认格式,new SimpleDateFormat("yyyy-MM-dd")指定格式
 * 1.3.2 日期格式化 df.format(d1)
 * 1.3.3 解析 df1.parse("2022-01-01");
 *
 */
public class T06_Date {
    @Test
    public void test(){
        System.out.println(System.currentTimeMillis());
    }
    @Test
    public void dateTest(){
        /*
        toString(),getTime()
         */
        Date d1 = new Date();
        System.out.println(d1.toString());
        System.out.println(d1.getTime());//获得时间戳
        //指定一个时间
        Date d2 = new Date(1691376169832L);

        /*
        sql.date,util转sql
         */
        java.sql.Date d3 = java.sql.Date.valueOf("2023-08-05");
        System.out.println(d3);
        //方式1
       // java.sql.Date d4 = (java.sql.Date) d2;
        //方式2
        java.sql.Date d5 = new java.sql.Date (d2.getTime());
        System.out.println(d5.getTime());
        System.out.println(new Date(d5.getTime()));
    }

    @Test
    public void test1(){
        Date ud = new Date();
        //1. util->sql
        java.sql.Date sd = new java.sql.Date(ud.getTime());
        //2. sql->util
        Date ud2 = new Date(sd.getTime());
    }


    @Test
    public void dateFormat() throws ParseException {
        /*
        1.日期格式化:日期->字符串
            就用SimpleDateformat
         */
        SimpleDateFormat df = new SimpleDateFormat();//默认格式
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");//带参的格式
        Date d1 = new Date();
        System.out.println(df.format(d1));
        /*
        2.解析:字符串->日期
         */
        Date d2 = df.parse("2023/8/7 上午11:00");
        System.out.println(d2);
        Date d3 = new Date();
        System.out.println(df1.format(d3));
        /*
        3.其他解析方法,固定的yyyy-MM-dd
         */
        java.sql.Date d5 = java.sql.Date.valueOf("2023-08-05");
    }
}
