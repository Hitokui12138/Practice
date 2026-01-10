package TR0808_范型枚举注解.T03_Fruit;

import java.util.Arrays;

public class TestMain {
    public static void main(String[] args) {
        AppleNew appleNew = new AppleNew();
        appleNew.add(new AppleNew());
        //appleNew.add("123"); 编译时就会报错

        BananaNew bananaNew = new BananaNew();
        //bananaNew.add("123");//而这个会在运行时报错

        System.out.println(Arrays.toString(FruitNew.sort("a", "c", "b")));

        /*
        泛型没有继承,继承关系只看左边
         */
        //注意虽然FruitNew是AppleNew的父类,但 FruitNew<FruitNew>和 FruitNew<AppleNew> 没有并列关系,他们是并列的
        FruitNew<AppleNew> f1 = new FruitNew<>();//也可以不写<>
        FruitNew<FruitNew> f2 = new FruitNew<>();
        //FruitNew<String> Apple<String> 泛型相同时是父子关系,
    }

    //通配符?的作用是可以加上extends等等限定词
    //用来对传进来的泛型的进行约束
    public static void test(FruitNew<? extends FruitNew> fruitNew){

    }
}
