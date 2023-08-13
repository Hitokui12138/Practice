package TR0721_二维数组_排序;

public class partFind {
	public static void main(String[] args) {
		int target = 6;
		int[] arr = {1,2,3,4,5,6};
		int index = -1;
		
		int min = 0;
		int max = arr.length - 1;
		int mid = Math.floorDiv(min + max, 2);
		
		//注意这里要写等号
		while(min <= max) {
			if(target > arr[mid]) {
				//在右边
				min = mid + 1;
			}else if(target < arr[mid]) {
				//在左边
				max = mid - 1;
			}else {
				index = mid;
				break;
			}
			//每次循环更新mid
			mid = Math.floorDiv(min + max, 2);
		}
		System.out.println(index);
	}
}
