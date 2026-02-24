package service

import (
	"context"
	"fmt"

	"github.com/redis/go-redis/v9"
)

type FollowService struct {
	rdb *redis.Client
}

func NewFollowService(rdb *redis.Client) *FollowService {
	return &FollowService{rdb: rdb}
}

/*
1. 关注 LUA脚本实现
2. 取关 PipeLine实现
3. 共同关注 交集
4. 是否互关 PipeLine实现
5. 获取关注数和粉丝数
*/

/*
A.关注, LUA 脚本, 双向写
 1. 判断是否已经关注过了
 2. 双向写, 首先添加我的关注者, 然后添加目标用户的粉丝
*/
var flowLuaScript = redis.NewScript(`
	local myFollowing = KEYS[1]
	local targetFollower = KEYS[2]
	local myID = ARGV[1]
	local targetID = ARGV[2]

	-- 判断是否已经关注过了
	if redis.call("SISMEMBER", myFollowing, targetID) == 1 then
		return 0 -- 已经关注过了，返回0
	end
	
	-- 添加关注关系, 双向写入
	redis.call("SADD", myFollowing, targetID)
	redis.call("SADD", targetFollower, myID)
	return 1
`)

// 执行脚本
func (s *FollowService) Follow(ctx context.Context, myID, targetID int64) (bool, error) {
	myFollowing := fmt.Sprintf("follow:%d:following", myID)
	targetFollower := fmt.Sprintf("follow:%d:follower", targetID)

	result, err := flowLuaScript.Run(ctx, s.rdb,
		[]string{myFollowing, targetFollower},
		myID, targetID).Int()
	if err != nil {
		return false, err
	}
	return result == 1, nil
}

/*
B. 取关, PipeLine实现, 双向删除
1. SREM方法不会删掉SET中不存在的元素, 也不会报错,因此不需要判断
2. 直接在Pipeline中写, 删除我的关注列表中的目标用户ID, 然后删除目标用户的粉丝列表中的我的ID
*/
func (s *FollowService) Unfollow(ctx context.Context, myID, targetID int64) error {
	myFollowing := fmt.Sprintf("follow:%d:following", myID)
	targetFollower := fmt.Sprintf("follow:%d:follower", targetID)

	pipe := s.rdb.Pipeline()
	pipe.SRem(ctx, myFollowing, targetID)
	pipe.SRem(ctx, targetFollower, myID)
	_, err := pipe.Exec(ctx)
	return err
}

/*
C. 共同关注, 交集
1. 直接使用SINTER命令求交集, 传入两个用户的关注列表的key, 返回共同关注的用户ID列表
2. 不过要注意如果 关注人数很大 的的时候不要求交集
*/
func (s *FollowService) CommonFollowing(ctx context.Context, userID1, userID2 int64) ([]string, error) {
	keyA := fmt.Sprintf("follow:%d:following", userID1)
	keyB := fmt.Sprintf("follow:%d:following", userID2)

	return s.rdb.SInter(ctx, keyA, keyB).Result()
}

/*
D. 是否互关, PipeLine实现
1. 直接使用SISMEMBER命令判断是否互相关注, 传入两个用户的关注列表的key, 判断对方的ID是否在自己的关注列表中
2. 使用Pipeline批量执行两条命令, 分别判断A是否关注B和B是否关注A, 最后返回结果
*/
func (s *FollowService) IsMutualFollow(ctx context.Context, userID1, userID2 int64) (bool, error) {
	keyA := fmt.Sprintf("follow:%d:following", userID1)
	keyB := fmt.Sprintf("follow:%d:following", userID2)

	pipe := s.rdb.Pipeline()
	cmd1 := pipe.SIsMember(ctx, keyA, userID2)
	cmd2 := pipe.SIsMember(ctx, keyB, userID1)
	_, err := pipe.Exec(ctx)
	if err != nil {
		return false, err
	}
	return cmd1.Val() && cmd2.Val(), nil
}

/*
E. 获取关注数和粉丝数
1. 直接使用SCARD命令获取集合的元素数量, 传入用户的关注列表的key和粉丝列表的key, 分别获取关注数和粉丝数
2. 小集合用SCARD, 大集合用单独的string维护一个计数器
*/
func (s *FollowService) GetFollowCounts(ctx context.Context, userID int64) (followingCount, followerCount int64, err error) {
	followingKey := fmt.Sprintf("follow:%d:following", userID)
	followerKey := fmt.Sprintf("follow:%d:follower", userID)

	// 使用SCARD命令直接计算, 或者维护一个单独的string
	// 小集合用SCARD, 大集合用单独的string
	followingCount, err = s.rdb.SCard(ctx, followingKey).Result()
	if err != nil {
		return 0, 0, err
	}
	followerCount, err = s.rdb.SCard(ctx, followerKey).Result()
	if err != nil {
		return 0, 0, err
	}
	return followingCount, followerCount, nil
}
