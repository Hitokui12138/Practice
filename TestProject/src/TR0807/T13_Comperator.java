package TR0807;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 引用类型比较大小
 * 自然排序Comparable, 定制排序Comparator
 * 1.Comparable接口 CompareTo()
 * 像是String包装类等等都实现了Comparable接口
 * 2.Comparator compare()
 * 想倒叙排序需要使用定制的comparator
 */
public class T13_Comperator {
    @Test
    public void Test(){
        String[] strs = {"ww","aa","ss","dd"};
        Arrays.sort(strs);
        System.out.println(Arrays.toString(strs));

        Goods[] goods = {
                new Goods("XiaomiMouse", 100),
                new Goods("AppleMouse", 200),
                new Goods("LenovoMouse", 300),
                new Goods("LogitechMouse", 400),
                new Goods("RapooMouse", 400)
        };
        Arrays.sort(goods);//java.lang.ClassCastException: class TR0807.Goods cannot be cast to class java.lang.Comparable
        System.out.println(Arrays.toString(goods));

        //想倒叙排序需要使用定制的comparator
        //Arrays.sort(T[] t, Comparator<? super T> c)
        Arrays.sort(goods,new Comparator<Goods>(){  //匿名函数?
            @Override
            public int compare(Goods o1, Goods o2) {
                if(o1 instanceof Goods && o2 instanceof Goods){
                    Goods g1 = (Goods) o1;
                    Goods g2 = (Goods) o2;
                    return -g1.name.compareTo(g2.name);
                }
                throw new RuntimeException("非法的类型");
            }
        });
        System.out.println(Arrays.toString(goods));

        //先价格正序,再价格倒叙
        Arrays.sort(goods,new Comparator<Goods>(){
            @Override
            public int compare(Goods o1, Goods o2) {
                if(o1 instanceof Goods && o2 instanceof Goods){
                    Goods g1 = (Goods) o1;
                    Goods g2 = (Goods) o2;
                    int res = Double.compare(g1.price, g2.price);
                    if(res!=0){
                        return res;
                    }else {
                        return -g1.name.compareTo(g2.name);
                    }
                }
                throw new RuntimeException("非法的类型");
            }
        });
        System.out.println(Arrays.toString(goods));
    }
}

class Goods implements Comparable{
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
    public int compareTo(Object o) {
        if (o instanceof Goods){
            Goods other = (Goods)o;
            int res = this.name.compareTo(other.name);
            System.out.println(this.name+"<===>"+other.name);
            if(res!=0){
                return res;
            }else{
                //return -Double.compare(this.price, other.price);
                if(this.price> other.price){
                    return 1;
                }else{
                    return -1;
                }
            }
        }else {
            throw new RuntimeException("类型不匹配");
        }
    }

    //想要降序的话需要用Comparator定制排序
    //重写compare(o1,o2)

}
