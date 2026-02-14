package main

import (
	"fmt"
	"testing"
	"time"
) //标准库

func TestInterfaceMain(t *testing.T) {
	alipay := Alipay{
		AppID:      "88888888",
		AppSecret:  "sadasfdgfd",
		MerchantID: "1001",
	}
	wechatPay := WechatPay{
		AppID:      "66666666",
		AppSecret:  "fdsfsdfknc",
		MerchantID: "3029",
	}

	transactions := []struct {
		Payment Payment
		Amount  float64
		Name    string
	}{
		{alipay, 100, "支付宝"},
		{wechatPay, 200, "微信"},
	}

	for _, pp := range transactions {
		transactionID, err := ProcessPayment(pp.Payment, pp.Amount)
		if err != nil {
			fmt.Printf("交易失败, 错误信息: %s\n", err.Error())
			continue
		}
		fmt.Printf("ProcessPayment支付成功, 订单号: %s\n", transactionID)
	}

	/*
		空接口 与 断言
	*/
	var anything interface{}
	anything = 123
	assertEmptyInterface(anything)

	anything = "string"
	assertEmptyInterface(anything)

}

/*
各种支付方式都要有支付和退款两个方法
*/
type Payment interface {
	Pay(amount float64) (string, error)
	Refund(transactionID string, amount float64) (string, error)
}

type Alipay struct {
	AppID      string
	AppSecret  string
	MerchantID string
}

type WechatPay struct {
	AppID      string
	AppSecret  string
	MerchantID string
}

/*
实现接口方法
这里值接受者和指针接受者均可
*/
func (a Alipay) Pay(amount float64) (string, error) {
	return fmt.Sprintf("ALIPAY-PAY-%d", time.Now().Unix()), nil
}
func (a Alipay) Refund(transactionID string, amount float64) (string, error) {
	return fmt.Sprintf("ALIPAY-REFUND-%s", transactionID), nil
}

func (a WechatPay) Pay(amount float64) (string, error) {
	return fmt.Sprintf("WechatPay-PAY-%d", time.Now().Unix()), nil
}
func (a WechatPay) Refund(transactionID string, amount float64) (string, error) {
	return fmt.Sprintf("WechatPay-REFUND-%s", transactionID), nil
}

/*
统一处理支付
这里传参是interface, 抽象,多态的体现
是一个基类 BaseClass
*/
func ProcessPayment(p Payment, amount float64) (string, error) {
	fmt.Println("----开始处理支付请求----")
	transactionID, err := p.Pay(amount)
	if err != nil {
		return "", err
	}
	fmt.Printf("支付成功, 交易号: %s\n", transactionID) //_用于接收 “不需要使用的返回值”；
	return transactionID, nil
}

/*
空接口与断言
*/
func assertEmptyInterface(i interface{}) {
	switch v := i.(type) {
	case int:
		fmt.Println("int: ", v)
	case string:
		fmt.Println("string: ", v)
	default:
		fmt.Println("Unknown")
	}
}
