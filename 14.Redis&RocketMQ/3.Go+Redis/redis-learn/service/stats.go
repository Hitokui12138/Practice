package service

import (
	"context"
	"fmt"
	"time"

	"github.com/redis/go-redis/v9"
)

type StatsService struct {
	rdb *redis.Client
}

func NewStatsService(rdb *redis.Client) *StatsService {
	return &StatsService{rdb: rdb}
}

/*
A. 记录用户日活
1.用userID放进去, 然后用BitMap把这个位置占掉, 就表示当天已经活跃了
*/
func (s *StatsService) RecordDailyActiveUser(ctx context.Context, userID int64) error {
	dateStr := time.Now().Format("20060102")
	key := fmt.Sprintf("dau:%s", dateStr)
	return s.rdb.SetBit(ctx, key, userID, 1).Err()
}

/*
B. 获取某天的日活数量
1. 直接用BitCount命令统计当天的BitMap中有多少个1
*/
func (s *StatsService) GetDailyActiveUserCount(ctx context.Context, date time.Time) (int64, error) {
	key := fmt.Sprintf("dau:%s", date.Format("20060102"))
	return s.rdb.BitCount(ctx, key, nil).Result()
}

/*
C. 统计某文章的UV 唯一用户浏览量 HyperLogLog(比BitMap更节省空间, 但有一定误差, 适合大数据量的场景)
1. 用PFAdd命令把用户ID添加到HyperLogLog中, 这个数据结构会自动去重
*/
func (s *StatsService) RecordArticleView(ctx context.Context, articleID int64, userID int64) error {
	key := fmt.Sprintf("uv:article:%d", articleID)
	return s.rdb.PFAdd(ctx, key, userID).Err()
}

/*
D. 获取某文章的UV数量
2. 用PFCount命令获取某文章的UV数量
*/
func (s *StatsService) GetArticleUVCount(ctx context.Context, articleID int64) (int64, error) {
	key := fmt.Sprintf("uv:article:%d", articleID)
	return s.rdb.PFCount(ctx, key).Result()
}

/*
E. 合并UV数据
1. 像是B站可能有视频合集, 但是又不想遍历拿取各个视频的UV
2. 可以用PFMerge命令把多个HyperLogLog合并成一个新的HyperLogLog, 然后再统计这个新的HyperLogLog的UV数量
*/
func (s *StatsService) MergeArticleUV(ctx context.Context, destKey string, source ...string) error {
	return s.rdb.PFMerge(ctx, destKey, source...).Err()
}
