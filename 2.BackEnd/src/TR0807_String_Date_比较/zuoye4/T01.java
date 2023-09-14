package TR0807_String_Date_比较.zuoye4;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class T01 {
    public static void main(String[] args) throws ParseException {
        //1
        java.sql.Date d1 = java.sql.Date.valueOf("2017-08-16");
        //2
        DateFormat d = new SimpleDateFormat("YYYY-mm-dd");
        Date d2 = d.parse("2017-08-16");
        java.sql.Date d3 = new java.sql.Date (d2.getTime());
    }
}
