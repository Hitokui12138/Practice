package com.dao;

import com.entity.User;
import com.mysql.cj.jdbc.JdbcConnection;
import com.utils.DruidUtils;
import com.utils.JdbcUtils;
import oracle.jdbc.OracleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName UserDao.java
 * @Description TODO
 * @createTime 2023年08月22日 15:49:00
 * <p>
 * DAO
 * 表名 + DAO
 * 封装增删改查
 */
public class UserDao {
    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;
    private CallableStatement cst;

    //事务专用
    private PreparedStatement pstm1;
    private PreparedStatement pstm2;
    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    public int addUser(User user) {
        String sql = "INSERT INTO user VALUES (0,?,?)";
        try {
            conn = JdbcUtils.getConn();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, user.getName());
            pstm.setString(2, user.getPassword());
            return pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, pstm, rs);
        }
        return 0; //0表示失败了
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    public int updateUser(User user) {
        String sql = "UPDATE user SET name = ?,password = ? WHERE id = ?";
        try {
            conn = JdbcUtils.getConn();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, user.getName());
            pstm.setString(2, user.getPassword());
            pstm.setInt(3, user.getId());
            return pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, pstm, rs);
        }
        return 0; //0表示失败了
    }

    /**
     * 删除用户
     *
     * @param
     * @return
     */
    public int deleteUser(int id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try {
            conn = JdbcUtils.getConn();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            return pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, pstm, rs);
        }
        return 0; //0表示失败了
    }

    /**
     * 查找
     *
     * @param
     * @return
     */
    public ArrayList<User> findAllUser() {
        String sql = "SELECT * FROM user";
        ArrayList<User> userList = new ArrayList<>();
        try {
            conn = DruidUtils.getConn();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            User user = null;
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.close(conn, pstm, rs);
        }
        return null; //0表示失败了
    }

    /**
     * 使用MYSQL 的procedure返回多个结果集
     * CREATE PROCEDURE getEmpDept()
     * BEGIN
     * SELECT * FROM emp;
     * END;
     * <p>
     * call getEmpDept;
     */
    public void getEmpDept() {
        String sql = "{call getEmpDept()}";
        try {
            conn = JdbcUtils.getConn();
            cst = conn.prepareCall(sql);

            System.out.println("第一个查询");
            rs = cst.executeQuery();//为了取得结果集,使用这个
            while (rs.next()) {
                System.out.println(rs.getInt("empno") + "--" + rs.getInt("deptno"));
            }

            System.out.println("第二个查询");
            if (cst.getMoreResults()) {    //判断有没有下一个结果集,不能用while,
                rs = cst.getResultSet();    //这里不能用executeQuery
                while (rs.next()) {
                    System.out.println(rs.getObject("deptno") + "--" + rs.getObject("dname"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, cst, rs);
        }
    }


    /**
     * 分页
     */
    public void page(int page, int rows) {
        String sql = "{call page2(?,?)}";

        int i = (page - 1) * rows;
        int j = rows;

        try {
            conn = JdbcUtils.getConn();
            cst = conn.prepareCall(sql);
            cst.setInt(1, i);
            cst.setInt(2, j);

            System.out.println("第" + page + "页");
            rs = cst.executeQuery();    //为了取得结果集,使用这个executeQuery
            while (rs.next()) {
                System.out.println(rs.getObject("empno") + "--" + rs.getObject("ename") + "--" + rs.getObject("deptno"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, cst, rs);
        }
    }


    /**
     * 事务,转账
     */
    public void transfer(int sid, int did, int money){
        String sql1 = "UPDATE account SET balance = balance - ? WHERE id = ?";
        String sql2 = "UPDATE account SET balance = balance + ? WHERE id = ?";

        try {
            conn = JdbcUtils.getConn();
            conn.setAutoCommit(false);//set @autocommit = 0//手动提交

            pstm1 = conn.prepareStatement(sql1);
            pstm2 = conn.prepareStatement(sql2);

            pstm1.setInt(1, sid);
            pstm1.setInt(2, money);

            pstm2.setInt(1, did);
            pstm2.setInt(2, money);

            pstm1.executeUpdate();  //MYSQL默认是自动提交
            //int i = 10/0;//这里抛出异常
            pstm2.executeUpdate();

            System.out.println("转账成功");
            conn.commit();//提交事务

        } catch (SQLException throwables) {
            try {
                conn.rollback();//回滚事务
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }finally {
            try {
                conn.setAutoCommit(true);//相当于关闭事务,不过close相当于窗口断了,再打开会复原
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            JdbcUtils.close(conn, pstm1, rs);
            JdbcUtils.close(conn, pstm2, rs);
        }

    }

    /**
     * MYSQL带异常处理的事务
     * @param sid
     * @param did
     * @param money
     * @return
     */
    public int transfer_pro(int sid, int did, int money){
        String sql = "{call transfer(?,?,?,?)}";
        try {
            conn = JdbcUtils.getConn();
            cst=conn.prepareCall(sql);
            cst.setInt(1, sid);
            cst.setInt(2, did);
            cst.setInt(3, money);
            cst.registerOutParameter(4, Types.INTEGER); // 注意MYSQL的TYPE

            cst.execute();
            return cst.getInt(4);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(conn, cst, rs);
        }
        return 0;

    }

}
