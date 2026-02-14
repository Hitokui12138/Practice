package main

import (
	"cmp"
	"fmt"
	"testing"
)

func TestGenericsMain(t *testing.T) {
	intSlice := []int{1, 2, 3, 4, 5}
	PrintSlice(intSlice)
	fmt.Printf("Index of 3: %d\n", FindIndex(intSlice, 3))

	stringSlice := []string{"a", "b", "c", "d", "e"}
	PrintSlice(stringSlice)
	fmt.Printf("Index of 'c': %d\n", FindIndex(stringSlice, "c"))

	fmt.Printf("Max of 10 and 20: %d\n", Max(10, 20))

	person1Slice := []Person1{
		{Name: "Alice"},
		{Name: "Bob"},
		{Name: "Charlie"},
	}
	PrintAll(person1Slice)
}

/* any表示没有任何约束 */
func PrintSlice[T any](s []T) {
	for _, v := range s {
		fmt.Println(v)
	}
	fmt.Println()
}

func FindIndex[T comparable](slice []T, target T) int {
	for i, v := range slice {
		if v == target {
			return i
		}
	}
	return -1
}

func Max[T cmp.Ordered](a, b T) T {
	if a > b {
		return a
	}
	return b
}

/* 定义一种约束 */
type stringer interface {
	string() string
}

/* 用这样的方式限制使用该方法的struct必须实现stringer(因为要调用string方法) */
func PrintAll[T stringer](s []T) {
	for _, v := range s {
		fmt.Println(v.string())
	}
}

type Person1 struct {
	Name string
}

/*实现Stringer*/
func (p Person1) string() string {
	return p.Name
}

/* 自定义赋值约束 要求必须是int或者float64 */
type Number64 interface {
	~int64 | ~float64
}

func sum64[T Number64](a, b T) T {
	return a + b
}

/* 写一个复杂的, 将切片中的元素转换为另一种类型 */
func Map[T, U any](slice []T, f func(T) U) []U {
	result := make([]U, len(slice))
	for i, v := range slice {
		result[i] = f(v)
	}
	return result
}

/* 过滤满足条件的数据 */
func Filter[T any](slice []T, f func(T) bool) []T {
	result := make([]T, 0)
	for _, v := range slice {
		if f(v) {
			result = append(result, v)
		}
	}
	return result
}

/* 聚合方法 Reduce*/
func Reduce[T, U any](slice []T, f func(U, T) U, initial U) U {
	result := initial
	for _, v := range slice {
		result = f(result, v)
	}
	return result
}
