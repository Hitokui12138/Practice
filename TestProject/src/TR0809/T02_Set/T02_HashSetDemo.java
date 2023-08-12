package TR0809.T02_Set;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName T02_HashSetDemo.java
 * @Description TODO
 * @createTime 2023年08月09日 12:48:00
 */
class User{
    private String name;
    private String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        return password != null ? password.equals(user.password) : user.password == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}

public class T02_HashSetDemo {
    /*
    用户注册
    要求用户名和密码不能相同
     */
    public static void main(String[] args) {
        System.out.println("用户注册系统");
        HashSet<User> users = new HashSet<>();
        Scanner input = new Scanner(System.in);
        while (true){
            System.out.println("请输入用户名");
            String userName = input.nextLine();
            System.out.println("请输入密码");
            String passWord = input.nextLine();
            boolean flag = users.add(new User(userName, passWord));
            if(flag){
                System.out.println("成功");
                System.out.println(users);
            }else {
                System.out.println("失败");
            }
        }
    }
}

