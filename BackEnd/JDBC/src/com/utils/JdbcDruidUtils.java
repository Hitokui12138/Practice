package com.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.sun.tools.javac.Main;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName JdbcDruidUtils.java
 * @Description TODO
 * @createTime 2023年08月24日 10:01:00
 */
public class JdbcDruidUtils {
    /**
     * 获取连接和
     */
    private static DataSource datasource;

    static {
        Properties prop = new Properties();
        InputStream is = JdbcDruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            prop.load(is);
            datasource = (DruidDataSource) DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        //获取连接
        public static Connection getConn() throws SQLException {
            return datasource.getConnection();
        }
        //关闭连接
        public static void close(Connection conn, Statement pstm, ResultSet rs){
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

        //
        public static void main(String[] args) throws SQLException {
            System.out.println(JdbcDruidUtils.getConn());
        }


}
