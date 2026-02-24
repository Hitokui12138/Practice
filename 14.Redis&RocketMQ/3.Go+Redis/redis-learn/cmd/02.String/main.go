package main

import (
	"context"
	"fmt"
	"redis-learn/utils/redisClient"
	"time"

	"github.com/redis/go-redis/v9"
)

func main() {
	//返回一个「空的、不可取消的、无截止时间的」基础上下文实例。
	//用于在 goroutine（协程）之间传递「控制信号」和「请求范围内的值」。
	ctx := context.Background()
	client := redisClient.GetRedisClient()

	//模拟读取
	for i := 0; i < 10; i++ {
		err := Increament(ctx, client, "1001")
		if err != nil {
			panic(err)
		}
	}

	viewCount, err := GetViewCount(ctx, client, "1001")
	if err != nil {
		panic(err)
	}
	fmt.Printf("24小时内 文章 1001 的阅读量为: %d\n", viewCount)
}

// Increament 1. 对阅读进行增加
func Increament(ctx context.Context, client *redis.Client, id string) error {
	key := fmt.Sprintf("article:view:%s", id)
	val, err := client.Incr(ctx, key).Result() // 自增
	if err != nil {
		return err
	}
	if val == 1 {
		// 设置过期时间
		err = client.Expire(ctx, key, time.Hour*24).Err()
		if err != nil {
			return err
		}
	}
	return nil
}

// GetViewCount 2. 读取数量
func GetViewCount(ctx context.Context, client *redis.Client, id string) (int64, error) {
	key := fmt.Sprintf("article:view:%s", id)
	return client.Get(ctx, key).Int64()
}
