package main

import (
	"context"
	"redis-learn/utils/redisClient"
	"sync"

	"github.com/redis/go-redis/v9"
)

func main() {
	ctx := context.Background()
	client := redisClient.GetRedisClient()

	// 先手动设置库存
	// 启动多个协程一起扣除库存
	wg := sync.WaitGroup{}
	for i := 0; i < 10; i++ {
		wg.Add(1)
		go func(index int) {
			defer wg.Done()
			res, err := DecrStock(ctx, client)
			if err != nil {
				println("扣除库存失败:", err.Error(), "  协程:", index)
				return
			}
			if res == 1 {
				println("扣除库存结果:", res, "  协程:", index)
			}
			if res == 0 {
				println("库存不足，无法扣除库存  协程:", index)
			}
			if res == -1 {
				println("库存不存在  协程:", index)
			}
		}(i)
	}
	wg.Wait()
}

const (
	ProductStockKey = "product:stock:1000"
)

// Lua脚本 用于原子性地减少库存
var decrStockScript = redis.NewScript(`
	local key = KEYS[1]
	local stock = tonumber(redis.call("get", key))
	if not stock then
		return -1
	end
	if stock <= 0 then
		return 0
	end
	redis.call("decr", key)
	return 1
`)

// DecrStock 使用Lua脚本原子性地减少库存
func DecrStock(ctx context.Context, client *redis.Client) (int64, error) {
	//执行 Redis Lua 脚本的典型写法
	result, err := decrStockScript.Run(ctx, client, []string{ProductStockKey}).Result()
	if err != nil {
		return 0, err
	}
	return result.(int64), nil
}
