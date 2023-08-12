package TR0808.T11_jihe;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 删除ArrayList的重复元素"aa"
 */
public class DuplicateTest {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        list.add("aa");
        list.add("aa");//这个可能不会被删除
        list.add("dd");

        //这个方法是不行的,因为每次删除,都会导致list.size()变化
//        for (int i = 0; i < list.size(); i++) {
//            String temp = list.get(i);
//            if(temp.equals("aa")){
//                list.remove(temp);
//            }
//        }
//        System.out.println(list);
        /*
         1.倒叙
         2.使用迭代器的remove()
         */
        //倒叙(for循环的跳出条件与会变化的长度无关)
//        for (int i = list.size()-1; i > 0; i--) {
//            String temp = list.get(i);
//            if(temp.equals("aa")){
//                list.remove(temp);
//            }
//        }
//        System.out.println(list);

        //迭代器
        Iterator i = list.iterator();
        while (i.hasNext()){
            if(i.next() == "aa"){
                i.remove();
            }
        }
        System.out.println(list);
    }
}
