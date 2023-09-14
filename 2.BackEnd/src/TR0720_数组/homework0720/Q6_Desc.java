package TR0720_数组.homework0720;

import java.util.Arrays;

public class Q6_Desc {
    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5,6,7,8,9};
        int temp;
        for(int i = 0; i < Math.floorDiv(arr.length,2); i++){
            temp = arr[i];
            arr[i] = arr[arr.length - i -1];
            arr[arr.length - i - 1] = temp;
        }
        System.out.println(Arrays.toString(arr));
    }
}
