package TR0721_二维数组_排序;

public class bubbleSort {
	public static void main(String[] args) {
		int[] arr = {5,4,3,2,1};
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
		System.out.println(2<<3);
	}
}
