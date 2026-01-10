package main

import (
	"encoding/json"
	"fmt"
	"net/http"
	"time"

	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

type Fruit struct {
	Name  string `json:"name"`
	Price string `json:"price"`
	Image string `json:"image"`
}

func main() {
	// 数据库连接配置
	dsn := "root:123456@tcp(mm-supermarket-db:3306)/fruits?charset=utf8mb4&parseTime=True&loc=Local"

	// 最大重试次数
	maxRetries := 10
	retries := 0
	var db *gorm.DB
	var err error
	// 循环尝试连接数据库
	for retries < maxRetries {
		// 使用 GORM 打开数据库连接
		db, err = gorm.Open(mysql.Open(dsn), &gorm.Config{})
		if err == nil {
			break // 成功连接，退出循环
		}
		time.Sleep(5 * time.Second) // 等待5秒后重试
		retries++
	}
	if err != nil {
		panic("failed to connect database")
	}
	// 使用AutoMigrate方法创建表
	db.AutoMigrate(&Fruit{})
	//写入默认数据
	entities := []Fruit{
		{
			Name:  "苹果",
			Price: "2 元/个",
			Image: "/images/apple.jpeg",
		},
		{
			Name:  "香蕉",
			Price: "1 元/根",
			Image: "/images/banana.jpeg",
		},
		{
			Name:  "橙子",
			Price: "3 元/个",
			Image: "/images/orange.jpeg",
		},
	}
	for _, entity := range entities {
		var existingFruit Fruit
		if err := db.Where("name = ?", entity.Name).First(&existingFruit).Error; err != nil {
			if err == gorm.ErrRecordNotFound {
				// 记录不存在，创建新记录
				if err := db.Create(&entity).Error; err != nil {
					panic(err)
				}
			} else {
				panic(err)
			}
		}
	}
	http.HandleFunc("/fruits", func(w http.ResponseWriter, r *http.Request) {
		// 查询数据库中的水果数据
		var fruits []Fruit
		err := db.Find(&fruits).Error
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}
		// 将水果数据以 JSON 格式返回给客户端
		w.Header().Set("Content-Type", "application/json")
		if err := json.NewEncoder(w).Encode(fruits); err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}
	})
	fmt.Printf("star server")
	// 启动 HTTP 服务器，监听端口 8080
	if err := http.ListenAndServe(":8080", nil); err != nil {
		panic(err.Error())
	}
}
