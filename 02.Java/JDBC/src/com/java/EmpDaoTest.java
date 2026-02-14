package com.java;

import com.dao.EmpDao;
import com.entity.Emp;
import com.entity.User;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName EmpDaoTest.java
 * @Description TODO
 * @createTime 2023年08月22日 16:46:00
 */
public class EmpDaoTest {
    EmpDao empDao = new EmpDao();

    /**
     * 查询每个部门工资最高的两个员工
     */
    @Test
    public void test1(){
        ArrayList<Emp> empList = empDao.findTop2Emp();
        empList.forEach(System.out::println);
    }

    /**
     * 分页
     */
    @Test
    public void test2(){
        ArrayList<Emp> empList = empDao.LimitEmp(0,5);
        empList.forEach(System.out::println);
    }
    /**
     * 分页2
     */
    @Test
    public void test3(){
        ArrayList<Emp> empList = empDao.page(1);
        empList.forEach(System.out::println);
    }


    /**
     *
     */
    @Test
    public void test4(){
        Emp emp = new Emp();
        emp.setEname("石原");
        emp.setJob("CLERK");
        emp.setMgr(7369);
        emp.setSal(1000);
        emp.setComm(0);
        emp.setDeptno(10);
        int rows = empDao.addEmp(emp);
    }
    //删
    @Test
    public void test5(){
        Emp emp = new Emp();
        emp.setEmpno(3);
        System.out.println(empDao.delEmp(emp));
    }

    //更新
    @Test
    public void test6() throws ParseException {
        Emp emp = new Emp();
        emp.setEmpno(4);
        String hiredate = "2023-08-23";
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        emp.setHiredate(df1.parse(hiredate)); //返回util.date
        System.out.println(empDao.updEmp(emp));
    }

    /**
     * Procedure的调用
     *
     */
    @Test
    public void test7() throws ParseException {
        ArrayList<Emp> empList = empDao.getALLempByID(30,1);
        empList.forEach(System.out::println);
    }
    /**
     * 函数
     * 根据ID取sal
     */
    @Test
    public void test8() throws ParseException {
        int i = empDao.f_1(7499);
        System.out.println(i);
    }

}
