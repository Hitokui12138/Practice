package TR0808_范型枚举注解;

/**
 * 员工状态类
 */
public enum T09_Status {
    FREE("FREE"),VOCATION("VOCATION"),BUSY("BUSY");
    private final String NAME;

    T09_Status(String NAME) {
        this.NAME = NAME;
    }

    public String getNAME() {
        return NAME;
    }

    @Override
    public String toString() {
        return "T09_Status{" +
                "NAME='" + NAME + '\'' +
                '}';
    }
}
