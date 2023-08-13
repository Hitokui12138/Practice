package TR0808_范型SystemMath枚举.T03_Fruit;

import java.util.Arrays;

public class FruitNew <T>{
    T t;
    public void add(T t){
        System.out.println(t.getClass().getName());
    }

    public void get(T t){
        System.out.println(t.getClass().getName());
    }

    public static <E> E[] sort(E ... arr){
        Arrays.sort(arr);
        return arr;
    }

    public static int[] sort2(int[] arr){
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length -i -1; j++) {
                if(arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j]= arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        return arr;
    }



}
