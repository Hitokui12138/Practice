package com.dao;

import com.entity.Emp;
import com.entity.User;
import com.mysql.cj.jdbc.JdbcConnection;
import com.utils.JdbcOracleUtils;
import com.utils.JdbcUtils;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.oracore.OracleType;

import java.sql.*;
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
    private CallableStatement cst;
    private ResultSet rs;

    /**
     * 查询每个部门工资最高的两个员工
     */
    public ArrayList<Emp> findTop2Emp() {
        String sql = "SELECT empno, ename,deptno, sal FROM emp o " +
                "WHERE 1 >= (SELECT COUNT(1) FROM emp WHERE deptno = o.deptno AND sal < o.sal) ORDER BY deptno";
        ArrayList<Emp> empList = new ArrayList<>();
        try {
            conn = JdbcUtils.getConn();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            Emp emp = null;
            while (rs.next()) {
                emp = new Emp();
                emp.setEmpno(rs.getInt("empno"));
                emp.setEname(rs.getString("ename"));
                emp.setDeptno(rs.getInt("deptno"));
                emp.setSal(rs.getDouble("sal"));
                empList.add(emp);
            }
            return empList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, pstm, rs);
        }
        return null; //0表示失败了
    }


    /**
     * 分页
     */
    public ArrayList<Emp> LimitEmp(int limit, int offset) {
        String sql = "SELECT empno, ename,deptno, sal FROM emp LIMIT ?,?";
        ArrayList<Emp> empList = new ArrayList<>();
        try {
            conn = JdbcUtils.getConn();
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, limit);
            pstm.setInt(2, offset);

            rs = pstm.executeQuery();
            Emp emp = null;
            while (rs.next()) {
                emp = new Emp();
                emp.setEmpno(rs.getInt("empno"));
                emp.setEname(rs.getString("ename"));
                emp.setDeptno(rs.getInt("deptno"));
                emp.setSal(rs.getDouble("sal"));
                empList.add(emp);
            }
            return empList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, pstm, rs);
        }
        return null; //0表示失败了
    }

    /**
     * 分页2
     */
    public ArrayList<Emp> page(int curPage) {
        ArrayList<Emp> empList = new ArrayList<>();
        StringBuilder builder = new StringBuilder()
                .append("SELECT * FROM")
                .append("(SELECT a.*, rownum r FROM")
                .append("    (select EMPNO, ENAME, JOB, MGR, HIREDATE from EMP order by empno desc)a)")
                .append("WHERE r <= ? AND r >?");
        /**
         * 起始条数 = (当前页 - 1) * 每页显示数
         * 结束条数 = 当前页 * 每页显示数
         */
        try {
            conn = JdbcOracleUtils.getConn();
            pstm = conn.prepareStatement(builder.toString());
            pstm.setInt(1, curPage * 3);
            pstm.setInt(2, (curPage - 1) * 3);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Emp emp = new Emp();
                emp.setEmpno(rs.getInt("empno"));
                emp.setEname(rs.getString("ename"));
                emp.setJob(rs.getString("job"));
                emp.setMgr(rs.getInt("mgr"));
                emp.setHiredate(rs.getDate("hiredate")); // sql.date转util.date
                empList.add(emp);
            }
            return empList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcOracleUtils.close(conn, pstm, rs);
        }
        return null;
    }


    /**
     * 增
     */
    public int addEmp(Emp emp) {
        StringBuilder builder = new StringBuilder()
                .append("insert into EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)")
                .append("     values (seq_emp.nextval,?,?,?,SYSDATE,?,?,?)");
        try {
            conn = JdbcOracleUtils.getConn();
            pstm = conn.prepareStatement(builder.toString());
            pstm.setString(1, emp.getEname());
            pstm.setString(2, emp.getJob());
            pstm.setInt(3, emp.getMgr());
            pstm.setDouble(4, emp.getSal());
            pstm.setDouble(5, emp.getComm());
            pstm.setInt(6, emp.getDeptno());
            return pstm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcOracleUtils.close(conn, pstm, rs);
        }
        return 0;
    }

    /**
     * 删除
     */
    public int delEmp(Emp emp) {
        StringBuilder builder = new StringBuilder()
                .append("delete from EMP where empno = ?");
        try {
            conn = JdbcOracleUtils.getConn();
            pstm = conn.prepareStatement(builder.toString());
            pstm.setInt(1, emp.getEmpno());
            return pstm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcOracleUtils.close(conn, pstm, rs);
        }
        return 0;
    }

    /**
     * 更新
     */
    public int updEmp(Emp emp) {
        StringBuilder builder = new StringBuilder()
                .append(" update EMP set hiredate = ? where empno = ?");
        try {
            conn = JdbcOracleUtils.getConn();
            pstm = conn.prepareStatement(builder.toString());
            pstm.setDate(1, new java.sql.Date(emp.getHiredate().getTime())); //需要把utilDate->sqlDate
            pstm.setInt(2, emp.getEmpno());
            return pstm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JdbcOracleUtils.close(conn, pstm, rs);
        }
        return 0;
    }

    /**
     * 存储过程
     * in empno
     * inout sal
     */
    public void updateSalGetEnameAndSal(int empno, int sal) {
        String sql = "call updateSalGetEnameAndSal(?,?,?)";//只能调用PROCEDURE,PROCEDURE定义本身还是在ORACLEDB里面做
        try {
            conn = JdbcOracleUtils.getConn();
            cst = conn.prepareCall(sql); // CallableStatement

            cst.setInt(1, empno);   //IN,和之前一样直接设值
            cst.registerOutParameter(2, OracleTypes.VARCHAR);   //OUT,给?注册输出参数
            cst.setInt(3, sal); //INOUT,既需要设值,又需要注册
            cst.registerOutParameter(3, OracleTypes.NUMBER);    //INOUT,也需要注册

            cst.execute();//执行PROCEDURE

            String ename = cst.getString(2);    //获取OUT的返回值
            sal = cst.getInt(3);    //获取INOUT的返回值

            System.out.println("员工名: " + ename);
            System.out.println("涨薪后: " + sal);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcOracleUtils.close(conn, cst, rs);
        }
    }

    /**
     * 存储过程 getALLempByID
     * p_deptno in emp.deptno%type,
     * p_cur_emp OUT SYS_REFCURSOR,
     * p_offset IN NUMBER,
     * p_pageSize IN NUMBER
     */
    public ArrayList<Emp> getALLempByID(int deptno, int curPage) {
        int offset = (curPage - 1) * 3;
        int pageSize = curPage * 3;
        String sql = "{call getALLempByID(?,?,?,?)}"; //大括号还是写着吧
        ArrayList<Emp> empList = new ArrayList<>();

        try {
            conn = JdbcOracleUtils.getConn();
            cst = conn.prepareCall(sql);    //多态:子类当作父类来用
            cst.setInt(1, deptno);
            cst.registerOutParameter(2, OracleTypes.CURSOR);
            cst.setInt(3, offset);
            cst.setInt(4, pageSize);

            cst.execute();
            rs = (ResultSet)cst.getObject(2);//取得游标,强制转型

            while (rs.next()) {
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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcOracleUtils.close(conn, cst, rs);//注意外部资源一定要关掉
        }
        return empList;
    }

    /**
     * 调用函数
     */
    public int f_1(int empno){
        String sql = "{call ? := f_1(?)}";  //v_sal := t_1(7369),但v_sal没有定义,因此用?表示
        try {
            conn = JdbcOracleUtils.getConn();
            cst = conn.prepareCall(sql);
            cst.registerOutParameter(1, OracleTypes.NUMBER);
            cst.setInt(2, empno);
            cst.execute();
            return cst.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcOracleUtils.close(conn, cst);
        }
        return 0;
    }
}
