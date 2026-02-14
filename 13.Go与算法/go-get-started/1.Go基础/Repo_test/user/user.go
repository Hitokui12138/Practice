// Package user 提供用户信息的存储、查询等核心逻辑
package user

import (
	"sync"
	"time"
)

// User 这里实际上是Repository层, 定义了用户的数据结构和存储逻辑
type User struct {
	ID       int       `json:"id"` //JSON Tag, 用于解析JSON
	Name     string    `json:"name"`
	Email    string    `json:"email"`
	CreateAt time.Time `json:"create_at"`
	Active   bool      `json:"active"`
}

type UserStore struct {
	users  map[int]User //用id可以查找用户users
	mutex  sync.RWMutex //用一个读写锁, 因为可能同时有多个用户访问, 分布式还是有问题
	nextID int          //下一个用户的ID 模拟自增
}

func NewUserStore() *UserStore {
	return &UserStore{
		users:  make(map[int]User),
		nextID: 1, //从1开始自增
	}
}

// AddUser 当用户登录时, 就调用方法, 把用户信息存到UserStore里
func (s *UserStore) AddUser(name, email string) User {
	s.mutex.Lock()         //加锁, 因为要修改数据
	defer s.mutex.Unlock() //解锁, 无论如何都会执行
	user := User{
		ID:       s.nextID,
		Name:     name,
		Email:    email,
		CreateAt: time.Now(),
		Active:   true,
	}
	s.users[s.nextID] = user //把用户存到map里
	s.nextID++               //自增ID
	return user
}

func (s *UserStore) GetUser(id int) (User, bool) {
	s.mutex.RLock()         //读锁, 因为只是读取数据
	defer s.mutex.RUnlock() //解锁
	user, ok := s.users[id] //查询是否存在的语法
	return user, ok
}

func (s *UserStore) GetAllUsers() []User {
	s.mutex.RLock()
	defer s.mutex.RUnlock()
	//创建slice, 长度为0, 容量为用户数量, 避免多次扩容, 把map的用户一个个存进去
	users := make([]User, 0, len(s.users))
	for _, user := range s.users {
		users = append(users, user)
	}
	return users
}
