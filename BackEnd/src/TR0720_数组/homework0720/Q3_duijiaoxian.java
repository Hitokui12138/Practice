package TR0720_数组.homework0720;

import java.util.Scanner;

public class Q3_duijiaoxian {
    public static void main(String[] args) {
        int[] arr[] = new int[3][3];
        int sum = 0;
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.println("输入arr[" + i + "]{" + j + "]:");
                arr[i][j] = s.nextInt();
            }
            sum += arr[i][i];
        }
        System.out.println(sum);
    }
}
