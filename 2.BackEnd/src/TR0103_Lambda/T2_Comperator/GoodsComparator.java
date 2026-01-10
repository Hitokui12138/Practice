package TR0103_Lambda.T2_Comperator;

import java.util.Comparator;

public class GoodsComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof Goods && o2 instanceof Goods){
            Goods g1 = (Goods) o1;
            Goods g2 = (Goods) o2;
            return g1.price > g2.price?1:-1;
        }
        throw new RuntimeException("非法的类型");
    }
}
