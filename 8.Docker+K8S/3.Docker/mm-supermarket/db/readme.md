# 数据库服务

## 构建镜像
```bash
docker build -t mm-supermarket-db:v1 .
```
## 运行容器
```bash
docker run -d --name mm-supermarket-db --net mm-supermarket  mm-supermarket-db:v1
```

