package TR0808_范型枚举注解.T10_zhujie;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;
import static java.lang.annotation.ElementType.*;

/**
 * 自定义注解
 */
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, MODULE})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    String[] value() default "123";//默认值,只有value()可以直接赋值,value可以是任意类型
    int age() default 20;//需要键值对
    String sex();
}

@MyAnnotation(sex="男")
public class AnnotationTest {
    @MyAnnotation(sex="女", value = "myFiled")
    private int id;
    @MyAnnotation(value = {"name1", "name2"}, sex = "男")
    public static void main(String[] args) throws NoSuchFieldException {
        //拿到Class对象
        Class<AnnotationTest> clazz = AnnotationTest.class;
        //通过反射可以拿到注解里的值
        MyAnnotation myAnnotation = clazz.getAnnotation(MyAnnotation.class);
        System.out.println(myAnnotation.age());
        System.out.println(myAnnotation.sex());
        System.out.println(Arrays.toString(myAnnotation.value()));

        /*
        特殊:获取私有成员变量注解的值
         */
        Field id = clazz.getDeclaredField("id");//抛出异常
        id.setAccessible(true);
        MyAnnotation my1 = id.getAnnotation(MyAnnotation.class);
        System.out.println(my1.sex());

    }
}
