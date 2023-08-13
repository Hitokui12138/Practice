package TR0808_范型SystemMath枚举;

/**
 * System 系统类
 * in,out,err
 * gc(),getProperty()
 *
 *
 */
public class T05_System {
    public static void main(String[] args) {
        System.err.println("红色字体");
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("os.version"));
    }
}
