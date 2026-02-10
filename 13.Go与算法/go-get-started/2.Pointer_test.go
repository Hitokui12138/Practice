package main

import (
	"fmt"
	"testing"
) //标准库

func TestPointerMain(t *testing.T) {
	// 1. 定义普通变量v（储物盒，初始值0）
	var v int = 0
	fmt.Println("v的初始值：", v) // 输出：v的初始值：0
	fmt.Println("v的地址：", &v) // 输出：v的地址：0x140000a6200（不同环境地址不同）

	// 2. 声明指针变量p（空纸条，还没写地址）
	var p *int                    // 声明p是一个“指向int类型变量的指针”，初始值为nil（空地址）
	fmt.Println("p的初始值（空指针）：", p) // 输出：p的初始值（空指针）： <nil>

	// 3. 给指针p赋值：把v的地址写在纸条上
	p = &v                          // &v是取v的地址，赋值给p → p现在指向v的位置
	fmt.Println("p存储的地址（v的地址）：", p) // 输出：0x140000a6200（和v的地址一致）

	// 4. 解引用p：根据地址找到v，修改里面的值
	*p = 100                    // 等价于直接修改v = 100
	fmt.Println("修改后v的值：", v)   // 输出：修改后v的值：100
	fmt.Println("通过*p读取值：", *p) // 输出：通过*p读取值：100

	/*
		2. 指针在函数参数中的应用
	*/
	user := User{ID: 1, Name: "AAA", Balance: 100.0}
	updateBalanceValue(user, 50)
	fmt.Println("更新后的余额: ", user.Balance)
	updateBalancePointer(&user, 50) //这个地址会修改原数据
	fmt.Println("更新后的余额", user.Balance)

	/*
		3. 指针与数组
	*/
	users := []User{
		User{ID: 2, Name: "BBB", Balance: 200.0},
		User{ID: 3, Name: "CCC", Balance: 300.0},
	}
	usersP := []*User{
		&User{ID: 2, Name: "BBB", Balance: 200.0},
		&User{ID: 3, Name: "CCC", Balance: 300.0},
	}
	AddBalanceAndPrintForPtr(usersP)

	// 如何处理值切片? 通过索引操作原元素
	fmt.Println("值切片测试:")
	//1.这种方法不能修改原始值
	for _, user := range users {
		user.Balance += 50.0 //这个user只是一个值拷贝的副本
	}
	for _, user := range users {
		fmt.Println("更新后的余额", user.Balance)
	}
	//2.使用索引操作原元素
	fmt.Println("使用索引操作原元素:")
	for i := range users {
		users[i].Balance += 50.0 //修改原数据
	}
	for _, user := range users {
		fmt.Println("更新后的余额", user.Balance)
	}

}

type User struct {
	ID      int
	Name    string
	Balance float64
}

/*
2.指针在函数参数中的应用
*/
func updateBalancePointer(user *User, amount float64) {
	user.Balance += amount
	fmt.Println("updateBalancePointer: ", user.Balance)
}

func updateBalanceValue(user User, amount float64) {
	user.Balance += amount
	fmt.Println("updateBalanceValue: ", user.Balance)
}

/*
3. 指针在切片中的应用
*/

// 2. 处理指针切片
func AddBalanceAndPrintForPtr(users []*User) {
	// 1. 修改余额（指针副本指向原结构体，修改生效）
	for _, user := range users {
		user.Balance += 50.0
	}

	// 2. 打印结果
	fmt.Println("===== 指针切片-更新后的用户余额 =====")
	for _, user := range users {
		fmt.Printf("用户%s（ID:%d）：%.2f\n", user.Name, user.ID, user.Balance)
	}
}
