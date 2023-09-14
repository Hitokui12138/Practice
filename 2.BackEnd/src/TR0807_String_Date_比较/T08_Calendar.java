package TR0807_String_Date_比较;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Calendar 日历,是一个抽象类
 * 注意Calendar实例是可变的
 */
public class T08_Calendar {

    @Test
    public void Cal(){
        //A.构造器
        //1.使用提供的方法
        Calendar c = Calendar.getInstance();
        //2.使用唯一的子类
        Calendar c1 = new GregorianCalendar();

        //B.常用方法
        System.out.println();
        System.out.println(c.getClass().getName());
        //get,set,add
        System.out.println(c.get(Calendar.YEAR));
        System.out.println(c.get(Calendar.MONTH)+1);

        //JDK1.8之前日期是可变的,而且线程也不安全
        c.set(Calendar.YEAR, 2023);
        System.out.println(c.get(Calendar.YEAR));

        //add
        c.add(Calendar.YEAR, -1);
        System.out.println(c.get(Calendar.YEAR));

        //C. Calendar 和 Date转换
        Date d1 = c.getTime();
        System.out.println(d1);

        Date d2 = new Date();
        c.setTime(d2);
        System.out.println(d2);

        //D. 特殊方法
        System.out.println(c.getActualMaximum(Calendar.DAY_OF_MONTH));//获取一个月最后一天


    }
}
