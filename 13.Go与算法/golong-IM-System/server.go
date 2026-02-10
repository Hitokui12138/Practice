package main

import (
	"fmt"
	"net"
	"sync"
)

type Server struct {
	Ip   string
	Port int

	//map用于记录当前在线用户
	OnlineMap map[string]*User
	mapLock   sync.RWMutex //map是全局的,所以加一个读写锁

	//用于广播的Channel
	Message chan string
}

// 创建一个server的接口
func NewServer(ip string, port int) *Server {
	server := &Server{
		Ip:        ip,
		Port:      port, // 这个逗号不能删?
		OnlineMap: make(map[string]*User),
		Message:   make(chan string),
	}
	return server
}

// 监听广播channel, 一旦有消息, 就发送给全部在线User
func (this *Server) ListenMessage() {
	for {
		msg := <-this.Message

		//将msg发给全部在线User
		this.mapLock.Lock()
		for _, cli := range this.OnlineMap { //不关心Key,只关心Value
			cli.C <- msg
		}
		this.mapLock.Unlock()
	}
}

// 广播消息的方法
func (this *Server) BroadCast(user *User, msg string) {
	//发送者和接受者
	sendMsg := "[" + user.Address + "]" + user.Name + ":" + "已上线"
	this.Message <- sendMsg
}

func (this *Server) Handle(conn net.Conn) {
	//当前连接的业务
	fmt.Println("连接建立成功")
	user := NewUser(conn)

	//将用户加入Map
	this.mapLock.Lock()
	this.OnlineMap[user.Name] = user
	this.mapLock.Unlock()
	//广播该用户的消息
	this.BroadCast(user, "已上线")

	//为了防止整个struct死掉, 暂时阻塞handler
	select {}

}

// 启动服务器的接口
/**
做四件事
*/
func (this *Server) Start() {
	//1. socket listen
	/*
		点进函数看看
	*/
	//第一个是枚举,第二个是监听地址"127.0.0.1:8888"
	listener, err := net.Listen("tcp", fmt.Sprintf("%s:%d", this.Ip, this.Port))
	if err != nil {
		fmt.Println("net.Listen err: ", err)
		return
	}
	//4. close listen sockte
	defer listener.Close() // 为了防止后面忘了关闭

	//启动监听Msg的goroutine
	go this.ListenMessage()

	/*
		2和3的处理应该放在大循环里
	*/
	for {
		//2. accept
		conn, err := listener.Accept()
		if err != nil {
			fmt.Println("listener accept err: ", err)
			continue
		}
		//3. do handle
		go this.Handle(conn) //开一个线程异步处理
	}

}
