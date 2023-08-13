package TR0807_String_Date_比较;

import java.util.Arrays;

public class T02_Zuiduanxiangtong {
    public static void main(String[] args) {
        String a = "abcdsadhellohfdf";
        String b = "abcdcvhelldhfdf";//10
        String[] c = sameLargeStr(a,b);
        System.out.println(Arrays.toString(c));
    }
/*
    先比较0~10,然后0~9,1~10,这样
 */
    private static String[] sameLargeStr(String a,String b){
        int len1 = a.length();
        int len2 = b.length();
        String max = len1>len2?a:b;
        String min = len1>len2?b:a;
        StringBuilder sBuilder = new StringBuilder();


        for (int i = 0; i < min.length(); i++) {
            for (int x = 0, y = min.length() - i; y < min.length() ; x++, y++) {
                String sub = min.substring(x,y+1);
                System.out.println(sub+":"+x+":"+y);
                if(max.contains(sub)){
                    System.out.println(sub+":"+x+":"+y);
                    sBuilder.append(sub+",");
                    //String split = sBuffer.toString().replaceAll(",$","").split("\\,")
                }
            }
            if(sBuilder.length()>0){break;}
        }
        if(sBuilder.length()>0){
            return sBuilder.toString().replaceAll(",$", "").split("\\,");
        }
        return null;
    }
}
