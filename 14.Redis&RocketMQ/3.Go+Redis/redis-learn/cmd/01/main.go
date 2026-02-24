package main

import (
	"context"
	"redis-learn/utils/redisClient"
)

/*
尝试单例模式连接Redis
之后还有IO多路复用
*/
func main() {
	ctx := context.Background()
	// 获取 Redis 客户端实例
	client := redisClient.GetRedisClient()
	res, err := client.Ping(ctx).Result()
	if err != nil {
		panic(err)
	}
	println("Redis连接成功: ", res)
}
