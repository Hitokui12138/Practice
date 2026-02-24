package service

import (
	"context"

	"github.com/gogf/gf/v2/util/gconv"
	"github.com/redis/go-redis/v9"
)

// 纯数据模型：只存用户信息，无任何业务逻辑/外部依赖
type UserProfile struct {
	UserID   int64  `json:"user_id"`
	NickName string `json:"nick_name"`
	Avatar   string `json:"avatar"`
	Sex      string `json:"sex"`
}

// 算是Service层, 注入一个rdb用于Redis相关操作(依赖一个rdb)
type UserService struct {
	rdb *redis.Client
}

// 构造函数, 封装初始化逻辑
// 要求必须传入 Redis 客户端才能创建 UserService
func NewUserService(rdb *redis.Client) *UserService {
	return &UserService{rdb: rdb}
}

/* 用Hash更新用户信息 */
func (s *UserService) UpdateUserProfile(ctx context.Context, userID int64, profile *UserProfile) error {
	key := "user:profile:" + gconv.String(userID)
	// 直接对象转Map?
	valueMap := gconv.Map(profile)
	return s.rdb.HSet(ctx, key, valueMap).Err()
}

func (s *UserService) GetUserProfile(ctx context.Context, userID int64) (*UserProfile, error) {
	key := "user:profile:" + gconv.String(userID)
	result, err := s.rdb.HGetAll(ctx, key).Result()
	if err != nil {
		return nil, err
	}
	if len(result) == 0 {
		// TODO: 用户不存在时应该从数据库中查询, 涉及查询数据库时应该加一个分布式锁
		return &UserProfile{
			UserID:   userID,
			NickName: "db_nickname",
			Avatar:   "db_avatar",
			Sex:      "db_sex",
		}, nil
	}
	profile := &UserProfile{}           //创建空的结构体实例
	err = gconv.Struct(result, profile) //然后将Map转换为结构体
	if err != nil {
		return nil, err
	}
	return profile, nil
}

/* 粉丝数+1 */
func (s *UserService) IncrFollowers(ctx context.Context, userID int64) error {
	key := "user:followers:" + gconv.String(userID)
	return s.rdb.Incr(ctx, key).Err()
}
