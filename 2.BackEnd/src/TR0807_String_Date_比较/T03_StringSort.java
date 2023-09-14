package TR0807_String_Date_比较;

import java.util.Arrays;

public class T03_StringSort {
    public static void main(String[] args) {
        String a = "abcdsadhellohfdf";
        System.out.println(AscSort(a));
    }
    /*
    可以转成char数组或者String数组
     */
    private static String AscSort(String str){
        //String[] split = str.split("");
        byte[] split = str.getBytes();
        Arrays.sort(split);
        return new String(split);
    }
}
