module redis-learn

go 1.24.5

require github.com/redis/go-redis/v9 v9.18.0

require (
	go.opentelemetry.io/otel v1.40.0 // indirect
	go.opentelemetry.io/otel/trace v1.40.0 // indirect
	go.uber.org/atomic v1.11.0 // indirect
)

require (
	github.com/cespare/xxhash/v2 v2.3.0 // indirect
	github.com/dgryski/go-rendezvous v0.0.0-20200823014737-9f7001d12a5f // indirect
	github.com/gogf/gf/v2 v2.10.0
)

//1. 安装依赖的方法
// go get github.com/gogf/gf/v2@latest
//2. 同步到go.mod
// go mod tidy
