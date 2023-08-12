package TR0809.T01_LinkedList;

import java.util.LinkedList;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName DuplicateChar.java
 * @Description TODO
 * @createTime 2023年08月09日 10:17:00
 *
 * "abbaca"->"ca"
 * 用链表自带的栈方法来实现
 *
 * 还有一个ArrayDeque也实现了Deque接口,但底层是数组实现的,因此速度要更快一些
 * LinkedList底层是Node接点实现的
 *
 * 双向指针,字符串做栈
 * https://blog.csdn.net/mynameisgt/article/details/122631606
 */
public class DuplicateChar {
    public static void main(String[] args) {
        String s = "abbaca";
        System.out.println(removeDuplicate(s));
    }

    private static String removeDuplicate(String str) {
        LinkedList<Character> list = new LinkedList();
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if(list.isEmpty()|| list.peek()!=str.charAt(i)){
                list.push(str.charAt(i));
            }else {
                list.poll();
            }
        }
        String temp = "";
        while (!list.isEmpty()){
            temp = list.poll() + temp;
        }
        return temp;
    }
}
