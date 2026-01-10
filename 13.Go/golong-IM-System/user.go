package main

import "net"

type User struct {
	Name    string
	Address string
	C       chan string //当前与用户绑定的channel
	conn    net.Conn    //与当前用户通讯的连接
}

// 创建用户的API
func NewUser(conn net.Conn) *User {
	userAddr := conn.RemoteAddr().String()
	user := &User{
		Name:    userAddr,
		Address: userAddr,
		C:       make(chan string),
		conn:    conn,
	}

	//创建后, 启动监听当前UserChannel
	go user.ListenMessage()

	return user
}

// 监听当前User Channel, 一旦有消息, 就发送给对应的客户端
func (this *User) ListenMessage() {
	for {
		msg := <-this.C
		this.conn.Write([]byte(msg + "\n")) //把二进制消息转成byte数组
	}
}
