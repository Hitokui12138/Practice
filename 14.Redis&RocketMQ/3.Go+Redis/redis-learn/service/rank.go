package service

import (
	"context"
	"fmt"
	"time"

	"github.com/redis/go-redis/v9"
)

type RankService struct {
	rdb *redis.Client
}

func NewRankService(rdb *redis.Client) *RankService {
	return &RankService{rdb: rdb}
}

/*
A. 每天更新文章的热度
 1. 获取当日日期
 2. 原子增加分数ZIncrBy
*/
func (s *RankService) UpdateScore(ctx context.Context, articleID int64, score float64) error {
	// 每日一个榜
	dateStr := time.Now().Format("20060102")
	key := fmt.Sprintf("rank:article:%s", dateStr)

	// 原子增加分数
	return s.rdb.ZIncrBy(ctx, key, score, fmt.Sprintf("%d", articleID)).Err()
}

/*
B. 获取TopN, N不要太多，过多会影响性能
*/
func (s *RankService) GetTopN(ctx context.Context, n int64) ([]redis.Z, error) {
	dateStr := time.Now().Format("20060102")
	key := fmt.Sprintf("rank:article:%s", dateStr)
	//ZRevRange 表示从高到低获取分数，0表示起始位置，n-1表示结束位置
	return s.rdb.ZRevRangeWithScores(ctx, key, 0, n-1).Result()
}

/*
C. 获取文章排名
*/
func (s *RankService) GetArticleRank(ctx context.Context, articleID int64) (int64, error) {
	dateStr := time.Now().Format("20060102")
	key := fmt.Sprintf("rank:article:%s", dateStr)

	//ZRevRank 表示从高到低获取排名，返回值是从0开始的索引
	rank, err := s.rdb.ZRevRank(ctx, key, fmt.Sprintf("%d", articleID)).Result()
	if err == redis.Nil {
		return -1, nil // 文章未上榜
	}
	return rank + 1, err // 转换为从1开始的排名
}

/*
D. 点赞时触发热度更新
*/
func (s *RankService) OnArticleLiked(ctx context.Context, articleID int64) {
	// 每次点赞增加10分
	s.UpdateScore(ctx, articleID, 1)
}

/*
E. 设置过期时间 7天
1. 这个功能应该使用定时器操作
*/
func (s *RankService) SetExpire(ctx context.Context) {
	dateStr := time.Now().AddDate(0, 0, -7).Format("20060102")
	key := fmt.Sprintf("rank:article:%s", dateStr)
	s.rdb.Expire(ctx, key, 0) //0表示立即过期
}
