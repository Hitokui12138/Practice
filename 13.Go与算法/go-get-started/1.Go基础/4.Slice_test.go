package main

import (
	"fmt"
	"testing"
) //标准库

func TestSliceMain(t *testing.T) {
	//定义一个购物车
	var cart []CartItem
	/*
		1. 增
	*/
	cart = append(cart, CartItem{
		Name:     "手机",
		Price:    5999.9,
		Quantity: 1,
	})
	cart = append(cart, CartItem{
		Name:     "电脑",
		Price:    3999.5,
		Quantity: 2,
	})

	cart = append(cart, CartItem{
		Name:     "鼠标",
		Price:    50.2,
		Quantity: 3,
	})
	total := 0.0
	for _, item := range cart {
		fmt.Printf("商品名称: %s, 价格: %.2f, 数量: %d\n", item.Name, item.Price, item.Quantity)
		total += item.Price * float64(item.Quantity)
	}
	fmt.Println("总价格: ", total)

	/*
		2. 删
		append(slice[:i], slice[i+1:]...)
	*/
	cart = append(cart[:1], cart[2:]...) //删除index为1的元素
	total = 0
	for _, item := range cart {
		fmt.Printf("----删除后----商品名称: %s, 价格: %.2f, 数量: %d\n", item.Name, item.Price, item.Quantity)
		total += item.Price * float64(item.Quantity)
	}
	fmt.Println("总价格: ", total)

}

type CartItem struct {
	Name     string
	Price    float64
	Quantity int
}
