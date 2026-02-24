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

	AddScore(ctx, client, "player1", 90.0)
	AddScore(ctx, client, "player2", 100.0)
	AddScore(ctx, client, "player3", 70.0)
	AddScore(ctx, client, "player4", 110.0)
	AddScore(ctx, client, "player5", 60.0)

	topPlayers, err := GetTopN(ctx, client, 3)
	if err != nil {
		panic(err)
	}
	fmt.Println("TopPlayers: ", topPlayers)

	for _, player := range topPlayers {
		rank, err := GetRank(ctx, client, player.Member.(string))
		if err != nil {
			panic(err)
		}
		score, err := GetScore(ctx, client, player.Member.(string))
		if err != nil {
			panic(err)
		}
		fmt.Println("Rank:", rank+1, "Player:", player.Member.(string), "Score:", score)
	}
}

func fmtRankRedisKey() string {
	return "rank:name"
}

// AddScore 更新积分
func AddScore(ctx context.Context, client *redis.Client, playerID string, score float64) error {
	key := fmtRankRedisKey()
	return client.ZIncrBy(ctx, key, score, playerID).Err()
}

/*
GetTopN 获取排行榜前N名
返回的 []redis.Z 表示 分数+元素 这样一个结构
*/
func GetTopN(ctx context.Context, client *redis.Client, n int) ([]redis.Z, error) {
	key := fmtRankRedisKey()
	return client.ZRevRangeWithScores(ctx, key, 0, int64(n-1)).Result()
}

func GetRank(ctx context.Context, client *redis.Client, playerID string) (int64, error) {
	key := fmtRankRedisKey()
	return client.ZRevRank(ctx, key, playerID).Result()
}

func GetScore(ctx context.Context, client *redis.Client, playerID string) (float64, error) {
	key := fmtRankRedisKey()
	return client.ZScore(ctx, key, playerID).Result()
}
