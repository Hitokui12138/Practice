package main

import (
	"fmt"
	"math/rand"
	"time"
)

func main() {
	// rand.Seed(time.Now().UnixNano())

	// orderChan := make(chan Order, 10)  // 订单通道, 缓冲区大小为10
	// resultChan := make(chan Order, 10) // 结果通道, 缓冲区大小为10
	// done := make(chan bool)            // 完成信号通道

	// go OrderProduct(orderChan, 20) // 启动订单生产协程
	// //启动多个协程来处理订单
	// for i := 0; i < 3; i++ {
	// 	go orderProcessor(orderChan, resultChan) // 启动订单处理协程
	// }
	// go orderResult(resultChan, done) // 启动协程收集结果

	// <-done // 等待所有订单处理完毕

	// /*
	// 	多路复用, 类似于epoll
	// 	使用select, 哪个channel先来, 就处理哪个channel的数据
	// */
	// ch1 := make(chan string)
	// ch2 := make(chan string)
	// go func() {
	// 	time.Sleep(time.Second * 2)
	// 	ch1 <- "来自ch1"
	// }()
	// go func() {
	// 	time.Sleep(time.Second * 2)
	// 	ch1 <- "来自ch2"
	// }()

	// //用select捕获数据
	// for i := 0; i < 2; i++ {
	// 	select {
	// 	case msg := <-ch1:
	// 		fmt.Println(msg)
	// 	case msg := <-ch2:
	// 		fmt.Println(msg)
	// 	case <-time.After(time.Second * 3):
	// 		fmt.Println("超时")
	// 		return
	// 	}
	// }

	// /*
	// 	只执行一小段时间
	// */
	// ticket := time.NewTicker(time.Millisecond * 500) //执行间隔
	// done := make(chan bool)
	// go func() {
	// 	for { //死循环
	// 		select {
	// 		case <-done:
	// 			return
	// 		case t := <-ticket.C:
	// 			fmt.Println("当前时间: " + t.Format("2006-01-02 15:04:05"))
	// 		}
	// 	}
	// }()

	// time.Sleep(2 * time.Second) // 等两秒以触发定时器

	/*
		协程池
	*/
	jobs := make(chan int, 100)
	results := make(chan int, 100)
	// 这就是工作池
	for i := 0; i < 3; i++ {
		go worker(jobs, results)
	}
	//发送100个任务
	for i := 0; i < 100; i++ {
		jobs <- i
	}
	close(jobs)
	//干完之后打印结果
	for value := range results {
		fmt.Println("result: ", value)
	}
}

/*
用Channel模拟高并发状态下的订单处理
*/

type Order struct {
	ID       int
	UserID   string
	Amount   float64
	Status   string
	CreateAt time.Time
}

/*
定义生产者

	参数 orderChan chan<- Order 表示只能写入 Order 类型数据的通道
*/
func OrderProduct(orderChan chan<- Order, number int) {
	for i := 1; i <= number; i++ {
		order := Order{
			ID:       i,
			UserID:   fmt.Sprintf("user%d", rand.Intn(100)),
			Amount:   rand.Float64()*100 + 100,
			Status:   "pending",
			CreateAt: time.Now(),
		}
		orderChan <- order //写入到channel
		fmt.Printf("生产订单: %v\n", order)
		time.Sleep(time.Duration(rand.Intn(1000)) * time.Microsecond) // 模拟订单处理时间
	}
	close(orderChan) // 关闭channel, 表示生产者生产完毕
}

/*
消费者处理器
同时需要一个结果通道 resultChan chan<- Order 表示只能写入 Order 类型数据的通道
还需要一个resultChan来聚合结果
*/
func orderProcessor(orderChan <-chan Order, resultChan chan<- Order) {
	for order := range orderChan {
		fmt.Printf("处理订单: %v\n", order)
		order.Status = "processed"
		time.Sleep(time.Duration(rand.Intn(1000)) * time.Microsecond) // 模拟订单处理时间
		order.Status = "completed"
		resultChan <- order // 写入处理后的订单到结果通道
	}
	close(resultChan) // 关闭结果通道, 表示该消费者处理完毕
}

/*
外面只需要等待done通道关闭, 就可以知道所有订单都处理完毕了
注意resultChan是只读通道, 只能从resultChan中读取数据, 不能写入数据
donechan是只写通道, 只能向donechan中写入数据, 不能读取数据
*/
func orderResult(resultChan <-chan Order, done chan<- bool) {
	for order := range resultChan {
		fmt.Printf("聚合订单结果: %v\n", order)
	}
	done <- true // 发送完成信号
}

func worker(jobs <-chan int, results chan<- int) {
	for j := range jobs {
		fmt.Println("worker: ", j)
		time.Sleep(time.Second)
		results <- j * 2 //处理结果就是 二倍x
	}
	close(results)
}
