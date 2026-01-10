package TR0808_范型枚举注解;

/**
 * jdk 1.5 枚举类,注解
 * 当需要定义一组有限的且确定的常量(对象)时,应该定义一个枚举类
 * 不允许改动,里面的对象private static final
 *
 * 看一下ppt
 * 1.使用enum的类默认继承java.long.enum,不能继承其他类
 * 2.构造器 私有的
 */

interface Test{
    void show();
}
enum SeasonEnum implements Test{
    //可以到src里看一下这个类的class文件,需要反编译一下 XJad
    //SPRING,SUMMER,AUTUMN,WINTER;//定义了四个对象,相当于饿汉模式

    //因为下面定义了有参构造器,而且enum的构造器有有参的时候,就不能再有无参的了
    SPRING("春天"){
        //如果实现了接口,则每个对象都要重写方法
        @Override
        public void show(){
            System.out.println("show春天");
        }
    },
    SUMMER("夏天"){
        @Override
        public void show(){
            System.out.println("show夏天");
        }
    };
    //
    String seasonName;

    SeasonEnum(String seasonName) {
        this.seasonName = seasonName;
    }


    @Override
    public String toString() {
        return super.toString();
    }

}

public class T08_enum {
    public static void main(String[] args) {
        SeasonEnum[] values = SeasonEnum.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println(values[i]);
        }

        //获取一个对象的两种方法
        SeasonEnum s1 = SeasonEnum.valueOf("SPRING");
        System.out.println(s1);
        SeasonEnum s2 = SeasonEnum.SUMMER;//因为定义里是public final
    }
}


