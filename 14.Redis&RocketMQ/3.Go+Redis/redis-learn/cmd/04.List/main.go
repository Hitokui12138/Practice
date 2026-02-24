package main

import (
	"context"
	"fmt"
	"redis-learn/utils/redisClient"
	"time"

	"github.com/redis/go-redis/v9"
)

const (
	QueueName = "queue:task"
)

func main() {
	ctx := context.Background()
	client := redisClient.GetRedisClient()

	//定义一个携程一直生成job
	go func() {
		for {
			time.Sleep(time.Second * 1) // 每秒生成一个任务
			err := PushTask(ctx, client, fmt.Sprintf("task-%d", time.Now().Unix()))
			if err != nil {
				fmt.Printf("Error pushing task: %v\n", err)
			} else {
				fmt.Printf("生成task: %s\n", fmt.Sprintf("task-%d", time.Now().Unix()))
			}
		}
	}()
	// 启动一个工作池，处理任务
	StartWorkerPool(ctx, client, 3)
	//阻塞主线程, 让工作池一直运行
	select {} //for{}会持续占用 CPU 核心 select{} 不会占用 CPU 核心, 适合阻塞主线程
}

func PushTask(ctx context.Context, client *redis.Client, task string) error {
	return client.LPush(ctx, QueueName, task).Err()
}

func ConsumeTask(ctx context.Context, client *redis.Client) error {
	for { // 基础无限循环（需主动退出）,等价于 for true {}
		// 我们已经知道阻塞的方法了, 这里就不要轮询了
		task, err := client.BRPop(ctx, 0, QueueName).Result()
		if err != nil {
			if err == context.Canceled {
				return err
			}
			fmt.Printf("Error consuming task: %v\n", err)
			continue // 这里我们不处理错误, 继续等待任务
		}
		fmt.Printf("消费task: %s, 队列: %s\n", task[1], task[0])
		// 模拟处理时间
		time.Sleep(500 * time.Millisecond)
	}
}
func StartWorkerPool(ctx context.Context, client *redis.Client, size int) {
	for i := 0; i < size; i++ {
		// 启动一个新的 goroutine，让函数异步执行，实现并发
		go ConsumeTask(ctx, client)
	}
	//然后主线程可以做别的事
}
