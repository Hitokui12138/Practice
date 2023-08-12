package TR0728;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

/**
 * 使用Vector替代数组,因为数组是定长的
 * @author Admin
 *
 */
public class T3_Vector {
	public static void main(String[] args) {
		Vector v= new Vector<>();
		Scanner input = new Scanner(System.in);
		System.out.println("Enter Score:");
		while(true) {
			int score = input.nextInt();
			if(score == -1) {
				break;
			}
			//1.5之前的写法
			//Integer score1 = new Integer(score); //不用再包装了
			v.addElement(score);//因为有自动装箱,不需要上面那个
		}
		System.out.println(v.toString());
		System.out.println(v);//效果一样的
		
		//2.取得最大值
		Integer max = (Integer)v.elementAt(0);
		for (int i = 0; i < args.length; i++) {
			Integer i1 = (Integer)v.elementAt(i);
			if(max < i1) {
				max = i1;
			}
		}
		System.out.println(max);
	}
}
