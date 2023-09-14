package TR0807_String_Date_比较;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class T07_DateTest {
    public static void main(String[] args) throws ParseException {
        Date d1 = new Date();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d2 = df1.parse("2022-01-01");
        System.out.println(d1);
        System.out.println(d2);
        long t = (d1.getTime()-d2.getTime())/(1000*60*60*24)+1;
        System.out.println(t+"天");
        t=t%5;
        System.out.println(t);
    }
}
