package TR0103_Lambda.T2_Comperator;

import java.util.Comparator;

public class Goods implements Comparator {
    String name;
    double price;
    public Goods(String name, double price) {
        this.name = name;
        this.price = price;
    }
    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    //先按照name正序排,再按照price倒叙排
    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof Goods && o2 instanceof Goods){
            Goods g1 = (Goods) o1;
            Goods g2 = (Goods) o2;
            return -g1.name.compareTo(g2.name);
        }
        throw new RuntimeException("非法的类型");
    }


}
