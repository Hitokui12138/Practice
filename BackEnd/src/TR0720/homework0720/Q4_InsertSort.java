package TR0720.homework0720;

import java.util.Arrays;

public class Q4_InsertSort {
    public static void main(String[] args) {
        int[] arr= {1,2,4,5};
        int[] arr2 = new int[arr.length + 1];
        int num = 3;
        for(int i = 0; i < arr.length - 1; i++){
            if(arr[i] < num && arr[i + 1] > num){
                //插入i+1的位置
                //j=i+1时结束
                for(int j = arr.length; j > i + 1; j--){
                    arr2[j] = arr[j - 1];
                }
                //插入
                arr2[i + 1] = num;
                for(int k = i; k >= 0; k--){
                    arr2[k] = arr[k];
                }
                break;
            }
        }
        System.out.println(Arrays.toString(arr2));
    }
}
