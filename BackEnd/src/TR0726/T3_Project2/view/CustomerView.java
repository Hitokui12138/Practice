package TR0726.T3_Project2.view;

import TR0726.T3_Project2.entity.Customer;
import TR0726.T3_Project2.service.CustomerList;
import TR0726.T3_Project2.util.CMUtility;

public class CustomerView {
    CustomerList customerList = new CustomerList(10);
    public CustomerView(){
        customerList.addCustomer(new Customer("Name", 'M', 18, "123", "123@123.com"));
    }
    /**
     * 显示菜单
     */
    public void enterMainMenu() {
        boolean flag = true;
        while (flag) {
            System.out.println("客户端信息管理系统");
            System.out.println("1.添加客户");
            System.out.println("2.修改客户");
            System.out.println("3.删除客户");
            System.out.println("4.客户列表");
            System.out.println("5.退出");
            System.out.println("请选择（1-5）：");

            char option = CMUtility.readMenuSelection();
            switch (option) {
                case '1':
                    addNewCustomer();
                    break;
                case '2':
                    modifyCustomer();
                    break;
                case '3':
                    deleteCustomer();
                    break;
                case '4':
                    listCustomer();
                    break;
                case '5':
                    System.out.println("确认是否退出（Y/N）：");
                    char exit = CMUtility.readConfirmSelection();
                    if (exit == 'Y') {
                        flag = false;
                    }
                    break;
            }
        }
    }

    /**
     * 添加客户
     */
    public void addNewCustomer() {
        System.out.println("---- ADD ----");
        System.out.println("姓名：");
        String name = CMUtility.readString(10);
        Customer c = new Customer(name,'M',20,"123","456");
        boolean flag = customerList.addCustomer(c);
        if(flag){
            System.out.println("----OK----");
        }else {
            System.out.println("----NO----");
        }
    }

    /**
     * 修改客户
     */
    public void modifyCustomer() {
    }

    /**
     * 删除客户
     */
    public void deleteCustomer() {
    }

    /**
     * 显示客户
     */
    public void listCustomer() {
        System.out.println("----客户列表----");
        if (customerList.getTotal() == 0) {
            System.out.println("----没有客户记录----");
        } else {
            System.out.println("编号\t姓名\t性别\t年龄\t电话\t邮箱");
            for (int i = 0; i < customerList.getTotal(); i++) {
                Customer c = customerList.getCustomer(i);
                System.out.println(i + 1 + "\t" + c.getName() + "\t" + c.getGender() + "\t" + c.getAge() + "\t" + c.getPhone() + "\t" + c.getEmail());
            }
        }
        System.out.println("----END----");
    }

    public static void main(String[] args) {
        CustomerView view = new CustomerView();
        view.enterMainMenu();
    }
}
