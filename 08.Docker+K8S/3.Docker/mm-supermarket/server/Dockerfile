# 使用官方的 Golang 基础镜像，指定 Go 版本
FROM golang:1.18 as build

# 设置工作目录
WORKDIR /app

# 将 Go 源代码复制到容器中
COPY . .

# 编译 Go 应用程序
RUN go env -w GOPROXY=https://goproxy.cn,direct && go mod tidy && go build -o main

# 构建最终的容器镜像
FROM debian:buster-slim

# 复制编译后的 Go 应用程序到容器中
COPY --from=build /app/main /app/main

# 暴露应用程序的端口（根据您的应用程序配置）
EXPOSE 8080

# 设置启动命令
CMD ["/app/main"]