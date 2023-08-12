package TR0721;

import java.util.Arrays;
import java.util.Random;

public class caipiao {
	public static void main(String[] args) {
		int[] arr = new int[30];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i + 1;
		}
		System.out.println(Arrays.toString(arr));
		
		int[] arr2 = new int[6];//机打结果
		Random r = new Random();
		for (int i = 0; i < arr2.length; i++) {
			int rnum = r.nextInt(arr.length - i);
			arr2[i] = arr[rnum];
			//交换随机数
			int temp = arr[arr.length - i - 1];
			arr[arr.length - i - 1] = arr[rnum];
			arr[rnum] = temp;
		}
		System.out.println(Arrays.toString(arr2));
	}
}
