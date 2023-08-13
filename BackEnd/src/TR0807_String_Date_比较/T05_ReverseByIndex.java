package TR0807_String_Date_比较;

/**
 * 指定反转
 */
public class T05_ReverseByIndex {
    public static void main(String[] args) {
        String a = "shdabsadg";
        System.out.println(reverse(a,3,6));
    }
    private static String reverse(String str, int start, int end){
        String temp = str.substring(0, start);
        for (int i = end; i >= start; i--) {
            temp += str.charAt(i);
        }

        temp += str.substring(end+1);
        return temp;
    }
//    private static String reverse2(String str, int start, int end){
//        StringBuilder temp = new StringBuilder(str.substring(0, start));
//        for (int i = end; i >= start; i--) {
//            temp += str.charAt(i);
//        }
//
//        temp += str.substring(end+1);
//        return temp;
//    }
}
