package TR0807;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * 图片
 */
public class T12_PeriodDuration {
    @Test
    public void test(){
        LocalDateTime ldt1 = LocalDateTime.now();
        LocalDateTime ldt2 = LocalDateTime.of(2022,1,1,0,0,0);
//        Period p = Period.between(ld2, ld1);//只能用于LocalDate
//        System.out.println(p);
//        System.out.println(p.getDays());//不太好用,只能得到偏移的数

        Duration d = Duration.between(ldt2, ldt1);//只能用于LocalDateTime
        System.out.println(d.toDays());
    }
}
