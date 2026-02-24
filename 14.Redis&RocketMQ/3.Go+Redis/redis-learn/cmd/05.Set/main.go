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

	// 添加标签
	AddTag(ctx, client, "tag:golang", 1001)
	AddTag(ctx, client, "tag:golang", 1002)
	AddTag(ctx, client, "tag:golang", 1003)
	AddTag(ctx, client, "tag:redis", 1001)
	AddTag(ctx, client, "tag:redis", 1004)

	// 获取标签下的帖子
	posts, _ := GetTagPosts(ctx, client, "tag:golang")
	fmt.Println("tag:golang 标签下的帖子:", posts)
	posts, _ = GetTagPosts(ctx, client, "tag:redis")
	fmt.Println("tag:redis 标签下的帖子:", posts)

	// 交集
	intersectPosts, _ := IntersectTags(ctx, client, []string{"tag:golang", "tag:redis"})
	fmt.Println("同时具有 tag:golang 和 tag:redis 标签的帖子:", intersectPosts)

	// 并集
	unionPosts, _ := UnionTags(ctx, client, []string{"tag:golang", "tag:redis"})
	fmt.Println("具有 tag:golang 或 tag:redis 标签的帖子:", unionPosts)

	// 推荐帖子
	recommendedPosts, _ := RecommendPosts(ctx, client, "tag:golang")
	fmt.Println("随机推荐 tag:golang 标签下的帖子:", recommendedPosts)

}

func fmtTagRedisKey(tagName string) string {
	return "tag:" + tagName
}

func AddTag(ctx context.Context, client *redis.Client, tagName string, postID int64) error {
	key := fmtTagRedisKey(tagName)
	return client.SAdd(ctx, key, postID).Err()
}

func GetTagPosts(ctx context.Context, client *redis.Client, tagName string) ([]string, error) {
	key := fmtTagRedisKey(tagName)
	return client.SMembers(ctx, key).Result()
}

/* 交并集 */

func IntersectTags(ctx context.Context, client *redis.Client, tagNames []string) ([]string, error) {
	var keys []string
	for _, tagName := range tagNames {
		keys = append(keys, fmtTagRedisKey(tagName))
	}
	// keys表示 k1,k2,...,kn, 是go的语法糖
	return client.SInter(ctx, keys...).Result()
}

func UnionTags(ctx context.Context, client *redis.Client, tagNames []string) ([]string, error) {
	var keys []string
	for _, tagName := range tagNames {
		keys = append(keys, fmtTagRedisKey(tagName))
	}
	return client.SUnion(ctx, keys...).Result()
}

// RecommendPosts 随机推荐
func RecommendPosts(ctx context.Context, client *redis.Client, tagName string) ([]string, error) {
	key := fmtTagRedisKey(tagName)
	return client.SRandMemberN(ctx, key, 1).Result()
}
