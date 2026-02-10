package main

import (
	"fmt"
	"testing"
) //标准库

func TestMethodMain(t *testing.T) {
	account := BankAccount{
		AccountNumber: "123456",
		Balance:       342.23,
		IsActive:      true,
	}

	//值接受者方法
	fmt.Println(account.GetAccountInfo())
	//指针接受者
	err := account.Deposit(-500.0)
	if err != nil {
		fmt.Println("操作失败:", err)
	}
	fmt.Printf("账号余额: %f", account.Balance)
}

/*定义结构体的方法*/
type BankAccount struct {
	AccountNumber string
	Balance       float64
	IsActive      bool
}

// 定义一个接受者类型为该结构体的方法,
// 值接受, 不改变原来的值, 表示这个方法只Get
func (acc BankAccount) GetAccountInfo() string {
	status := "活跃"
	if !acc.IsActive {
		status = "冻结"
	}
	return fmt.Sprintf("账号:%s, 余额:%f, 状态:%s", acc.AccountNumber, acc.Balance, status)
}

// 存钱转账方法, 因为要修改对象的信息, 因此这里使用指针接受者
func (acc *BankAccount) Deposit(amount float64) error {
	if !acc.IsActive {
		return fmt.Errorf("账户已冻结")
	}
	if acc.Balance+amount < 0 {
		return fmt.Errorf("你没那么多钱")
	}
	acc.Balance += amount
	return nil
}
