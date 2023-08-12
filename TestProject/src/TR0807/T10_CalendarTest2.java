package TR0807;

import java.util.Calendar;

/**
 * 打印今年日历
 */
public class T10_CalendarTest2 {
    public static void printCal(Calendar c){
        System.out.println("日\t一\t二\t三\t四\t五\t六");
        int max = c.getActualMaximum(Calendar.DAY_OF_MONTH);//31,30
        int weekDay = c.get(Calendar.DAY_OF_WEEK);

        int num = weekDay -1;//第一行\t的数量
        for (int i = 0; i < num; i++) {
            System.out.print("\t");
        }

        for (int i = 1; i <= max; i++) {
            num++;
            System.out.print(i+"\t");
            if(num%7==0){
                System.out.println();
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2023);
        c.set(Calendar.DATE, 1);
        for (int i = 0; i < 12; i++) {
            c.set(Calendar.MONTH, i);
            System.out.println();
            System.out.println(c.get(Calendar.MONTH)+1+"月");
            printCal(c);
        }
    }
}
