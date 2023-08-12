package TR0725;

public class T2_Test {
    public static void main(String[] args) {
        int[] arr = new int[10];
        char[] arr1 = {'a','b','c'};
        //打印出来一样吗？不一样，因为调用的是重载的两个方法
        System.out.println(arr);//println(Object X)，打印地址
        System.out.println(arr1);//println(char[] x)，循环数组
    }
}
