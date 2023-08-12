package TR0721;

import java.util.Arrays;

/**
 * 二维数组
 * @author Admin
 *
 */
public class ArrayX2 {
	public static void main(String[] args) {
		//1.动态初始化
		//也有一种写法int[]arr[]
		int[][] arr = new int[2][3];
		arr[0][0] = 1;
		arr[0][1] = 1;
		arr[0][2] = 1;
		arr[1][0] = 1;
		arr[1][1] = 1;
		arr[1][2] = 1;
		System.out.println(Arrays.toString(arr));
		
		int[][] arr2 = new int[2][];//只声明行数
		//System.out.println(arr2[0].length); //会报空指针错误,因为arr2[0]的位置上的的数组还没有定义
		arr2[0] = new int[] {1,2,3};//本质上是在一个数组的地址上各加一个数组
		System.out.println(arr2[0].length); 
		
		String[][] arr3 = {{"1","2","3"},{"4","5","6"}};
		
		//x是一维数组,y是二维数组
		int[] x,y[];
	}
}
