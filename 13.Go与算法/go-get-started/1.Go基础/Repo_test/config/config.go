// Package config 这个文件是用来演示如何使用配置文件的, 以及如何通过命令行输入参数来覆盖配置文件的值的
package config

import (
	"encoding/json"
	"fmt"
	"io"
	"os"
)

// Config 也可以写yaml格式的配置文件, 这里我们用json格式的配置文件来演示
type Config struct {
	Port     int    `json:"port"`
	LogLevel string `json:"log_level"`
}

func LoadConfig(filename string) (*Config, error) {
	// 用os包打开文件（获取文件的 “访问句柄”）
	file, err := os.Open(filename)
	if err != nil {
		return nil, fmt.Errorf("failed to open config file: %w", err)
	}
	defer file.Close()
	// 读取文件内容」（通过句柄把文件数据加载到内存）
	content, err := io.ReadAll(file)
	if err != nil {
		return nil, fmt.Errorf("failed to read config file: %w", err)
	}

	//读取后, 存入一个对象中
	var config Config
	err = json.Unmarshal(content, &config)
	if err != nil {
		return nil, fmt.Errorf("failed to unmarshal config: %w", err)
	}

	return &config, nil
}

// SaveConfig 为了演示命令行输入的用法
func SaveConfig(filename string, config *Config) error {
	file, error := os.Create(filename)
	if error != nil {
		return fmt.Errorf("failed to create config file: %w", error)
	}
	defer file.Close()

	content, err := json.MarshalIndent(config, "", "  ")
	if err != nil {
		return fmt.Errorf("序列化配置文件失败: %w", err)
	}

	_, err = file.Write(content)
	if err != nil {
		return fmt.Errorf("写入配置文件失败: %w", err)
	}
	return nil
}
