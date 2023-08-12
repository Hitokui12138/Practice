package TR0725;

/**
 * JavaBean是一种Java语言写成的可重用组件
 * 1.指的是符合以下标准的Java类
 * 1.1 类是公共的
 * 1.2 有一个无参的公共构造器
 * 1.3 属性都是私有的，具有对应的getter，setter方法
 *
 * 用户可以使用JavaBean来将功能，处理，值，数据库访问等等任何用java代码创建的东西打包
 * 其他开发者可以使用这些对象
 * JavaBean提供了一种随时复制粘贴的功能而不需要关心任何变化
 */
public class T5_JavaBean {
    private String name;

    //1.无参构造器 alt+/
    // cmd+n 生成各种东西
    public T5_JavaBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
