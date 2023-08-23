package com.dao;

import com.entity.Emp;
import com.entity.User;
import com.mysql.cj.jdbc.JdbcConnection;
import com.utils.JdbcOracleUtils;
import com.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 分页2
     */
    public ArrayList<Emp> page(int curPage){
        ArrayList<Emp> empList = new ArrayList<>();
        StringBuilder builder = new StringBuilder()
                .append("SELECT * FROM")
                .append("(SELECT a.*, rownum r FROM")
                .append("    (select EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO from EMP order by empno desc)a)")
                .append("WHERE r <= ? AND r >?");
        /**
         * 起始条数 = (当前页 - 1) * 每页显示数
         * 结束条数 = 当前页 * 每页显示数
         */
        try{
            conn = JdbcOracleUtils.getConn();
            pstm = conn.prepareStatement(builder.toString());
            pstm.setInt(1, curPage * 3);
            pstm.setInt(2, (curPage-1)*3);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()){
                Emp emp = new Emp();
                emp.setEmpno(rs.getInt("empno"));
                emp.setEname(rs.getString("ename"));
                emp.setDeptno(rs.getInt("deptno"));
                emp.setSal(rs.getDouble("sal"));
                emp.setHiredate(rs.getDate("hiredate")); // sql.date转util.date
                emp.setJob(rs.getString("job"));
                emp.setMgr(rs.getInt("mgr"));
                empList.add(emp);
            }
            return empList;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcOracleUtils.close(conn, pstm, rs);
        }
        return null;
    }


    /**
     *  增
     */
    public int addEmp(Emp emp){
        StringBuilder builder = new StringBuilder()
                .append("insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)")
                .append("     values (seq_emp.nextval,?,?,?,SYSDATE,?,?,?)");
        try{
            conn = JdbcOracleUtils.getConn();
            pstm = conn.prepareStatement(builder.toString());
            pstm.setString(1,emp.getEname());
            pstm.setString(2,emp.getJob());
            pstm.setInt(3,emp.getMgr());
            pstm.setDouble(4,emp.getSal());
            pstm.setDouble(5,emp.getComm());
            pstm.setInt(6,emp.getDeptno());
            return pstm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcOracleUtils.close(conn, pstm, rs);
        }
        return 0;
    }

    /**
     * 删除
     */
    public int delEmp(Emp emp){
        StringBuilder builder = new StringBuilder()
                .append("delete from EMP where empno = ?");
        try{
            conn = JdbcOracleUtils.getConn();
            pstm = conn.prepareStatement(builder.toString());
            pstm.setInt(1,emp.getEmpno());
            return pstm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcOracleUtils.close(conn, pstm, rs);
        }
        return 0;
    }

    /**
     * 更新
     */
    public int updEmp(Emp emp){
        StringBuilder builder = new StringBuilder()
                .append(" update EMP set ename = ? where empno = ?");
        try{
            conn = JdbcOracleUtils.getConn();
            pstm = conn.prepareStatement(builder.toString());
            pstm.setInt(1,emp.getEmpno());
            return pstm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcOracleUtils.close(conn, pstm, rs);
        }
        return 0;
    }
}
