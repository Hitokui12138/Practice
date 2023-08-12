package TR0721;

import java.util.Arrays;

public class lianxi1 {
	public static void main(String[] args) {
		int[] arr = {2,3,4,7,4,5,9,5,2};
		for (int i = 0; i < Math.floorDiv(arr.length, 2); i++) {
			int temp = arr[i];
			arr[i] = arr[arr.length - i -1];
			arr[arr.length - i - 1] = temp;
		}
		System.out.println(Arrays.toString(arr));
	}
}
