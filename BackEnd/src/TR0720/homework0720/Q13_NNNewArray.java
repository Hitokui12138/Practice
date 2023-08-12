package TR0720.homework0720;

import java.util.Arrays;
import java.util.Scanner;

public class Q13_NNNewArray {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("N*N数组，请输入N：");
        int n = s.nextInt();
        int[] arr[] = new int[n][n];
        int newArr[] = new int[n];
        int max = 0;//给个初期值，下面换成arr[i][0]
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.println("输入arr[" + i + "]{" + j + "]:");
                arr[i][j] = s.nextInt();
                if(j == 0){
                    //max初始化，arr[i][0]
                    max = arr[i][j];
                }
                //获得这一行最大的元素
                if(arr[i][j] > max){
                    max = arr[i][j];
                }
                //一行循环完毕后设给newArr
                if(j == n - 1){
                    newArr[i] = max;
                }
            }
        }
        System.out.println(Arrays.toString(newArr));
    }
}
