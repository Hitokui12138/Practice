# 练习的说明文件
- https://github.com/Hitokui12138/Practice
## git使用方法
### A 创建本地仓库
1. `cd` 到目标路径
2. `git init` 初始化,自动创建一个".git"隐藏文件夹用来管理版本信息
3. `git add .` 把当前路径下所有文件`add`到 暂存区
4. `git commit -m "本次提交的MSG"` 把暂存区的文件`commit`到 本地仓库
5. 注意上一步不加`-m`的话会进入vim编辑器
- 按`i`进入编辑模式
- 按`esc`退出编辑模式，再输入`:wq`退出vim
6. `git log`查看这次的提交，VSCODE自带一些基本功能，也可以安装一个`git history diff`插件
----
### B 创建Github远程仓库
- 创建完成后按照指示操作
1. `git branch -M main`
2. `git remote add origin https....`
3. `git push -u origin main` 第一次`push`因为远程仓库为空，所以加上`-u`
4. 之后`push`只要`git push origin main`就好
----
### C 新人想加入项目
1. `git clone httpsXXXX`clone到本地
2. `git push origin main`试一下能不能行
3. ``
----
## homebrew
1. `brew ls tomcat` 查看安装地址
2. `brew services list` 查看当前服务列表和状态
    - `brew services start tomcat@9` 启动服务
    - `brew services stop mysql` 停止服务

## Config
1. `vim .bash_profile`编辑配置文件
2. `source ~/.bash_profile` 让这个配置文件生效

## 打开mac隐藏文件
1. Command + Shift + .
## 查看端口
1. `sudo lsof -i tcp:8080` 查看占用情况
2. `sudo kill -9 3210` 3210是该进程的PID

## 使用idea社区版
1. 创建目录结构(SpringMVC)
- SpringBoot结构稍微不同
    - src
        - main
            - java
            - resources
            - webapp(手动创建)
                - WEB-INF(手动创建)
                    - web.xml(非必需)
                - index.html(测试用首页)
        - test
2. 打开smart tomcat
    - 设置Deployment Drictory 到webapp下面(会自动设置)
3. Project Structure
    1. Project 检查jdk
    2. Modules -> Path(只有SpringBoot需要?)
        - 选Use Module Compile output path
            - 设置到web/WEB-INF/classes

## PlantUML
1. 下载插件plantUML,这样可以在.puml文件里画图
2. 想在markdown里使用的话还需要:
    1. plantuml.server

## git push 时 400
1. remote remove origin
2. remote add origin git@github.com:Hitokui12138/Practice.git
3. push -u origin main
