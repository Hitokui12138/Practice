package TR0725;

/**
 * 一个方法体调用它本身
 * 1. 一种隐式循环
 * 2. 要向 已知方向 递归，否则就死循环了
 */
public class T3_Recursion {
    public static void main(String[] args) {
        System.out.println(sum(5));
        System.out.println(factorial(5));
        System.out.println(test3(3));
        System.out.println(test4(7));
    }

    //练习1:累加
    public static int sum(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n + sum(n - 1);//向已知方向（n=1）方向递归
        }
    }
    /*
    sum(3) = 3 + sum(2)
    sum(2) = 2 + sum(1)
    sum(1) = 1 是已知的
     */

    //练习2:阶乘
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    //练习3:f(0)=1,f(1)= 4,f(n+2)=2*f(n+1)+f(n),求f(10)
    //解析： 从最大的n+2解析，f(n)= 2*f(n-1)+f(n-2),因为要向已知方向递归
    public static int test3(int n) {
        if (n == 0) {
            return 1;
        } else if (n == 1) {
            return 4;
        } else {
            return 2 * test3(n - 1) + test3(n - 2);
        }
    }

    //练习4:f(20)=1,f(21)=4,f(n+2)=2*f(n+1)+f(n),求f(10)
    //这个题是解不出来的，因为是往上走的

    //练习5:Fibonacci数列，0 1 1 2 3 5 8 13
    //f(1) = 1 f(2) = 1 f(n+2) = f(n)+f(n+1)
    //f(n) = f(n - 2) + f(n - 1)
    public static int test4(int n) {
        if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 1;
        } else {
            return test4(n - 2) + test4(n - 1);
        }
    }//不过递归是效率最差的一种解法，因为每个值都要算到
}
