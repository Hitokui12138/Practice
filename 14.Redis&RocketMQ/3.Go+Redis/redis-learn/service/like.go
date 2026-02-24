package service

import (
	"context"
	"fmt"

	"github.com/redis/go-redis/v9"
)

type LikeService struct {
	rdb  *redis.Client
	rank *RankService
}

func NewLikeService(rdb *redis.Client, rank *RankService) *LikeService {
	return &LikeService{rdb: rdb, rank: rank}
}

/*
A. 点赞功能: 使用LUA确保以下三步操作的原子性(事务),且中间不要有别的命令插入
1. 检查用户是否点过赞
2. 添加用户ID到Set
3. 增加点赞数
*/
var likeLuaScript = redis.NewScript(`
	-- []KEYS和[]ARGV分别代表传入的键和参数
	local userKey = KEYS[1]
	local countKey = KEYS[2]
	local userID = ARGV[1]

	-- 判断用户是否已经点过赞
	if redis.call("SISMEMBER", userKey, userID) == 1 then
		return 0 -- 已经点过赞，返回0
	end
		
	-- 将用户ID添加到Set中
	redis.call("SADD", userKey, userID)
	-- 点赞数加1
	redis.call("INCR", countKey)
	return 1
`)

// Like 点赞方法
func (s *LikeService) Like(ctx context.Context, articleID, userID int64) (bool, error) {
	userKey := fmt.Sprintf("like:article:%d", articleID)
	countKey := fmt.Sprintf("article:%d:like_count", articleID)

	// 执行LUA脚本
	result, err := likeLuaScript.Run(ctx, s.rdb,
		[]string{userKey, countKey},
		userID).Int()
	if err != nil {
		return false, err
	}
	s.rank.OnArticleLiked(ctx, articleID)
	return result == 1, nil
}

/*
B. 取消点赞
1. 判断是否是`未点赞的`
2. 从Set中删除用户
3. 点赞数-1
*/
var unlikeLuaScript = redis.NewScript(`
	local userKey = KEYS[1]
	local countKey = KEYS[2]
	local userID = ARGV[1]

	-- 判断用户是否已经点过赞
	if redis.call("SISMEMBER", userKey, userID) == 0 then
		return 0 -- 没有点过赞，返回0
	end
		
	-- 从Set中删除用户ID
	redis.call("SREM", userKey, userID)
	-- 点赞数减1
	redis.call("DECR", countKey)
	return 1
`)

func (s *LikeService) Unlike(ctx context.Context, articleID, userID int64) (bool, error) {
	userKey := fmt.Sprintf("like:article:%d", articleID)
	countKey := fmt.Sprintf("article:%d:like_count", articleID)

	// 执行LUA脚本
	result, err := unlikeLuaScript.Run(ctx, s.rdb,
		[]string{userKey, countKey},
		userID).Int()
	if err != nil {
		return false, err
	}

	return result == 1, nil
}

/*
C. 其他一些功能
1. 是否被点赞
2. 获取点赞数
3. 获取最近点赞的用户列表(10个)
*/

func (s *LikeService) IsLiked(ctx context.Context, articleID, userID int64) (bool, error) {
	userKey := fmt.Sprintf("like:article:%d", articleID)
	return s.rdb.SIsMember(ctx, userKey, userID).Result()
}

func (s *LikeService) GetLikeCount(ctx context.Context, articleID int64) (int64, error) {
	countKey := fmt.Sprintf("article:%d:like_count", articleID)
	// String读取 O(1)
	return s.rdb.Get(ctx, countKey).Int64()
}

func (s *LikeService) GetRecentLikers(ctx context.Context, articleID int64, size int) ([]string, error) {
	userKey := fmt.Sprintf("like:article:%d", articleID)
	// SScan命令可以实现对Set的分页扫描，避免一次性获取大量数据导致性能问题
	// 不要使用SMEMBERS命令，因为它会一次性返回Set中的所有成员，可能导致性能问题
	vals, _, err := s.rdb.SScan(ctx, userKey, 0, "*", int64(size)).Result()
	return vals, err
}
