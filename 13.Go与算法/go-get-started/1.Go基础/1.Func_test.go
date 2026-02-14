package main

import (
	"fmt"
	"sort"
	"testing"
) //标准库

func TestFuncMain(t *testing.T) {
	//1. 计时器
	f1 := counter()
	fmt.Println("第一个计数器")
	for i := 0; i < 2; i++ {
		fmt.Println(f1())
	}
	fmt.Println("第二个计数器")
	f2 := counter()
	for range 3 {
		fmt.Println(f2())
	}

	//2. 匿名函数, 比较器
	strs := []string{"hello", "world", "golong"}
	sort.Slice(strs, func(i, j int) bool {
		return strs[i] < strs[j]
	})
	fmt.Println(strs)

	//3. 处理每个str
	procStrFunc := func(str string) string {
		//根据指定的格式字符串，将参数按格式拼接成一个新的字符串并返回
		return fmt.Sprintf("%s_AAA", str)
	}
	strs = ProcessString(strs, procStrFunc)
	fmt.Println(strs)
}

/*
 1. 闭包（Closure）计时器测试
    a. 调用counter()时，会初始化一个局部变量count = 0，并返回一个匿名函数；
    b.这个匿名函数会 “捕获” 外层的count变量，即使counter()函数执行完毕，count也不会被销毁；
    c. 每次调用返回的匿名函数，都会让count自增 1，并返回当前值。
    d. func() int, 表示返回结果是一个“返回值为int的方法”
*/
func counter() func() int {
	count := 0
	return func() int {
		count++ //每次调用返回的匿名函数，count 加一
		return count
	}
}

/*
 3. 函数作为参数处理
    a. 目标: 处理[]string中每个字符串, 但是具体处理逻辑自己定义
	b. 简单说：你可以自定义 “怎么处理单个字符串”（比如转大写、加前缀、去空格），然后把这个规则传给ProsessString，它会帮你把这个规则应用到整个字符串切片上。
*/
//定义一个func, 名字叫StringProcessor, 参数和返回值都是string
//先给 “接收 string、返回 string” 的函数类型起别名，方便后续使用；
type StringProcessor func(string) string

func ProcessString(strs []string, processor StringProcessor) []string {
	var result []string
	// 遍历原切片，_ 表示忽略索引（我们只需要字符串值）
	// range切片会返回两个值, 因此忽略第一个index
	// 增强for循环 foreach
	for _, str := range strs {
		// 原切片strs不会被修改
		result = append(result, processor(str))
	}
	return result
}
