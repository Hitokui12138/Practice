package TR0809.T03_TreeSet;

import java.util.TreeSet;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T02_wuxuString.java
 * @Description TODO
 * @createTime 2023年08月09日 13:44:00
 *
 * 数据结构
 * 1. 线性 遍历快,查询快
 * 2. 链表 插入删除快
 * 3. Hash 能去重
 * 4. 队列 先进先出
 * 5. 栈 先进后出
 * 6. 二叉树 排序快
 *
 *
 */
public class T02_wuxuString {
    /*
    把无序字符串转成有序的
    注意这个方法没有考虑字符重复的情况
     */
    public static void main(String[] args) {
        String s = "wadfsnmbvc";
        TreeSet<Character> set = new TreeSet<>();
        for (int i = 0; i < s.length(); i++) {
            set.add(s.charAt(i));
        }
        String temp = "";
        for(Character c:set){
            temp+=c;
        }
        System.out.println(temp);
        set.forEach(System.out::print);//jdk8方法引用
    }
}
