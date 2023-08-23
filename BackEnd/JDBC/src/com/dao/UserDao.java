package com.dao;

import com.entity.User;
import com.mysql.cj.jdbc.JdbcConnection;
import com.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName UserDao.java
 * @Description TODO
 * @createTime 2023年08月22日 15:49:00
 *
 * DAO
 * 表名 + DAO
 * 封装增删改查
 */
public class UserDao {
    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    /**
     * 添加用户
     * @param user
     * @return
     */
    public int addUser(User user){
        String sql = "INSERT INTO user VALUES (0,?,?)";
        try{
            conn = JdbcUtils.getConn();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, user.getName());
            pstm.setString(2, user.getPassword());
            return pstm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, pstm, rs);
        }
        return 0; //0表示失败了
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    public int updateUser(User user){
        String sql = "UPDATE user SET name = ?,password = ? WHERE id = ?";
        try{
            conn = JdbcUtils.getConn();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, user.getName());
            pstm.setString(2, user.getPassword());
            pstm.setInt(3, user.getId());
            return pstm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, pstm, rs);
        }
        return 0; //0表示失败了
    }

    /**
     * 删除用户
     * @param
     * @return
     */
    public int deleteUser(int id){
        String sql = "DELETE FROM user WHERE id = ?";
        try{
            conn = JdbcUtils.getConn();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            return pstm.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, pstm, rs);
        }
        return 0; //0表示失败了
    }

    /**
     * 查找
     * @param
     * @return
    */
    public ArrayList<User> findAllUser(){
        String sql = "SELECT * FROM user";
        ArrayList<User> userList = new ArrayList<>();
        try{
            conn = JdbcUtils.getConn();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            User user = null;
            while (rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, pstm, rs);
        }
        return null; //0表示失败了
    }


}
