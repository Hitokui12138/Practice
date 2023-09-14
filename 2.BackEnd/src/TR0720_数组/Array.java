package TR0720_数组;

public class Array {
    /*
    1.一位数组的声明和初始化
    2.引用
    3.length
    4.遍历
    5.默认初始化值
    6.内存解析
     */
    public static void main(String[] args) {
        //1. 动态初始化，必须给一个长度，只有默认值，需要通过下标进行复制
        int[] arr1 = new int[5];
        arr1[0] = 1;
        arr1[1] = 2;
        //1.1 静态初始化，必须给内容
        String[] arr2 = new String[]{"1","2","3","4","5"};
        String[] arr3 = {"1","2","3","4","5"};
        //1.2 不能既给长度，又给内容
        //int[] arr4 = new int[3]{1,2,3};

        //默认值
        //引用类型（String）默认值为null，而不是“”

        //6
        //引用类型指向 堆内存 一块地址的首地址
        arr2 = arr3;
        arr2[1] = "test";
        System.out.println(arr3[1]);
        //java的垃圾回收机制会把没有指向的内存清除掉，c语言没有，因此c语言需要手动清空变量
    }
}
