package TR0103_Lambda.T2_Comperator;

import java.util.Arrays;
import java.util.Comparator;

public class ComparatorTest {
    public static void main(String[] args) {
        Goods[] goods = {
                new Goods("XiaomiMouse", 500),
                new Goods("AppleMouse", 200),
                new Goods("LenovoMouse", 450),
                new Goods("LogitechMouse", 400),
                new Goods("RapooMouse", 300)
        };
        //1. 使用定义好的comparator
        Arrays.sort(goods,new GoodsComparator());
        System.out.println(Arrays.toString(goods));

        //2. 使用匿名内部类
        Arrays.sort(goods, new Comparator<Goods>() {
            @Override
            public int compare(Goods o1, Goods o2) {
                if(o1 instanceof Goods && o2 instanceof Goods){
                    Goods g1 = (Goods) o1;
                    Goods g2 = (Goods) o2;
                    return g1.price < g2.price?1:-1;
                }
                throw new RuntimeException("非法的类型");
            }
        });
        System.out.println(Arrays.toString(goods));

        //3. 将匿名内部类简化成Lambda函数
        Arrays.sort(goods,(o1,o2)->{
            if(o1 instanceof Goods && o2 instanceof Goods){
                Goods g1 = (Goods) o1;
                Goods g2 = (Goods) o2;
                return g1.price > g2.price?1:-1;
            }
            throw new RuntimeException("非法的类型");
        });
        System.out.println(Arrays.toString(goods));
    }
}
