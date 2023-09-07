1. `~` :peihang.gu文件夹下
2. `/` :User外面,真正的根目录,usr和opt在这里
3. 下载了一个tree插件
    - `tree -L 1`



## homebrew
```shell
## homebrew
1. `brew ls tomcat` 查看安装地址
2. `brew services list` 查看当前服务列表和状态
    - `brew services start tomcat@9` 启动服务
    - `brew services stop mysql` 停止服务
```
brew安装软件后：

1.配置文件在/usr/local/etc中

2.安装文件在/opt/homebrew/Cellar中

3.二进制可执行程序的软连接在/usr/local/bin中

homebrew路径转化原理：

1、通过brew install安装应用最先是放在/opt/homebrew/Cellar/目录下。
2、有些应用会自动创建软链接放在/usr/bin或者/usr/sbin，同时也会将整个文件夹放在/usr/local

查找在homebrew安装软件的路径，以opencv为例：

`brew list opencv`

# tomcat
1. 

cmd + , 打开setting
搜索maven,更改home,
用mvn -v 查看路径

找不到javaee support的问题

stack overflow