package main

import (
	"fmt"
	"sort"
	"testing"
	"time"
) //标准库

func TestSortMain(t *testing.T) {
	pm := NewProductManager()

	fmt.Println("----自定义排序,按价格排序----")
	pm.SortByPrice(true)
	pm.PrintProduct()

	fmt.Println("----实现Sort接口排序,按年龄排序----")
	people := People{
		{"Alice", 25},
		{"Bob", 30},
		{"Charlie", 28},
	}
	sort.Sort(people)
	for _, person := range people {
		fmt.Printf("姓名: %s, 年龄: %d\n", person.Name, person.Age)
	}

}

/*
1. 自定义排序
*/
type Product struct {
	ID       int
	Name     string
	Price    float64
	Rating   float64
	Sales    int
	CreateAt time.Time
}

type ProductManager struct {
	products []Product
}

// 工厂函数(static) 无需与PM绑定
func NewProductManager() *ProductManager {
	return &ProductManager{
		products: []Product{
			{1, "智能手表", 599.99, 4.5, 8765, time.Now().Add(-24 * time.Hour)},
			{2, "无线蓝牙耳机", 179.80, 3.8, 1234, time.Now().Add(-48 * time.Hour)},
			{3, "电竞鼠标", 229.90, 4.9, 9876, time.Now().Add(-12 * time.Hour)},
			{4, "快充数据线", 29.99, 2.5, 4567, time.Now().Add(-72 * time.Hour)},
		},
	}
}

// 与PM绑定
func (pm *ProductManager) SortByPrice(ascending bool) {
	if ascending {
		sort.Slice(pm.products, func(i, j int) bool {
			return pm.products[i].Price < pm.products[j].Price
		})
	} else {
		sort.Slice(pm.products, func(i, j int) bool {
			return pm.products[i].Price > pm.products[j].Price
		})
	}
}

func (pm *ProductManager) PrintProduct() {
	for _, p := range pm.products {
		fmt.Printf("ID:%d, 名称:%s, 价格:%.2f元, 评分:%.1f, 销量:%d, 创建时间:%s\n",
			p.ID, p.Name, p.Price, p.Rating, p.Sales,
			p.CreateAt.Format("2006-01-02 15:04:05"))
	}
}

/*
2.实现sort接口的方式
*/
type Person struct {
	Name string
	Age  int
}

// ArrayList<Person>
type People []Person

// 实现Sort的三个方法, 注意大小写
func (p People) Len() int {
	return len(p)
}
func (p People) Less(i, j int) bool {
	return p[i].Age < p[j].Age
}
func (p People) Swap(i, j int) {
	// 切片的 “值拷贝” 拷贝的是 “切片结构体”，但切片结构体中的指针仍然指向底层数组
	// 当 People 不是切片，而是自定义的 “非切片集合类型”（比如链表、自定义结构体）时需要指针绑定
	p[i], p[j] = p[j], p[i]
}
