# 使用官方的 Nginx 基础镜像
FROM nginx:1.19

# 可选：设置作者信息
LABEL maintainer="muxian@imooc.com"

# 可选：将本地的自定义 Nginx 配置文件复制到镜像中
# 如果不需要自定义配置，可以跳过这一步
# COPY nginx.conf /etc/nginx/nginx.conf

# 可选：将您的静态网站文件复制到 Nginx 的默认 web 根目录
# 这里假设您的静态文件位于当前目录的 "html" 文件夹中
COPY html/ /usr/share/nginx/html/

# 将图片文件复制到 Nginx 的默认 web 根目录下的 images 子目录中
# 这里假设您的图片文件位于当前目录的 "static/images" 文件夹中
COPY static/images/ /usr/share/nginx/html/images/
# 配置 Nginx 以将所有请求代理到后端接口的地址
# 这里的后端接口地址需要根据您的实际部署环境进行配置
# 例如，如果后端接口运行在同一 Docker 网络中，可以使用容器名称或服务名称来配置代理
# 如果后端接口运行在不同的主机上，需要将代理地址设置为实际的后端接口地址
COPY nginx.conf /etc/nginx/nginx.conf

# 可选：暴露 Nginx 监听的端口
# 默认情况下，Nginx 使用 80 端口
# EXPOSE 80

# 可选：定义启动容器时运行的命令
# 如果不定义，将使用 Nginx 默认启动命令
# CMD ["nginx", "-g", "daemon off;"]
