package TR0807_String_Date_比较;

/**
 * 1.不可变性
 * String内部是一个Final数组..
 * 因此String再拼接时是效率比较低的
 */
public class T01 {
    public static void main(String[] args) {
        String s = "dasdaDASdsa";
        /*
        与char[],byte[]互转
         */
       char[] c = s.toCharArray();
       String s2 = new String(c,0,c.length);


        //String[] c = s.split("");
        System.out.println(c[1]);



    }
}
