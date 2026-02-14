package main

import (
	"fmt"
	"sync"
	"testing"
	"time"
) //标准库

func TestMapMain(t *testing.T) {
	sessionManager := NewSessionManager()
	sessionManager.AddSession("token1", "user1", "192.168.1.100")

	//验证用户是否登录
	if session, ok := sessionManager.GetSession("token1"); ok {
		fmt.Printf("找到用户: %s, 登录时间: %v\n", session.UserID, session.LoginTime.Format(time.DateTime))
	}

	sessionManager.RemoveSession("token1")
	if session, ok := sessionManager.GetSession("token1"); ok {
		fmt.Printf("找到用户: %s, 登录时间: %v\n", session.UserID, session.LoginTime.Format(time.DateTime))
	}

}

/**/
type UserSession struct {
	UserID       string
	LoginTime    time.Time
	LastActivity time.Time
	IPAddress    string
}

/*
用这个map来管理UserSession
*/
type SessionManager struct {
	//V是指向该用户会话实例的指针,存储指针只需 8 字节
	sessions map[string]*UserSession
	//线程不安全, 因此加一个读写锁
	mutex sync.RWMutex
}

/*
构造方法(工厂函数),这是社区约定的替代方案
*/
func NewSessionManager() *SessionManager {
	return &SessionManager{ //返回一个SessionManager的指针
		sessions: make(map[string]*UserSession),
	}
}

/*
用户登录时, token为key, session信息为value
(s *SessionManager) 表示这个方法是绑定给SessionManager的, 相当于this/self
*/
func (s *SessionManager) AddSession(token string, userID string, ipAddress string) {
	//写 锁
	s.mutex.Lock()
	defer s.mutex.Unlock()

	session := &UserSession{
		UserID:       userID,
		LoginTime:    time.Now(),
		LastActivity: time.Now(),
		IPAddress:    ipAddress,
	}

	s.sessions[token] = session
}

/*
获取Session
*/
func (s *SessionManager) GetSession(token string) (*UserSession, bool) {
	//读 锁,
	s.mutex.RLock()
	defer s.mutex.RUnlock()

	session, ok := s.sessions[token]
	if ok {
		session.LastActivity = time.Now()
	}
	return session, ok
}

/*
移除Session
*/
func (s *SessionManager) RemoveSession(token string) {
	s.mutex.Lock()
	defer s.mutex.Unlock()
	delete(s.sessions, token)
}
