package TR0720.homework0720;

import java.util.Arrays;

public class Q8_MN {
    public static void main(String[] args) {
        int[] arr = {4,5,3,2,3,4,6,8,1};
        int m = 3;
        int n = arr.length;
        System.out.println(Arrays.toString(mn(arr,m,n)));
    }

    public static int[] mn(int[] arr, int m, int n){
        if(m > n){
            return arr;
        }
        int temp;
        //往后移动m位
        for(int i = 0; i < m; i++){
            //保存倒数第一个
            temp = arr[arr.length - 1];
            //从倒数第二个开始移动　
            for(int j = arr.length - 2; j >= 0; j--){
                arr[j + 1] = arr[j];
            }
            arr[0] = temp;
            //System.out.println(i+":"+Arrays.toString(arr));
        }
        return arr;
    }
}
