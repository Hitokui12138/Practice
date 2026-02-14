# Chaster Lock Manager

一个用于调用 Chaster API 的网页应用，可以查看和管理 locks 数据。

## 项目结构

```
TestClaude/
├── backend/           # Go 后端
│   ├── cmd/main.go   # 入口文件
│   ├── internal/
│   │   ├── config/config.go          # 配置管理
│   │   ├── handler/locks.go           # HTTP处理器
│   │   └── service/chaster.go         # Chaster API服务
│   ├── .env          # 配置文件 (Bearer Token)
│   └── go.mod
├── frontend/         # Vue 前端
│   ├── src/
│   │   ├── components/
│   │   │   ├── LockList.vue          # Locks列表组件
│   │   │   └── LockDetail.vue        # Lock详情组件
│   │   ├── api/chaster.js            # API调用
│   │   ├── App.vue
│   │   └── main.js
│   └── package.json
└── README.md
```

## 功能

- 查看所有 locks 列表
- 查看 lock 详情
- 更新 lock 时间（支持天数、小时数、分钟数）

## 快速开始

### 后端

```bash
cd backend

# 下载依赖（需要设置 GOSUMDB=off）
GOSUMDB=off go mod tidy

# 运行后端
go run cmd/main.go
```

后端将在 `http://localhost:8080` 启动。

### 前端

```bash
cd frontend

# 安装依赖
npm install

# 运行开发服务器
npm run dev
```

前端将在 `http://localhost:5173` 启动。

## API 端点

### 后端接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/locks` | 获取所有locks列表 |
| GET | `/api/locks/:id` | 获取指定lock详情 |
| POST | `/api/locks/:id/update-time` | 更新lock时间 |

### 请求示例

更新 lock 时间：
```bash
curl -X POST http://localhost:8080/api/locks/{lockId}/update-time \
  -H "Content-Type: application/json" \
  -d '{"days": 1, "hours": 2, "minutes": 30}'
```

## 配置

后端配置文件 `backend/.env`：

```env
CHASTER_API_URL=https://api.chaster.app
CHASTER_BEARER_TOKEN=your_token_here
```

## 技术栈

- **前端**: Vue 3 + Vite + Axios
- **后端**: Go + Gin
- **配置管理**: .env 文件
