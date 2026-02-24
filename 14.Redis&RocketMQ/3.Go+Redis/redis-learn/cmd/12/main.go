package main

import (
	"context"
	"fmt"
	"redis-learn/service"
	"redis-learn/utils/redisClient"
	"time"

	"github.com/gogf/gf/v2/util/gconv"
)

func main() {
	ctx := context.Background()
	client := redisClient.GetRedisClient()

	/*
		A. 初始化用户信息
	*/
	// 1.新建一个对象
	// HSET user:profile:1001 user_id 1001 NickName "Hitokui"
	user := service.NewUserService(client)
	err := user.UpdateUserProfile(ctx, 1001, &service.UserProfile{
		UserID:   1001,
		NickName: "Hitokui",
		Avatar:   "https://example.com/avatar.jpg",
		Sex:      "male",
	})
	if err != nil {
		panic(err)
	}

	//2. 获取用户信息
	// HGETALL user:profile:1001
	profile, err := user.GetUserProfile(ctx, 1001)
	if err != nil {
		panic(err)
	}
	fmt.Println("用户信息: ", gconv.String(profile))

	// /*
	// 	B.给文章点赞
	// */
	// // 1.初始化一些文章
	// var (
	// 	article1 int64 = 9000
	// 	article2 int64 = 9001
	// 	article3 int64 = 9002
	// 	article4 int64 = 9003
	// )

	// //2. 三个人给 9000 点赞
	// // LUA脚本
	// rank := service.NewRankService(client)
	// like := service.NewLikeService(client, rank)
	// _, err = like.Like(ctx, article1, 1001)
	// if err != nil {
	// 	panic(err)
	// }
	// _, err = like.Like(ctx, article1, 1002)
	// if err != nil {
	// 	panic(err)
	// }
	// _, err = like.Like(ctx, article1, 1003)
	// if err != nil {
	// 	panic(err)
	// }
	// //两个人给 9001 点赞
	// _, err = like.Like(ctx, article2, 1001)
	// if err != nil {
	// 	panic(err)
	// }
	// _, err = like.Like(ctx, article2, 1004)
	// if err != nil {
	// 	panic(err)
	// }
	// //一个人给 9002 点赞
	// _, err = like.Like(ctx, article3, 1003)
	// if err != nil {
	// 	panic(err)
	// }
	// //一个人给 9003 点赞
	// _, err = like.Like(ctx, article4, 1002)
	// if err != nil {
	// 	panic(err)
	// }

	// //3. 获取点赞数
	// // GET article:9000:like_count
	// likeCount, err := like.GetLikeCount(ctx, article1)
	// if err != nil {
	// 	panic(err)
	// }
	// fmt.Println("文章 9000 的点赞数: ", likeCount)

	// likeCount, err = like.GetLikeCount(ctx, article2)
	// if err != nil {
	// 	panic(err)
	// }
	// fmt.Println("文章 9001 的点赞数: ", likeCount)

	// likeCount, err = like.GetLikeCount(ctx, article3)
	// if err != nil {
	// 	panic(err)
	// }
	// fmt.Println("文章 9002 的点赞数: ", likeCount)

	// likeCount, err = like.GetLikeCount(ctx, article4)
	// if err != nil {
	// 	panic(err)
	// }
	// fmt.Println("文章 9003 的点赞数: ", likeCount)

	// //4.取消点赞
	// like.Unlike(ctx, article1, 1001)
	// likeCount, err = like.GetLikeCount(ctx, article1)
	// if err != nil {
	// 	panic(err)
	// }
	// fmt.Println("现在, 文章 9000 的点赞数: ", likeCount)

	// /*
	// 	C. 关注
	// */
	// follow := service.NewFollowService(client)
	// // 1.用户 1001 关注了 1002 和 1003
	// // LUA脚本
	// follow.Follow(ctx, 1001, 1002)
	// follow.Follow(ctx, 1001, 1003)
	// follow.Follow(ctx, 1001, 1004)
	// // 用户 1003 关注了 1002, 1004
	// follow.Follow(ctx, 1003, 1002)
	// follow.Follow(ctx, 1003, 1004)

	// //2. 共同关注
	// common, err := follow.CommonFollowing(ctx, 1001, 1003)
	// if err != nil {
	// 	panic(err)
	// }
	// fmt.Println("用户 1001 和 1003 的共同关注: ", common)

	// /*
	// 	D. 获取排行榜
	// */
	// topN, err := rank.GetTopN(ctx, 3)
	// if err != nil {
	// 	panic(err)
	// }
	// fmt.Println("今日排行榜 Top 3: ", topN)

	/*
		E. 日活
	*/
	stats := service.NewStatsService(client)
	// 1. 记录用户登录访问
	stats.RecordDailyActiveUser(ctx, 1001)
	stats.RecordDailyActiveUser(ctx, 1001)
	stats.RecordDailyActiveUser(ctx, 1001)
	stats.RecordDailyActiveUser(ctx, 1002)
	stats.RecordDailyActiveUser(ctx, 1003)

	dau, err := stats.GetDailyActiveUserCount(ctx, time.Now())
	if err != nil {
		panic(err)
	}
	fmt.Println("今日活跃用户数: ", dau)

	//2. 文章 UV
	stats.RecordArticleView(ctx, 9000, 1001)
	stats.RecordArticleView(ctx, 9000, 1001)
	stats.RecordArticleView(ctx, 9000, 1001)
	stats.RecordArticleView(ctx, 9000, 1002)
	stats.RecordArticleView(ctx, 9000, 1004)
	stats.RecordArticleView(ctx, 9000, 1004)
	stats.RecordArticleView(ctx, 9000, 1005)

	uv, err := stats.GetArticleUVCount(ctx, 9000)
	if err != nil {
		panic(err)
	}
	fmt.Println("文章 9000 的 UV 数量: ", uv)

}
