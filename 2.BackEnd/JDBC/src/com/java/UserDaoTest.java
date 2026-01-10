package com.java;

import com.dao.UserDao;
import com.entity.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName UserDaoTest.java
 * @Description TODO
 * @createTime 2023年08月22日 15:56:00
 */
public class UserDaoTest {
    UserDao userDao = new UserDao();

    /**
     * 添加用户测试
     */
    @Test
    public void test1() {
        User user = new User();
        user.setName("ABC");
        user.setPassword("abc");
        int rows = userDao.addUser(user);
        System.out.println(rows == 1 ? "添加成功" : "添加失败");
    }

    /**
     * 修改用户测试
     */
    @Test
    public void test2() {
        User user = new User();
        user.setName("ABC");
        user.setPassword("abc");
        user.setId(3);
        int rows = userDao.updateUser(user);
        System.out.println(rows == 1 ? "修改成功" : "修改失败");
    }

    /**
     * 删除用户测试
     */
    @Test
    public void test3() {
//        User user = new User();
//        user.setId(3);
        int rows = userDao.deleteUser(3);
        System.out.println(rows == 1 ? "删除成功" : "删除失败");
    }

    /**
     * 查询所有用户
     */
    @Test
    public void test4() {
        ArrayList<User> userList = userDao.findAllUser();
        userList.forEach(System.out::println);
    }

    /**
     * 调用MYSQL的结果集
     * SELECT就是一个结果集
     */
    @Test
    public void test5(){
        userDao.getEmpDept();
    }
    /**
     * 分页查询
     */
    @Test
    public void test6(){
        userDao.page(1,3);
        userDao.page(2,3);
        userDao.page(3,3);
    }

    /**
     * MYSQL 事务
     */
    @Test
    public void test7(){
        userDao.transfer(1, 2, 200);
    }

    /**
     * MYSQL 事务
     */
    @Test
    public void test8(){
        System.out.println(userDao.transfer_pro(2, 1, 100));
    }

}
