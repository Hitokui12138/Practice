package TR0720.homework0720;

import java.util.Arrays;

public class Q7_MaxMin {
    public static void main(String[] args) {
        int[] arr = {4,5,7,8,9,3,4,6,8};
        int max = arr[0];
        int a = 0;
        int min = arr[0];
        int b = 0;
        for(int i = 1; i < arr.length; i++){
            if(arr[i] > max){
                max = arr[i];
                a = i;
            }
            if(arr[i] < min){
                min = arr[i];
                b = i;
            }
        }
        System.out.println("Max:" + max + "; Min:" + min);
        int temp;
        temp = arr[0];
        arr[0] = arr[a];
        arr[a] = temp;
        temp = arr[arr.length - 1];
        arr[arr.length - 1] = arr[b];
        arr[b] = temp;
        System.out.println(Arrays.toString(arr));
    }
}
