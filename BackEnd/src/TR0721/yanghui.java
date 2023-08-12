package TR0721;

import java.util.Scanner;

public class yanghui {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("请输入行数:");
		int num = input.nextInt();
		
		//动态初始化内层数组
		int arr[][] = new int[num][];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new int[i + 1];
			arr[i][0] = 1;
			arr[i][i] = 1;
			
			for (int j = 1; j < arr[i].length - 1; j++) {
				arr[i][j] = arr[i - 1][j] + arr[i - 1][j - 1];
			}
		}
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j]+"\t");
			}
			System.out.println("\n");
		}

	}
}
