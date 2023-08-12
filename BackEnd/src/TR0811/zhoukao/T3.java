package TR0811.zhoukao;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T3.java
 * @Description TODO
 * @createTime 2023年08月11日 14:47:00
 */
public class T3 {
    public static void main(String[] args) {
        int i = 12345;
        encryption(i);
    }

    public static void encryption(int i) {
        //int->String->String[]->ArraysList<Integer>
        String s = String.valueOf(i);
        String[] sList = s.split("");
        ArrayList<Integer> list = new ArrayList();
        for (int j = 0; j < sList.length; j++) {
            list.add(new Integer(sList[j]));
        }
        //System.out.println(list);
        //首先将数据倒序
        Collections.reverse(list);
        //将每位数字都加上5，再用和除以10的余数代替该数字
        for (int j = 0; j < list.size(); j++) {
            list.set(j, (list.get(j) + 5) % 10);
        }
        //最后将第一位和最后一位数字交换，第二位和倒数第二位数字交换
        for (int j = 0, t = list.size() - 1; j < t; j++, t--) {
            int temp = list.get(j);
            list.set(j, list.get(t));
            list.set(t, temp);
        }
        System.out.println(list);
    }
}
