package TR0720.homework0720;

import java.util.Arrays;
import java.util.Scanner;

public class Q12_duijiaoxianqingling {
    public static void main(String[] args) {
        int[] arr[] = new int[4][4];
        int h = arr.length;
        int l = arr[0].length;
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < l; j++) {
                System.out.println("输入arr[" + i + "]{" + j + "]:");
                arr[i][j] = s.nextInt();
                //直接清零
                if(i==j){
                    arr[i][j] = 0;
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
    }
}
