package TR0807;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * 给一个日期,打印日历
 * 打印今年12个月的日历
 */
public class T09_CalendarTest {
    public static void main(String[] args) {
        /*
        1.根据键盘输入,生成日期对象
        2.计算这个越有多少天
        3.获取天数
        4.计算第一行需要几个\t
        5.循环打印
         */
        System.out.println("输入一个日期(2023-01-02):");
        Scanner s = new Scanner(System.in);
        String strDate = s.nextLine();
        Date date = java.sql.Date.valueOf(strDate);
        System.out.println(date);
        System.out.println("日\t一\t二\t三\t四\t五\t六");
        //为了计算,转成Calendar
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //获得本月天数和输入的日期的天数
        int max = c.getActualMaximum(Calendar.DAY_OF_MONTH);//31
        int today = c.get(Calendar.DAY_OF_MONTH);

        //
        c.set(Calendar.DAY_OF_MONTH, 1);
        int weekDay = c.get(Calendar.DAY_OF_WEEK);
        int num = weekDay -1;//\t的数量

        for (int i = 0; i < num; i++) {
            System.out.print("\t");
        }

        for (int i = 1; i < max; i++) {
            num++;
            if(today == i){
                System.out.print(i+ "*\t");
            }else{
                System.out.print(i+"\t");
            }
            if(num%7==0){
                System.out.println();
            }
        }
    }
}
