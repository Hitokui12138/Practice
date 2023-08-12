package TR0720.homework0720;

import java.util.Arrays;
import java.util.Scanner;

public class Q10_sizhouqingling {
    public static void main(String[] args) {
        int[] arr[] = new int[3][4];
        int h = arr.length;
        int l = arr[0].length;
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                System.out.println("输入arr[" + i + "]{" + j + "]:");
                arr[i][j] = s.nextInt();
            }
        }
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                if(i == 0||i == h-1){
                    arr[i][j] = 0;
                }else if(j == 0||j == l-1){
                    arr[i][j] = 0;
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
    }
}
