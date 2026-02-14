package main

import (
	"log"

	"github.com/gin-gonic/gin"
	"github.com/peihanggu/chaster-api/internal/config"
	"github.com/peihanggu/chaster-api/internal/handler"
	"github.com/peihanggu/chaster-api/internal/service"
)

func main() {
	// 加载配置
	cfg := config.Load()

	// 初始化服务
	chasterService := service.NewChasterService(cfg)

	// 初始化处理器
	lockHandler := handler.NewLockHandler(chasterService)

	// 初始化Gin
	r := gin.Default()

	// 设置CORS
	r.Use(func(c *gin.Context) {
		c.Writer.Header().Set("Access-Control-Allow-Origin", "*")
		c.Writer.Header().Set("Access-Control-Allow-Credentials", "true")
		c.Writer.Header().Set("Access-Control-Allow-Headers", "Content-Type, Content-Length, Accept-Encoding, X-CSRF-Token, Authorization, accept, origin, Cache-Control, X-Requested-With")
		c.Writer.Header().Set("Access-Control-Allow-Methods", "POST, OPTIONS, GET, PUT, DELETE, PATCH")

		if c.Request.Method == "OPTIONS" {
			c.AbortWithStatus(204)
			return
		}

		c.Next()
	})

	// 设置路由
	api := r.Group("/api")
	{
		api.GET("/locks", lockHandler.GetLocks)
		api.POST("/keyholder/locks/search", lockHandler.GetKeyholderLocks)
		api.GET("/locks/:id", lockHandler.GetLockDetail)
		api.POST("/locks/:id/update-time", lockHandler.UpdateTime)
	}

	// 启动服务器
	log.Println("Server starting on :8080")
	if err := r.Run(":8080"); err != nil {
		log.Fatal("Failed to start server:", err)
	}
}
