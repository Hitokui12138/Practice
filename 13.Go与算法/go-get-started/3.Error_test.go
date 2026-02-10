package main

import (
	"fmt"
	"os"
	"testing"
	"time"
) //标准库

// golong的格式化必须使用固定的"2006-01-02 15:04:05"
const (
	TimeFmt = "2006-01-02 15:04:05"
)

func TestErrorMain(t *testing.T) {
	/*
		1. Error
	*/
	userName, err := queryDB(999)
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(userName)
	}

	userName, err = queryDB(-1)
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Println(userName)
	}

	/*
		2.Defer
	*/
	err = readFile("blank.txt")
	if err != nil {
		fmt.Println(err)
	}

	/*
		3.Panic,Recover
	*/
	result, err := safeAccess([]int{1, 2, 3}, 99)
	if err != nil {
		fmt.Println(err)
	}
	fmt.Println("数组结果: ", result)

	/*
		4.Defer和Func一起使用
	*/
	fmt.Println("Defer和Func一起使用的result: ", deferReturn())
}

/*
1. 定义一个Error
*/
type BusinessError struct {
	Code    int
	Message string
	Time    time.Time
}

// 格式化错误信息：拼接错误码、消息、格式化后的时间
// 这是实现error接口的关键 —— 没有这个方法，BusinessError就不能作为error类型返回；
// *BusinessError 指针接收者是 Go 社区的最佳实践
func (e *BusinessError) Error() string {
	return fmt.Sprintf("错误代码: %d, 消息: %s, 时间: %s", e.Code, e.Message, e.Time.Format(TimeFmt))
}

func queryDB(userID int) (string, error) {
	if userID == 999 {
		return "张三", nil
	} else {
		// 返回*BusinessError类型的指针，而非BusinessError值本身
		return "", &BusinessError{
			Code:    1001,
			Message: "用户不存在",
			Time:    time.Now(),
		}
	}
}

/*
2. Defer的作用
Go 的延迟执行语句，在当前函数执行完毕前（return/panic/ 函数正常结束），一定会执行
*/
func readFile(fileName string) error {
	file, err := os.Open(fileName)
	if err != nil {
		return fmt.Errorf("打开文件失败: %w", err)
	}
	// 用defer保证文件句柄一定会被关闭，避免资源泄漏。
	defer file.Close()
	// 读取文件中前 100 字节到字节切片buf中
	buf := make([]byte, 100)
	_, err = file.Read(buf)
	if err != nil {
		return fmt.Errorf("读取文件失败: %w", err)
	}
	return nil
}

/*
3.Panic/Recover, 相当于try catch
*/
//安全读取一个数组
func safeAccess(arr []int, index int) (result int, err error) {
	// 1. 定义defer匿名函数：Panic发生时才会生效
	defer func() {
		// 先新建一个r取得recover()的结果, 然后判断是否为空
		if r := recover(); r != nil {
			err = fmt.Errorf("发生Panic: %v", r)
		}
	}() //代码末尾的 () 是 Go 语言中匿名函数的 “立即调用” 语法
	return arr[index], nil
}

/*
4. Defer和返回值一起用
*/
func deferReturn() (result int) {
	defer func() {
		result++ //一定会在Return之前执行一次
	}()
	return 10
}
