package TR0808_范型枚举注解;

public class T04_Comparator {
    public static void main(String[] args) {
        //Arrays.sort();
    }
}
class Goods implements Comparable<Goods>{
    @Override
    public int compareTo(Goods o) {
        //就不需要instanceOf的判断了
        return 0;
    }
}