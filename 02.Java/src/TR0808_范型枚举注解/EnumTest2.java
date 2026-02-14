package TR0808_范型枚举注解;

public class EnumTest2 {
}
enum SeasonEnum2{
    SPRING("春天"),SUMMER("夏天");

    private final String name;

    private SeasonEnum2(String name){
        this.name = name;
    }
}
