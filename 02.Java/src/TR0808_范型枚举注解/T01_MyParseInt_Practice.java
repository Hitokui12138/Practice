package TR0808_范型枚举注解;

/**
 * 自己做一个Integer.parseInt
 * ASCII 0->48
 */
public class T01_MyParseInt_Practice {
    public static void main(String[] args) {
        String s = "123";
        System.out.println(myParseInt(s));
    }

    private static int myParseInt(String s) {
        int num = 1;
        int sum = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int n = s.charAt(i) - 48;
            sum += n * num;
            num *= 10;
        }
        return sum;
    }
}

