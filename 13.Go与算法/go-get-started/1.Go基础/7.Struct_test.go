package main

import (
	"fmt"
	"testing"
) //标准库

func TestStructMain(t *testing.T) {
	/*strcut初始化*/
	addr := AddressA{
		Province: "河南省",
		City:     "南阳市",
	}
	user1 := AUser{
		ID:       1,
		UserName: "AAA",
		AddressA: addr,
	}
	// 必须写完整的user1.AddressA.City
	fmt.Printf("用户名1:%s, 城市:%s\n", user1.UserName, user1.AddressA.City)

	user2 := AUser{
		ID:       2,
		UserName: "BBB",
		AddressB: AddressB{
			Province: "河南省",
			City:     "南阳市",
		},
	}
	//因为是匿名的, 所以可以直接user2.City
	fmt.Printf("用户名2:%s, 城市:%s\n", user2.UserName, user2.City)

	/*定义User指针*/
	user3 := &AUser{
		ID:       3,
		UserName: "CCC",
		AddressB: AddressB{
			Province: "河南省",
			City:     "南阳市",
		},
	}
	//访问对象指针的数据, 调用方法一样的
	fmt.Printf("用户名3:%s, 城市:%s\n", user3.UserName, user3.City)

	/*使用new()的方式创建一个指针*/
	user4 := new(AUser)
	user4.UserName = "DDD"
	fmt.Printf("用户名4:%s, 城市:%s\n", user4.UserName, user4.City)

}

type AUser struct {
	ID       int
	UserName string
	AddressA AddressA //嵌套另一个struct
	AddressB          //匿名嵌入AUser,这两种方式仅在访问值的时候有区别
}
type AddressA struct {
	Province string
	City     string
}
type AddressB struct {
	Province string
	City     string
}
