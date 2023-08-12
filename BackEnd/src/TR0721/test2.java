package TR0721;

import java.util.Arrays;

public class test2 {
	public static void main(String[] args) {
		int a = 5, b = 3, c = 7;
		int arr[] = {a,b,c};
		int temp;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length -i -1; j++) {
				if(arr[j] > arr[j+1]) {
					 temp = arr[j + 1];
					 arr[j + 1] = arr[j];
					 arr[j] = temp;
				}
			}
		}
		System.out.println(Arrays.toString(arr));
	}
}
