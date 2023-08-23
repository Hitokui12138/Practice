package com.entity;

import java.util.Date;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName emp.java
 * @Description TODO
 * @createTime 2023年08月22日 16:38:00
 */
public class Emp {
    private int empno;
    private String ename;
    private String job;
    private int mgr;
    private Date hiredate;  //注意是util.Date
    private double sal;
    private double comm;
    private int deptno;




    public Emp() {
    }

    @Override
    public String toString() {
        return "Emp{" +
                "empno=" + empno +
                ", ename='" + ename + '\'' +
                ", job='" + job + '\'' +
                ", mgr=" + mgr +
                ", hiredate='" + hiredate + '\'' +
                ", sal=" + sal +
                ", comm=" + comm +
                ", deptno=" + deptno +
                '}';
    }

    public int getEmpno() {
        return empno;
    }

    public String getEname() {
        return ename;
    }

    public String getJob() {
        return job;
    }

    public int getMgr() {
        return mgr;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public double getSal() {
        return sal;
    }

    public double getComm() {
        return comm;
    }

    public int getDeptno() {
        return deptno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setMgr(int mgr) {
        this.mgr = mgr;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    public void setComm(double comm) {
        this.comm = comm;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }
}
