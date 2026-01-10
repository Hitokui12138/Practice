package TR0720_数组.homework0720;

import java.util.Arrays;
import java.util.Scanner;

public class Q9_1423jiaohuan {
    public static void main(String[] args) {
        int[] arr[] = new int[4][4];
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.println("输入arr[" + i + "]{" + j + "]:");
                arr[i][j] = s.nextInt();
            }
        }
        //第一行和第四行交换
        for (int i = 0; i < arr.length; i++) {
            int temp = arr[0][i];
            arr[0][i] = arr[3][i];
            arr[3][i] = temp;
        }
        //第二行和第三行交换
        for (int i = 0; i < arr.length; i++) {
            int temp = arr[1][i];
            arr[1][i] = arr[2][i];
            arr[2][i] = temp;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
    }
}
