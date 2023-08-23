package com.java;

import com.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName JDBCTest.java
 * @Description TODO
 * @createTime 2023年08月22日 13:07:00
 */
public class JDBCTest {

    @Test
    public void Test1() throws Exception {
        /*
        1.下面这个只是演示,因为Driver里面的静态代码块已经注册过一次了
        Driver driver = new com.mysql.cj.jdbc.Driver(); //驱动
        DriverManager.registerDriver(driver);   //注册驱动
         */
        Class.forName("com.mysql.cj.jdbc.Driver");  //使用这个加载这个类来执行里面的静态代码块,驱动5以后有优化:甚至可以不写这一段,还是写上吧
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp?serverTimezone=GMT", "root", "admin");//建立连接 = 一个新对话
        //Connection conn =  DriverManager.getConnection("jdbc:mysql://emp?serverTimezone=GMT","root","admin");//优化:本地连接可以不用写localhost之类的,好像不行
        /*
        Access denied for user 'root'@'localhost' 用户名或密码出错
        url:需要时区 serverTimezone=GMT ,这个表示中国
         */
        System.out.println("conn = " + conn);
        /*
        Statement接口
         */
    }

    @Test
    public void Test2() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");  //使用这个加载这个类来执行里面的静态代码块,驱动5以后有优化:甚至可以不写这一段,还是写上吧
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/myschool?serverTimezone=GMT", "root", "admin");//优化:本地连接可以不用写localhost之类的
        System.out.println("conn = " + conn);
        /*
        Statement接口,语句,陈述
        	1. boolean execute(String sql) ：可以执行任意的sql 了解 ,查询以外都是false
			2. int executeUpdate(String sql) ：执行DML（insert、update、delete）语句、DDL(create，alter、drop)语句
			* 返回值：影响的行数，可以通过这个影响的行数判断DML语句是否执行成功 返回值>0的则执行成功，反之，则失败。
			3. ResultSet executeQuery(String sql)  ：执行DQL（select)语句
         */
        //1. DDL 这个方法一般只是用来做DDL的,因为返回值没设么意义
        Statement stat = conn.createStatement();
//        boolean flag = stat.execute("CREATE TABLE user(" +
//                "id INT PRIMARY KEY AUTO_INCREMENT," +
//                "name VARCHAR(20)," +
//                "password VARCHAR(20))");
//        System.out.println(flag);//false, 这个flag没什么意义,除了查询以外都是false

        //2. DML 增删改 %ROWCOUNT
        //int rows = stat.executeUpdate("INSERT INTO user VALUES(0,'石原里美','1234')");
        //int rows = stat.executeUpdate("UPDATE user SET name = '新桓结衣', password = '123' WHERE id = '1'");
        int rows = stat.executeUpdate("DELETE FROM user WHERE id = '1'");
        for (int i = 0; i < 20; i++) {
            rows = stat.executeUpdate("INSERT INTO user VALUES(0,'石原里美" + i + "','1234')");
        }
        System.out.println(rows == 1 ? "成功" : "失败");


    }

    @Test
    public void Test3() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");  //使用这个加载这个类来执行里面的静态代码块,驱动5以后有优化:甚至可以不写这一段,还是写上吧
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/myschool?serverTimezone=GMT", "root", "admin");//优化:本地连接可以不用写localhost之类的
        System.out.println("conn = " + conn);
        Statement stat = conn.createStatement();

        //3. DQL 查询语句
        ResultSet rs = stat.executeQuery("SELECT id,name,password FROM user");
        while (rs.next()) {   //类似于迭代器或者游标的fetch
            //用索引取得,不推荐
            //System.out.println(rs.getInt(1) + "--" + rs.getString(2) + "--" + rs.getInt(3));
            //用字段名取得,推荐
            System.out.println(rs.getInt("id") + "--" + rs.getInt("password") + "--" + rs.getString("name"));
        }
        //递进关系的关闭
        rs.close();
        stat.close();
        conn.close();


        //4. DCL 数据控制语句,访问权限和安全级别
    }

    @Test
    public void Test4() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");  //使用这个加载这个类来执行里面的静态代码块,驱动5以后有优化:甚至可以不写这一段,还是写上吧
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/myschool?serverTimezone=GMT", "root", "admin");//优化:本地连接可以不用写localhost之类的
        System.out.println("conn = " + conn);
        //Statement stat = conn.createStatement();

