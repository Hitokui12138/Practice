package TR0721_二维数组_排序;

import java.util.Arrays;

public class sq {
	public static void main(String[] args) {
		int num = 5;
		int arr[] = new int[2*num];
		for (int i = 0; i < num; i++) {
			arr[i] = i;
		}

		for (int i = num,k = 1; i < 2*num; i++,k++) {
			arr[i] = arr[i - k];
			k++;
		}
		System.out.println(Arrays.toString(arr));
		for (int i = 0; i < arr.length; i++) {
			
		}
		
		System.out.print("      ");
		System.out.println("*");
		for (int i = 0; i < 2 * num; i++) {
			if(i<num-1) {
				int k = num - i;
				while(k>0) {
					System.out.print(" ");
					k--;
				}
			}
			System.out.print("*");
			for (int j = 0; j < arr[i]; j++) {
				System.out.print(" ");
			}
			System.out.println("*");
		}
		System.out.println("*");
	}
}
