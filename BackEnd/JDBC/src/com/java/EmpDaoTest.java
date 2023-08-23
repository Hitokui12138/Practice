package com.java;

import com.dao.EmpDao;
import com.entity.Emp;
import com.entity.User;
import org.junit.jupiter.api.Test;

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
}
