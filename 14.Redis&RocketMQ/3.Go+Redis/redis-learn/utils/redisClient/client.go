package redisClient

import (
	"sync"

	"github.com/redis/go-redis/v9"
)

// 单例模式
// 确保整个程序运行期间，redisClient（Redis 客户端实例）只会被初始化一次
var (
	redisClient *redis.Client
	once        sync.Once //核心功能是保证传入的函数只会被执行一次，即使在多协程（并发）场景下也能保证
)

func GetRedisClient() *redis.Client {
	// 使用 sync.Once 来确保 Redis 客户端实例只会被初始化一次
	once.Do(func() {
		// 初始化 Redis 客户端实例
		redisClient = redis.NewClient(&redis.Options{
			Addr:         "localhost:6381", // Redis 服务器地址
			DB:           0,                // 使用默认数据库
			PoolSize:     10,
			PoolTimeout:  10,
			MinIdleConns: 1,
		})
	})
	return redisClient
}
