# 注解 Annotation
- 主要和反射一起使用,框架=注解+反射+设计模式
- 在JavaSE里面用的比较多,用来替换
# 常用
1. @Override
2. @Deprecated  用于标注一个方法已经弃用
3. @SuppressWarnings    抑制编译器警告
# 元注解
1. Retention(Source,class,runtime)
2. Target({TYPE, FIELD, METHOD})
3. Documented
4. Inherited
5. 在不修改源代码的情况下可以打几个标记,从而在运行时拿到标记的值

看一下@SupperssWarning