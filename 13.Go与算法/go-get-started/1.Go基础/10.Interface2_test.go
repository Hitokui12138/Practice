package main

import (
	"fmt"
	"testing"
) //标准库

/*
1. 现有需求, 微信通知, 邮件通知, 短信通知等等
2. 因此抽象出一个 通知 接口
3. 但有时几个通知都要, 因此讲接口与service相组合
*/
func TestInterface2Main(t *testing.T) {
	orderService := &OrderService{}

	emailNotifier := EmailNotifier{
		SMTPHost: "smtp.example.com",
		Port:     587,
	}
	smsNotifier := SmsNotifier{
		APIKey:   "test_APIKey",
		TmplCode: "test_template_code",
	}
	broadCastNotifier := &BroadCastNotifier{
		notifiers: []Notifier{emailNotifier, smsNotifier},
	}

	//1. 只发送Email
	orderService.SetNotifer(emailNotifier)
	orderService.CreateOrder("(Email发送)-手机", 1)

	//2. 都想发
	orderService.SetNotifer(broadCastNotifier)
	orderService.CreateOrder("(两者都发送)-电脑", 3)

}

type Notifier interface {
	Notify(message string) error
}

/* Email实现接口 */
type EmailNotifier struct {
	SMTPHost string
	Port     int
}

func (e EmailNotifier) Notify(message string) error {
	fmt.Printf("发邮件通知: %s\n", message)
	return nil
}

/* SMS实现接口 */
type SmsNotifier struct {
	APIKey   string
	TmplCode string
}

func (s SmsNotifier) Notify(message string) error {
	fmt.Printf("发短信通知: %s\n", message)
	return nil
}

/* Service组合接口 */
type OrderService struct {
	notifier Notifier
}

func (o *OrderService) SetNotifer(n Notifier) {
	o.notifier = n //选择一种实现方式
}

func (o *OrderService) CreateOrder(product string, quantity int) {
	fmt.Printf("创建订单: %s x %d\n", product, quantity)
	err := o.notifier.Notify("订单已创建")
	if err != nil {
		fmt.Println("消息发送失败", err)
	}
}

/* 如果我想同时选择多种方式呢? 再包装一层 */
type BroadCastNotifier struct {
	notifiers []Notifier
}

// 再次实现接口
func (b *BroadCastNotifier) Notify(message string) error {
	for _, n := range b.notifiers {
		err := n.Notify(message)
		if err != nil {
			return err
		}
	}
	return nil
}
