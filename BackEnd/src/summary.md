### 集合的体系以及区别？
1. Collection: 单列结构 
    1. List:有序可重复
        1. ArrayList:底层由数组实现,线程不安全,遍历效率高
        2. LinkedList:由双向链表实现,插入和删除操作快
        3. Vector:有数组实现,线程安全,用的少
    2. Set:不可重复
        1. HashSet:无序不可重复,由Hash表实现
        2. SortSet--->TreeSet:自动对存入的对象进行排序,由二叉树实现
2. Map:双列结构,Key是Set,Value是Collection,本身是Entry<Key,Value>
    1. HashMap:线程不安全.由Hash表实现
    2. reeMap:自动对Key排序

### 异常的体系以及区别？
1. Throwable
    1. Error:虚拟机无法解决的错误,JVM内部错误或资源耗尽等 
    2. Exception:可以针对处理的异常 
        1. RuntimeException:运行时异常,应该避免的编程逻辑错误和Bug 
        2. CheckedException:编译时就要求处理或抛出的异常
    
### I/O的体系以及区别？
1. 抽象基类(了解即可)
    1. 字节流:InputStream,OutputStream 
        - 1byte = 16bit
        - 可以处理2进制文件(图片等),既可以处理二进制文件,又可以处理文本文件
    2. 字符流:Reader,Writer,
        - 1char = 2byte = 16bit
        - String默认以Unicode规范(2字节)编码储存在内存里,因此读入时标注编码方式 
        - 字符流 = 字节流 + 解码
        - 用于处理文本
2. 访问文件(使用)
    1. 字节流:FileInputStream,FileOutputStream
       - 每调用一次read都会伴随一次磁盘的读写,效率低
    2. 字符流:FileReader,FileWriter
3. 缓冲流(常用)
   - 内部其实是访问文件用的几个类,只是包装了一下
    1. 字节流:BufferedInputStream,BufferedOutputStream
       - 里面定义了一个8kb的数组,先把数组先放到8kb的缓冲数组里,当8kb的数组满了,才会一次性取得,因此速度快
    2. 字符流:BufferedReader,BufferedWriter
4. 转换流
   1. 作用
        1. 字符流操作起来更简单(可以一行一行地读),因此提供了字节流->字符流地方法
        2. 也能对编码方式进行转换 ( UTF-8->字节流->通过转换流转字符流->GBK->字符->字节 )
   2. 实现
        1. InputStreamReader(字节流,编码):   InputStream->Reader
        2. OutputStreamWriter:  OutputStream->Writer
   3. GBK->UTF-8
5. 对象流
   - 对象->序列化->保存到文件里
6. 标准输入输出流
    - System.in System.out表示标准输入输出设备