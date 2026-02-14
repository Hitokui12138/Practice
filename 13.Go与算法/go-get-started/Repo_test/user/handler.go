package user

import (
	"encoding/json"
	"io"
	"net/http"
	"strconv"
	"strings"
)

// UserHandler 这里实际上是Controller层
type UserHandler struct {
	store *UserStore
}

func NewUserHandler(store *UserStore) *UserHandler {
	return &UserHandler{store: store}
}

// 这里可以添加处理HTTP请求的方法, 比如CreateUser, GetUser等, 这些方法会调用store的方法来操作用户数据
// 如果用Gin的话这些代码就不用写了, 因为Gin已经帮我们处理了HTTP请求和响应, 只需要定义路由和处理函数就行了

// CreateUser 处理创建用户的逻辑, 实际上是调用store的AddUser方法
func (h *UserHandler) CreateUser(w http.ResponseWriter, r *http.Request) {
	// 只允许Post方法
	if r.Method != http.MethodPost {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}
	// 读取数据, io包实现了BufferedReader接口, 可以直接读取r.Body
	body, err := io.ReadAll(r.Body)
	if err != nil {
		http.Error(w, "Failed to read request body", http.StatusBadRequest)
		return
	}
	defer r.Body.Close() //关闭Body, 避免资源泄漏

	// 临时定义一个request结构体接收body
	type request struct {
		Name  string `json:"name"`
		Email string `json:"email"`
	}

	//接下来用JSON反序列化
	var req request
	if err := json.Unmarshal(body, &req); err != nil {
		http.Error(w, "Failed to parse user data", http.StatusBadRequest)
		return
	}

	req.Name = strings.TrimSpace(req.Name) //去掉空格
	req.Email = strings.TrimSpace(req.Email)

	if req.Name == "" || req.Email == "" {
		http.Error(w, "Name and email are required", http.StatusBadRequest)
		return
	}

	//如果参数都是对的, 那就返回一条创建成功
	user := h.store.AddUser(req.Name, req.Email) //调用store的AddUser方法创建用户
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(http.StatusCreated) //201 Created
	json.NewEncoder(w).Encode(user)   //合并了「序列化+写入」两步
}

// GetUser 单个获取
func (h *UserHandler) GetUser(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodGet {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}

	path := strings.TrimPrefix(r.URL.Path, "/users/") //从URL路径中提取用户ID
	id, err := strconv.Atoi(path)                     //把字符串转换成整数
	if err != nil {
		http.Error(w, "Invalid user ID", http.StatusBadRequest)
		return
	}
	user, ok := h.store.GetUser(id)
	if !ok {
		http.Error(w, "User not found", http.StatusNotFound)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(user)
}

func (h *UserHandler) GetUsers(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodGet {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}
	users := h.store.GetAllUsers()
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(users)
}
