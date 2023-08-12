package TR0720.homework0720;

import java.util.Arrays;

/**
 * 打印斐波那契数列前20位
 * F(0) = 0, F(1) = 1
 * F(n) = F(n-1) + F(n-2)
 */
public class Q1_Fibonacci {
    public static void main(String[] args) {
        int n = 20;
        int[] arr = new int[20];
        arr[0] = 0;
        arr[1] = 1;
        for(int i = 2; i < 20; i++){
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        System.out.println(Arrays.toString(arr));
    }

}