//        String name = "石原里美0";
//        String password = "0";
//        ResultSet rs = stat.executeQuery("SELECT id,name,password FROM user " +
//                "WHERE name = '" + name + "', password = '" + password + "'");
//        if (rs.next()) {
//            System.out.println("登录成功");
//        } else {
//            System.out.println("登陆失败");
//        }
        /*
        sql注入
            一般用户输入: 石原里美0  -> '石原里美0'
            sql注入: 石原里美0' OR 1=1 --   ->    '石原里美0石原里美0' OR 1=1 -- ' 密码的条件被注释掉了
            由于1=1恒等,因此永远可以登陆成功,Statement,因此如果被修改,可能会发生安全问题
         解决方案:不要用拼接的方式组合单引号,使用另一个接口
         */
        //整理,准备语句
        //原来的是拼接单引号,现在是用占位符,先给占位符赋值,然后再自动生成单引号
        /*
        优点:
        1.防止SQL注入
        2.效率更高
         */
        String name = "石原里美0";
        String password = "1234";

        PreparedStatement pstm = conn.prepareStatement("SELECT id, name, password FROM user WHERE name = ? AND password = ?");
        pstm.setString(1, name);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            System.out.println("登录成功");
        } else {
            System.out.println("登陆失败");
        }

        /*
        2.为什么效率更高?
        使用之前的方法发送多条INSERT需要解析多次
        现在这种方法发送问号,会被放到sql的高速缓冲区,第二条会被认为和之前是同一条语句,这样就不会重复解析了参数可以不一样
        结论就是后续应该使用PrepareStatement接口
         */
//        PreparedStatement pstm2 = conn.prepareStatement("INSERT INTO user VALUES (0,?,?)");
//        pstm2.setString(1, "星野源");
//        pstm2.setString(2, "1234");
//        int rows = pstm2.
    }

    /**
     * 汇总
     * @throws Exception
     */
    @Test
    public void Test5() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");  //使用这个加载这个类来执行里面的静态代码块,驱动5以后有优化:甚至可以不写这一段,还是写上吧
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/myschool?serverTimezone=GMT", "root", "admin");//优化:本地连接可以不用写localhost之类的
        System.out.println("conn = " + conn);
        //增
//        PreparedStatement pstm = conn.prepareStatement("INSERT INTO user VALUES(0,?,?)");
//        pstm.setString(1, "星野源");
//        pstm.setString(2, "12345");
//        int rows = pstm.executeUpdate(); //DML语句要用这个
//        System.out.println(rows); //1

        //删
//        PreparedStatement pstm = conn.prepareStatement("DELETE FROM user WHERE id = ?");
//        pstm.setString(1, "22");
//        int rows = pstm.executeUpdate();
//        System.out.println(rows);

        //改
//        PreparedStatement pstm = conn.prepareStatement("UPDATE user SET name = ? WHERE id = ?");
//        pstm.setString(1, "星野源21");
//        pstm.setString(2, "21");
//        int rows = pstm.executeUpdate();
//        System.out.println(rows);

        //查
        PreparedStatement pstm = conn.prepareStatement("SELECT id, name, password FROM user WHERE name = ? AND password = ?");
        pstm.setString(1, "星野源21");
        pstm.setString(2, "1234");
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            System.out.println("登录成功");
        } else {
            System.out.println("登陆失败");
        }
        //递进关系的关闭
        rs.close();
        pstm.close();
        conn.close();
    }

    /*
    把查询结果放入集合
     */
    @Test
    public void Test6() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/myschool?serverTimezone=GMT", "root", "admin");
        System.out.println("conn = " + conn);

        PreparedStatement pstm = conn.prepareStatement("SELECT id, name, password FROM user");
        ResultSet rs = pstm.executeQuery();

        ArrayList<User> userlist = new ArrayList<>();


        while (rs.next()){
            userlist.add(new User(rs.getInt("id"),rs.getString("name"),rs.getString("password")));
        }

        //数组的遍历的四种方式
        /*
        1. fori list.size() list.get(i)
        2. for(User user : list)
        3. list.getIterator()
        4. userlist.forEach(System.out :: println);

         */
        userlist.forEach(System.out :: println);

        rs.close();
        pstm.close();
        conn.close();
    }

    /**
     * 设计模式 MVC 工厂 模板 单例
     * DAO
     * 1. DAO实体类
     * 2. DAO接口
     * 3. DAO实现类
     * 3. 工具类
     */
}
