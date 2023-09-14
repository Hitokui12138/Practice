package com.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
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
public class DruidUtils {

    private static DataSource datasource;

    //初始化DruidDataSource
    static {
        Properties prop = new Properties();
        InputStream is = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            prop.load(is);//如果读不出来的话可以试试Rebuild Project
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

        //测试用
        public static void main(String[] args) throws SQLException {
            System.out.println(DruidUtils.getConn());
        }
}
