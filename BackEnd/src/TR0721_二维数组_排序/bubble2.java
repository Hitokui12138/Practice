package TR0721_二维数组_排序;

import java.util.Arrays;

public class bubble2 {
	public static void main(String[] args) {
		int[] arr = {5,4,3,2,1};
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length - i - 1; j++) {
				if(arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}
		Arrays.sort(arr);
		Arrays.fill(arr, 10);
		Arrays.equals(arr, arr);
		System.out.println(Arrays.binarySearch(arr, 2));
		
		System.out.println(Arrays.toString(arr));
	}
}
