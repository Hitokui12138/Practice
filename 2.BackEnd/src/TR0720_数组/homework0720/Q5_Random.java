package TR0720_数组.homework0720;

import java.util.Arrays;

public class Q5_Random {
    public static void main(String[] args) {
        //System.out.println((int)(Math.random() * (31 - 1)));
        int[] arr = new int[10];
        int i = 0;
        while(i < 10) {
            boolean duplicate = false;
            int num = (int) (Math.random() * 30 + 1);
            for (int j = 0; j < 10; j++) {
                if (arr[j] == num) {
                    duplicate = true;
                }
            }
            if (duplicate == false) {
                arr[i] = num;
                i++;
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
