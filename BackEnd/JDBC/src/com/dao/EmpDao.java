package com.dao;

import com.entity.Emp;
import com.entity.User;
import com.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName EmpDao.java
 * @Description TODO
 * @createTime 2023年08月22日 16:41:00
 */
public class EmpDao {
    private Connection conn;
    private PreparedStatement pstm;
    private ResultSet rs;

    /**
     * 查询每个部门工资最高的两个员工
     */
    public ArrayList<Emp> findTop2Emp(){
        String sql = "SELECT empno, ename,deptno, sal FROM emp o " +
                "WHERE 1 >= (SELECT COUNT(1) FROM emp WHERE deptno = o.deptno AND sal < o.sal) ORDER BY deptno";
        ArrayList<Emp> empList = new ArrayList<>();
        try{
            conn = JdbcUtils.getConn();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            Emp emp = null;
            while (rs.next()){
                emp = new Emp();
                emp.setEmpno(rs.getInt("empno"));
                emp.setEname(rs.getString("ename"));
                emp.setDeptno(rs.getInt("deptno"));
                emp.setSal(rs.getDouble("sal"));
                empList.add(emp);
            }
            return empList;
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, pstm, rs);
        }
        return null; //0表示失败了
    }


    /**
     * 分页
     */
    public ArrayList<Emp> LimitEmp(int limit, int offset){
        String sql = "SELECT empno, ename,deptno, sal FROM emp LIMIT ?,?";
        ArrayList<Emp> empList = new ArrayList<>();
        try{
            conn = JdbcUtils.getConn();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, limit);
            pstm.setInt(2, offset);

            rs = pstm.executeQuery();
            Emp emp = null;
            while (rs.next()){
                emp = new Emp();
                emp.setEmpno(rs.getInt("empno"));
                emp.setEname(rs.getString("ename"));
                emp.setDeptno(rs.getInt("deptno"));
                emp.setSal(rs.getDouble("sal"));
                empList.add(emp);
            }
            return empList;
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, pstm, rs);
        }
        return null; //0表示失败了
    }
}
