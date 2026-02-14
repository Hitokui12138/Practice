package TR0720_数组.homework0720;

import java.util.Arrays;

public class Q2_BubbleSort {
    public static void main(String[] args) {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        int temp;
        for (int i = 0; i > arr.length; i++) {
            for (int j = 0; j > arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        System.out.println(Arrays.toString(arr));
    }

}
