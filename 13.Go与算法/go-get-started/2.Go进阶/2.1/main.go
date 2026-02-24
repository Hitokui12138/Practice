package main

import (
	"context"
	"fmt"
	"math/rand"
	"runtime"
	"sync"
	"time"
)

func main() {

	// start := time.Now()
	urlSlice := []string{
		"https://www.baidu.com",
		"https://www.sina.com.cn",
		"https://www.taobao.com",
		"https://www.jd.com",
		"https://www.1688.com",
	}

	// //如果是串行执行 大约2s
	// for _, url := range urlSlice {
	// 	fetchData(url, nil)
	// }

	// end := time.Now()
	// fmt.Println("串行执行耗时: ", end.Sub(start))

	// //使用协程 大约1s
	// start = time.Now()

	// /*
	// 	A. 启动多个协程
	// 		1. 给`每个url`都启动一个协程
	// 		2. 等待所有协程执行完毕
	// */
	// var wg sync.WaitGroup //定义一个等待组
	// for _, url := range urlSlice {
	// 	wg.Add(1)
	// 	go fetchData(url, &wg) //启动一个协程
	// }
	// wg.Wait() //等待所有协程执行完毕

	// end = time.Now()
	// fmt.Println("并行执行耗时: ", end.Sub(start))

	// /*
	// 	B. 设置协程超时时间
	// */
	// //创建一个上下文, 设置3秒超时
	// ctx, cancel := context.WithTimeout(context.Background(), 3*time.Second)
	// defer cancel() //在函数退出时, 调用取消函数, 释放资源

	// //启动5个worker协程
	// for i := 0; i < 2; i++ {
	// 	go worker(ctx, i)
	// } //两个程序会在3秒ctx过期后退出

	// time.Sleep(time.Second * 5)

	/*
		C. 协程池限制新建协程数量
		1. 后面的task必须等待前面的task执行完毕才能开启线程调用
	*/
	// 有五个URL, 但是线程池最大只有三个
	limitedWorkerPool(urlSlice, 3)
	showGoroutineInfo()

}

func fetchData(url string, wg *sync.WaitGroup) {
	if wg != nil {
		defer wg.Done()
	}
	delay := time.Duration(rand.Intn(1000)) * time.Millisecond
	time.Sleep(delay)

	fmt.Println("从 ", url, " 获取的数据, 耗时: ", delay)
}

func worker(ctx context.Context, id int) {
	for {
		select {
		case <-ctx.Done(): //如果上下文收到取消信号, 则退出
			fmt.Println("worker ", id, " 收到取消信号, 退出")
			return
		case <-time.After(1 * time.Second): //如果3秒内没有收到取消信号, 则超时
			fmt.Println("worker ", id, " 1秒内没有收到取消信号, 超时退出")
			return
		default:
			fmt.Println("worker ", id, " 正在运行")
			time.Sleep(500 * time.Millisecond)
		}
	}

}

func limitedWorkerPool(task []string, max int) {
	semaphore := make(chan struct{}, max)
	var wg sync.WaitGroup
	for i, t := range task {
		wg.Add(1)
		semaphore <- struct{}{} //通知semaphore启动了一个协程
		// 然后启动一个协程来处理任务t
		go func(i int, task string) {
			defer func() {
				<-semaphore //任务处理完毕, 释放一个semaphore
				wg.Done()   //等待组减一
			}()
			fmt.Println("worker ", i, " 正在处理任务: ", task)
			time.Sleep(500 * time.Millisecond)
			fmt.Println("worker ", i, " 处理任务: ", task, " 完毕")
		}(i, t)
	}
	wg.Wait()
	fmt.Println("所有任务处理完毕")
}

// 可以看一下你的电脑支持多少个线程
func showGoroutineInfo() {
	fmt.Println("当前Goroutine数量: ", runtime.NumGoroutine())
	fmt.Println("最大Goroutine数量: ", runtime.GOMAXPROCS(0))
	fmt.Println("当前CPU数量: ", runtime.NumCPU())
}
