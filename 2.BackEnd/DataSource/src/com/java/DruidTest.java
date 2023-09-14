package com.java;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName DruidTest.java
 * @Description TODO
 * @createTime 2023年08月24日 09:12:00
 */
public class DruidTest {
    @Test
    public void Test1() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        //硬编码不可取,应该从配置文件读取
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");//包名+类名:全限类名 Driver是类名
        dataSource.setUrl("jdbc:mysql://localhost:3306/myschool?serverTimezone=GMT&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("admin");

        dataSource.setInitialSize(3);
        dataSource.setMaxActive(6);
        dataSource.setMaxWait(-1);//永久等待

        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
        Connection con3 = dataSource.getConnection();
        Connection con4 = dataSource.getConnection();
        Connection con5 = dataSource.getConnection();
        Connection con6 = dataSource.getConnection();
        System.out.println(con1);
        con1.close();

        Connection con7 = dataSource.getConnection();
        System.out.println(con7);

    }

    @Test
    public void test2() throws Exception {
        Properties prop = new Properties();
        InputStream is = DruidTest.class.getClassLoader().getResourceAsStream("druid.properties");
        prop.load(is);
        //DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
        //使用工厂模式
        DruidDataSource dataSource = (DruidDataSource)DruidDataSourceFactory.createDataSource(prop);

        Connection con1 = dataSource.getConnection();
        System.out.println(dataSource.getActiveCount());//
        System.out.println(dataSource.getCreateCount());//一共有三个
        System.out.println("con1=" + con1);
    }
}
