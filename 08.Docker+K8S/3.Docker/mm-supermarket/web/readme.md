# 慕慕水果超市前端服务

# 构建镜像
```bash
docker build -t mm-supermarket-web:v1 . 
```

# 运行容器
```bash
docker run -d --name mm-supermarket-web --net mm-supermarket -p80:80 mm-supermarket-web:v1
```
