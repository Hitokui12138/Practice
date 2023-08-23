package com.utils;

import java.sql.*;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName JdbcOracleUtils.java
 * @Description TODO
 * @createTime 2023年08月23日 08:37:00
 */
public class JdbcOracleUtils {
    //oracle.jdbc.OracleDriver
    //jdbc:oracle:thin:@localhost:1521:XE
    //协议:数据库:
    public static final String DRIVER = "oracle.jdbc.OracleDriver";
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    public static final String USER = "scott";
    public static final String PASSWORD = "tiger";

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
        System.out.println(JdbcOracleUtils.getConn()); //测试
    }
}
