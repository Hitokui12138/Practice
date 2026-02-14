package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

/*
1. 一般调试方法
 1. 启动 go run main.go
 2. 在另一个终端看一下 curl http://localhost:8080/welcome?first_name=John&last_name=Doe

2. 使用Delve调试
 1. go get -u github.com/go-delve/delve/cmd/dlv
 2. dlv debug main.go
 3. 在Delve命令行界面设置断点，例如：break main.go:10, 也可以指定方法 b HelloHandler
 4. 然后执行 continue 或者 c, 来让main()运行
 5. 在另一个窗口执行curl, 可以看到之前的窗口在端点处停下了
 6. next/n 命令可以逐行运行; print/p firstName 可以查看变量值; list/l 可以查看当前代码上下文
 7. 可以使用 step/s 命令进入函数调用，或者使用 stepout/finish 命令执行完当前函数并返回调用处
 8. 觉得没问题的话就可以使用 continue/c 继续运行程序
*/

func main() {
	router := gin.Default()
	router.GET("/welcome", HelloHandler)

	router.Run(":8080")
}

func HelloHandler(c *gin.Context) {
	firstName := c.DefaultQuery("first_name", "Guest")
	lastName := c.Query("last_name") // 这是一个快捷方法，默认值为空字符串
	c.String(http.StatusOK, "Hello %s %s", firstName, lastName)
}
