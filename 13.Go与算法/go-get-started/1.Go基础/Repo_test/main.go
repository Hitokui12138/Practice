package main

import (
	"flag"
	"fmt"
	"log"
	"net/http"
	"repo-test/config"
	"repo-test/user"
)

/*
标准库测试Demo
做一个HTTP服务, 用于用户管理
添加用户, 查询用户, 获取用户列表
*/
func main() {
	// 通过命令行输入 port等配置信息
	var configFile string
	var port int

	// 如果有config参数, 那么使用参数; 如果没有, 则使用默认的config.json
	flag.StringVar(&configFile, "config", "config.json", "Path to the config file")
	flag.IntVar(&port, "port", 8080, "Port to run the server on")
	flag.Parse()

	config, err := config.LoadConfig(configFile)
	if err != nil {
		panic(err) //若配置文件错了, 只能退出了
	}

	fmt.Println("Starting server.")
	// 把user.NewUserStore() 注入 到 user.NewUserHandler() 里,
	// 这样就把Repository层的逻辑注入到了Controller层, 这样就实现了依赖注入, 也就是解耦了, 这样就可以更容易地测试和维护代码了
	handler := user.NewUserHandler(user.NewUserStore())

	/*
		实现HTTPServer, 处理用户相关的请求, 这里我们不使用第三方库, 直接用net/http包来实现
		w 用于写操作
		r 用于读操作
		注册陆游
	*/
	http.HandleFunc("/users", func(w http.ResponseWriter, r *http.Request) {
		switch r.Method {
		case http.MethodPost:
			handler.CreateUser(w, r) // 处理创建用户的逻辑, 实际上是调用store的AddUser方法
		case http.MethodGet:
			handler.GetUsers(w, r) // 处理获取用户列表的逻辑, 实际上是调用store的GetAllUsers方法
		default:
			http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		}
	})

	// 一般handler应该这样写, GetUser(w, r) 需要满足 HandlerFunc 要求的固定格式
	// 凡是访问 /users/ 开头的请求，都交给 handler.GetUser 来处理,
	// 这样就可以处理 /users/{id} 这样的请求了, 也就是获取单个用户的逻辑了, 实际上是调用store的GetUser方法
	http.HandleFunc("/users/", handler.GetUser)

	// 启动服务
	addr := fmt.Sprintf(":%d", config.Port)
	log.Printf("服务启动,端口: %+v\n", config.Port)
	// ListenAndServe函数会阻塞当前main(), 直到服务器关闭
	log.Fatal(http.ListenAndServe(addr, nil))

	fmt.Println("End server.")
}

/*
测试方法
1. 启动服务
2. 通过curl命令测试接口
2.1 curl -X POST -d '{"name": "Alice", "email": "123@qq.com"}' http://localhost:8000/users ,创建用户
2.2 curl http://localhost:8000/users ,获取用户列表
2.3 curl http://localhost:8000/users/1 ,获取单个用户
*/
