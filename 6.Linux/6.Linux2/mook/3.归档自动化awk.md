# 练习 归档命令
1. tar命令
    1. 选项
        1. -c 归档 
        2. -z 同时压缩, `.tar.gz`
        3. -f 可视化
    2. 一般直接 -czf
2. 实现一个脚本每天会对指定目录归档备份
    1. 输入一个目录名称
    2. 将目录下的所有文件按照天归档保存,附加归档日期
```sh
#!/bin/bash

#    1. 输入一个目录名称
#    2. 将目录下的所有文件按照天归档保存,附加归档日#!/bin/bash

# 1.判断输入参数个数
if [ $# -ne 1 ]
then
        echo "参数个数错误!只能输入一个归档目录名!"
        exit
fi

# 2.从输入参数获取目录名称,判断是不是一个路径
if [ -d $1 ]
then
        echo
else
        echo
        echo "目录不存在"
        echo
        exit
fi

dir_name=$( basename $1 )
dir_path=$( cd $(dirname $1); pwd )

# 3.获取当前日期
DATE=$(date +%y%m%d)

# 4.定义归档文件名称
FILE=archive_${dir_name}_$DATE.tar.gz
DEST=/Users/peihanggu/playground/archive/$FILE

# 5.开始归档
echo "开始归档.."
echo "归档目标: $DEST"
tar -czf $DEST $dir_path/$dir_name

if [ $? -eq 0 ]
then
        echo
        echo "归档成功!"
        echo "归档文件: $DEST"
        echo
else
        echo
        echo "归档失败!"
        echo
fi

exit
```

# 自动化
1. 加入定时任务 crontab
    1. -l ,看看现在有什么定时任务列表
    2. `crontab -e` ,进入定时任务编辑
        - `0 2 * * * /Users/peihanggu/playground/scripts/daily_archive.sh /Users/peihanggu/playground/scripts`,每天晚上02:00
        - `分 时 天数 月 星期 脚本 参数`

# 文本处理工具结合正则表达式
1. cut,相当于在一个表格里进行提取,可以从每一行剪切字节,字符,字段并输出
    - cut [option] filename
    1. -f 列号
        - 可以提取多咧
    2. -d 按指定分隔符分割,默认是制表符号"\t"
        - cut -d " " -f 1 cut.txt
        - 先-d分割,再-f提取列号
    3. -c 
    ```sh
    cat /etc/passwd | grep bash$ | cut -d ":" -f 1,6,7
    #直到末尾
    cat /etc/passwd | grep bash$ | cut -d ":" -f 1,6-
    echo $PATH | cut -d ":" -f 3-

    #切割ip地址
    ifconfig en0
    ifconfig en0 | grep netmask | cut -d " " -f 2
    192.168.31.222
    ```
2. awk gawk 三个创始人的首字母
    - 把文件诸行渡入,以空格为默认分隔符进行切片,切开部分再进行分析处理
    - 注意单引号
    - awk [opt] '/pattern1/{action1} /pattern2/{action2}...' filename
    - 一个模式对应一个命令
    - action里面是个代码块
    1. OPT
        1. -F 指定输入文件分割符
        2. -v 赋值一个用户定义变量
    2. 以root开头的所有行,并输出第7列
        ```sh
        #使用两个工具
        cat /etc/passwd | grep ^root | cut -d ":" -f 7
        #只使用awk
        cat /etc/passwd | awk -F ":" '/^root/{print $7}'
        ```
        - 用户名:加密密码:用户id:组id:注释:用户家目录:shell解析器
    3. 输出第一列和第七列,中间要以,分割
        - cat好像不能加,分割
        - 可以看出awk要更灵活
        ```sh
        cat /etc/passwd | awk -F ":" '/^root/{print $1","$6","$7}'
        ```
        - 要求在所有列前面添加列名user,shell 在最后一行添加end1,end2
        - 关键字`BEGAIN` ,`END`
        - awk -F ":" 'BEGIN{print "user,shell"} {print $1","$6","$7} END{print "end1,end2"}'
    4. 将所有用户id+1后输出
        - awk -F ":" '/^root/ {print $3+1}'
        - 考虑到代码块可能会很复杂,可以使用脚本和引入一个变量
        ```sh
        #不需要$
        cat /etc/passwd | awk -v i=1 -F ":" '/^root/ {print $3+i}'
        ```
    5. awk的内置变量
        1. FILENAME ,文件名
        2. NR ,number of row 行号
        3. NF ,number of filed 切割后的列的数量
        ```sh
        awk -v i=1 -F ":" '/^root/ {print "文件名: " FILENAME " 行号: "NR" 列数: "NF}' /etc/passwd

        #查看空行行号
        cat /etc/passwd | grep -n ^$
        ifconfig | awk '/^$/ {print "空行: "NR}'
        ```

    