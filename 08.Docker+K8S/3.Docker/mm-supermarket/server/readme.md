# 慕慕水果超市后端服务

## 构建镜像
```bash
docker build -t mm-supermarket-server:v1 .
```
## 运行容器
```bash
docker run -d --name mm-supermarket-server --net mm-supermarket mm-supermarket-server:v1
```
