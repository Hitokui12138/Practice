package TR0809.T01_LinkedList;

import java.util.LinkedList;
import java.util.Random;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName PokerTest.java
 * @Description TODO
 * @createTime 2023年08月09日 08:47:00
 */
class Poker {
    String number;//点数
    String color;//花色

    public Poker(String number, String color) {
        this.number = number;
        this.color = color;
    }

    @Override
    public String toString() {
        return color + number;
    }
}

public class PokerTest {
    public static void main(String[] args) {
        //1.生成
        LinkedList<Poker> poker = createPoker();
        //2.洗牌
        for (int i = 0; i < 100; i++) {
            myShuffle(poker);
        }
        //3.发牌
        printPoker(poker);

    }

    private static void printPoker(LinkedList<Poker> poker) {
        for (int i = 0; i < poker.size(); i++) {
            System.out.print(poker.get(i) + "\t");
            /*
            10个换一行,0%任何数都是0,因此可以换成下面这样
             */
            if (i % 10 == 9) {
                System.out.println();
            }
        }
    }


    private static LinkedList<Poker> createPoker() {
        LinkedList<Poker> temp = new LinkedList<Poker>();
        String[] numbers = {"3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2"};
        String[] colors = {"梅花", "方块", "黑桃", "红桃"};
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < colors.length; j++) {
                temp.add(new Poker(numbers[i], colors[j]));
            }
        }
        temp.add(new Poker("大王", " "));
        temp.add(new Poker("小王", " "));
        return temp;
    }

    private static void myShuffle(LinkedList<Poker> pokers) {
        Random r = new Random();
        int num1 = r.nextInt(54);
        int num2 = r.nextInt(54);
        Poker p1 = pokers.get(num1);
        Poker p2 = pokers.get(num2);
        pokers.set(num2, p1);
        pokers.set(num1, p2);
    }
}
