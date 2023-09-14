package TR0726_面向对象.T3_Project2.service;

import TR0726_面向对象.T3_Project2.entity.Customer;

/**
 * 业务类
 */

public class CustomerList {
    private Customer[] customers;
    private int total = 0;

    /**
     * 构造器
     *
     * @param total
     */
    public CustomerList(int total) {
        customers = new Customer[total];
        //customers[total++] = new Customer("Name", 'M', 18, "123", "123@123.com");
    }

    /**
     * 添加客户
     *
     * @param customer
     * @return
     */
    public boolean addCustomer(Customer customer) {
        //LengthCheck
        if (total >= customers.length) {
            System.out.println("Arrays too Long");
            return false;
        }
        customers[total++] = customer;
        return true;
    }

    /**
     * 修改客户
     *
     * @param index
     * @param cust
     * @return
     */
    public boolean replaceCustomer(int index, Customer cust) {
        /*
        1. 判断index范围
        2. 修改
         */
        if (index < 0 || index >= total) {
            return false;
        }
        customers[index] = cust;
        return true;
    }

    /**
     * 删除客户
     *
     * @param index
     * @return
     */
    public boolean deleteCustomer(int index) {
        /*
        1.判断索引范围
        2.从索引位置往后遍历，往前挪一位
        3.最后一位元素设为空
        4.total--
         */
        if (index < 0 || index > total) {
            return false;
        }
        for (int i = index; i < total - 1; i++) {
            customers[i] = customers[i + 1];
        }
        customers[total - 1] = null;
        total--;
        return true;
    }

    /**
     * 查找客户
     *
     * @param index
     * @return
     */
    public Customer getCustomer(int index) {
        if (index < 0 || index >= total) {
            return null;
        }
        return customers[index];
    }

    /**
     * 获取所有客户
     *
     * @return
     */
    public Customer[] getAllCustomers() {
        //为了封装性，创建一个新的临时数组，复制到新数组里面并返回
        Customer[] temp = new Customer[total];
        for (int i = 0; i < this.total; i++) {
            temp[i] = customers[i];
        }
        return customers;
    }

    /**
     * 获取客户数量
     *
     * @return
     */
    public int getTotal() {
        return this.total;
    }

//    public static void main(String[] args) {
//        CustomerList list = new CustomerList(10);
//        list.addCustomer(new Customer("Name", 'M', 18, "123", "123@123.com"));
//        Customer[] allCustomer = list.getAllCustomers();
//        System.out.println(Arrays.toString(allCustomer));
//        System.out.println(list.getTotal());
//    }

}
