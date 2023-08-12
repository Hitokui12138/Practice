# 培训练习作业的说明文件
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