package main

import (
	"context"
	"fmt"
	"redis-learn/utils/redisClient"

	"github.com/redis/go-redis/v9"
)

func main() {
	ctx := context.Background()
	client := redisClient.GetRedisClient()
	err := CreateUser(ctx, client, 1001)
	if err != nil {
		panic(err)
	}

	userFields, err := GetAllUserFields(ctx, client, 1001)
	if err != nil {
		panic(err)
	}
	fmt.Println(userFields)

	err = UpdateUserField(ctx, client, 1001, "city", "北京")
	if err != nil {
		panic(err)
	}

	userField, err := GetUserField(ctx, client, 1001, "city")
	if err != nil {
		panic(err)
	}
	fmt.Println(userField)
}

/* CreateUser 只是教学代码, 实际不要把redis当数据库用 */
func CreateUser(ctx context.Context, client *redis.Client, id int64) error {
	key := fmt.Sprintf("hash:user:%d", id)
	// interface表示可以是任意类型
	return client.HSet(ctx, key, map[string]interface{}{
		"name": "张三",
		"age":  18,
		"city": "上海",
	}).Err()
}

func UpdateUserField(ctx context.Context, client *redis.Client, id int64, field string, value interface{}) error {
	key := fmt.Sprintf("hash:user:%d", id)
	return client.HSet(ctx, key, field, value).Err()
}

// interface{} 表示可以是任意类型
func GetUserField(ctx context.Context, client *redis.Client, id int64, field string) (string, error) {
	key := fmt.Sprintf("hash:user:%d", id)
	return client.HGet(ctx, key, field).Result()
}

func GetAllUserFields(ctx context.Context, client *redis.Client, id int64) (map[string]string, error) {
	key := fmt.Sprintf("hash:user:%d", id)
	return client.HGetAll(ctx, key).Result()
}
