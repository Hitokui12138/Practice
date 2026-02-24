package main

import (
	"context"
	"fmt"
	"redis-learn/utils/redisClient"
	"time"

	"github.com/redis/go-redis/v9"
)

func main() {
	ctx := context.Background()
	client := redisClient.GetRedisClient()

	// 先设置一些用户数据
	for i := 0; i < 1000; i++ {
		key := fmt.Sprintf("user:%d", i)
		client.Set(ctx, key, fmt.Sprintf("user%d", i), 0)
	}

	//开始测试
	userIds := make([]int, 1000)
	for i := 0; i < 100; i++ {
		userIds[i] = i
	}

	for i := 0; i < 10; i++ {
		fmt.Println("开始压力测试")
		// 普通方式获取用户数据
		startTime := time.Now().UnixMilli()
		users, err := getUserNormal(ctx, client, userIds)
		if err != nil {
			panic(err)
		}
		fmt.Printf("普通方式获取用户数据: %d 个\n", len(users))
		fmt.Println("普通方式耗时: ", time.Now().UnixMilli()-startTime)

		time.Sleep(1 * time.Second) // 等待一段时间，模拟一些其他操作

		// MGET方式获取用户数据
		startTime = time.Now().UnixMilli()
		users, err = getUserMGet(ctx, client, userIds)
		if err != nil {
			panic(err)
		}
		fmt.Printf("MGET方式获取用户数据: %d 个\n", len(users))
		fmt.Println("MGET方式耗时: ", time.Now().UnixMilli()-startTime)

		time.Sleep(1 * time.Second) // 等待一段时间，模拟一些其他操作

		// Pipeline方式获取用户数据
		startTime = time.Now().UnixMilli()
		users, err = getUserPipeline(ctx, client, userIds)
		if err != nil {
			panic(err)
		}
		fmt.Printf("Pipeline方式获取用户数据: %d 个\n", len(users))
		fmt.Println("Pipeline方式耗时: ", time.Now().UnixMilli()-startTime)
	}

}

/* 一个一个取得 */
func getUserNormal(ctx context.Context, client *redis.Client, ids []int) ([]string, error) {
	var names []string
	for _, id := range ids {
		key := fmt.Sprintf("user:%d", id)
		name, err := client.Get(ctx, key).Result()
		if err != nil {
			return nil, err
		}
		names = append(names, name)
	}
	return names, nil
}

/* 试试MGET */
/* 一个一个取得, 当然也可以使用MGET */
func getUserMGet(ctx context.Context, client *redis.Client, ids []int) ([]string, error) {
	var (
		users []string
		keys  []string
	)
	for _, id := range ids {
		key := fmt.Sprintf("user:%d", id)
		keys = append(keys, key)
	}
	user, err := client.MGet(ctx, keys...).Result()
	if err != nil {
		return nil, err
	}
	for _, v := range user {
		if v != nil {
			users = append(users, v.(string))
		}
	}
	return users, nil
}

/*  使用Pipeline优化性能 */
func getUserPipeline(ctx context.Context, client *redis.Client, ids []int) ([]string, error) {
	pipeline := client.Pipeline()
	var (
		users []string
		cmds  []*redis.StringCmd
	)

	for _, id := range ids {
		key := fmt.Sprintf("user:%d", id)
		cmd := pipeline.Get(ctx, key)
		cmds = append(cmds, cmd)
	}

	// 这里只执行一次调用
	if _, err := pipeline.Exec(ctx); err != nil {
		return nil, err
	}
	for _, cmd := range cmds {
		user, err := cmd.Result()
		if err == nil {
			users = append(users, user)
		}
	}
	return users, nil
}
