package com.java;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName JDBCTest.java
 * @Description TODO
 * @createTime 2023年08月22日 13:07:00
 */
public class JDBCTest {

    @Test
    public void Test1() throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver(); //驱动
        DriverManager.registerDriver(driver);   //注册驱动
        Connection conn =  DriverManager.getConnection("jdbc:mysql://localhost:3306/emp?serverTimezone=GMT","root","admin");//建立连接 = 一个新对话
        /*
        Access denied for user 'root'@'localhost' 用户名或密码出错
        url:需要时区 serverTimezone=GMT ,这个表示中国
         */
        System.out.println("conn = " + conn);
    }
}
