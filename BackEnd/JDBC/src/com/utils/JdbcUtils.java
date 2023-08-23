package com.utils;

import java.sql.*;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName JdbcUtils.java
 * @Description TODO
 * @createTime 2023年08月22日 15:30:00
 * <p>
 * 连接数据库的工具类
 */
public class JdbcUtils {
    /**
     * 1. 定义连接数据库的参数,四个常量
     * 这个要求背下来
     */
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/myschool?serverTimezone=GMT&useSSL=false";
    public static final String USER = "root";
    public static final String PASSWORD = "admin";

    static { //只执行一次
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2. 获取链接的方法
     */
    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * 3. 关闭连接
     */
    public static void close(Connection conn, PreparedStatement pstm, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws SQLException {
        System.out.println(JdbcUtils.getConn()); //测试
    }
}

