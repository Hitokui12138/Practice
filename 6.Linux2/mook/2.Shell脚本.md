# Shell
1. 命令行的解释器,接收命令,调用操作系统的内核
    ```sh
    #查看当前支持的shell
    cat /etc/shells
    #/bin/sh 是指向 /usr/bin/sh 的连接


    #查看默认shell
    ls -l /bin/ | grep sh
    echo $SHELL
    #ubuntu 的默认sh指向dash


    #安装zsh
    sudo apt-get install -y zsh
    #更改成zsh
    sudo chsh -s /usr/bin/zsh
    ```
2. 执行脚本
    ```sh
    #!/bin/zch  //脚本一般都是以这句开头,指定shell解释器e
    echo "hello world"
    ```
    1. 执行脚本的方法
        1. sh hello.sh
            - 不需要赋予x权限,因为是当作sh的参数传入的
        2. 直接输入脚本的路径
            - 但是权限不够,因为touch创建的文件没有x权限
            - chomd +x hello,sh
            - 相对路径的话要加个. `./100_hello.sh`
        3. 不开子shell的方法,shell是可以嵌套的
            - `. 100_hello.sh`
            - `source 100_hello.sh`
        4. 如果二进制脚本放在/bin/下面的话可以直接执行
            - `cp hello.sh /bin/`,不过最好不要这样做
            - 只要是在`$PATH`下面的都可以直接执行
3. 变量
    1. 查看当前shell?
        - `ps -f`
    2. 系统预定义变量 $大写字母
        - 变量就是内存中存储的一个数据
        1. echo $HOME,当前家目录
        2. $PWD,当前目录
        3. $SHELL,当前使用的shell解析器
        4. $USER,当前登录的user
        5. `env | less`, 查看所有系统全局变量
        6. `set | less`,所有变量和函数(系统和用户)
    3. 用户自定义变量
        1. `变量名=变量值`,注意不能加空格
            - 有空格时,双引号单引号都可以
            1. 定义成全局变量
                1. 定义自定义变量
                2. `export 变量名`,提升成全局变量
                    - 但是子shell改了全局变量后不会影响父shell,可以影响子子shell
                    - 常量建议大写
        2. `unset 变量名`,撤销
        3. `readonly b=5`,只读变量,一旦设定不能更改,常量
            - 常量不能unset,不能更改
    4. 全局变量与局部变量
        1. 全局变量
            - 对于当前bash进程,和子shell会话,全局变量对他们可见
        2. 局部变量只对`当前shell`有效果
    5. 把参数传给脚本,linux特殊参数
        1. `$n`,$1,$2... ${10}
            - $0,比较特殊,表示这个脚本的名称
            - `echo "hello $1"`
            - `echo 'hello $n'`,单引号里$不会被识别,可以原封不动输出
        2. `$#`,获取输入参数的个数,参数个数统计
        3. `$*`,`$@`
            1. $* 把所有参数看作一个整体
                - 只是看一下当前的参数
            2. $@ 相当于获得一个参数数组
                - 可以用for循环去遍历参数
        4. `echo $?`
            1. 为0表示上一条执行正常
            2. 为127表示上一条执行不正常
        1. ``
4. 运算符号
    1. expr,麻烦的旧方法
        - 注意要带空格,否则还是相连
        - 相当于有expr有三个参数
            ```sh
            expr 1 + 2
            expr 5 - 2
            expr 5 \* 2
            #`命令替换`,把命令替换成值
            a=$(expr 5 \* 2)
            a=`expr 5 \* 2`
            ```
    2. 运算符号,最推荐
        - 在括号里面时空格可有可无
        ```sh
            #!/etc/sh
            #下面这样不能被识别为运算
            a=1+5
            #两种方法
            a=$((1+5))
            a=$[5+9]
            a=$[2*4]
            echo a
        ```
    3. 计算(2+3)*4
        ```sh
        a=$[(2+3)*4]
        ```
5. 条件判断
    1. test [condition],然后捕获返回结果`$?`
        - `$?`是判断命令是否执行成功用的
        ```sh
        a=hello
        test $a=hello
        echo $?
        test $a=hello
        echo $?
        ```
        1. 返回值为0时则true,为1时为false
    2. `[ condition ]`,注意,
        1. 前后一定需要有`空格`
        2. =两边也必须有空格,否则变成赋值语法了
            ```sh
            #[_$a_=_hello_]
            [ $a = hello ]
            [ asda ] #0
            [  ] #1

            #不等号
            [ $a != hello ]
            ```
    3. 数字大小比较
        1. 字符串直接使用=!=,但数字有自己的一套
            ```sh
            #equal not equal
            [ 2 -eq 8 ]
            [ 2 -ne 8 ]
            #less than  less equal
            (( 2 >= 8 ))
            [ 2 -lt 8 ]
            [ 2 -le 8 ]
            #greater than  greater equal
            [ 2 -gt 8 ]
            [ 2 -ge 8 ]
            ```
    4. 文件权限判断
        ```sh
        [ -r hello.sh ]
        [ -w hello.sh ]
        [ -x hello.sh ]
        ```
    5. 文件类型判断
        ```sh
        #exist?
        [ -e hello.sh ]
        #file?
        [ -f hello.sh ]
        #directory?
        [ -d hello.sh ]
        ```
    6. 多个条件同时判断
        1. 命令1 && 命令2,1执行成功时执行2
        2. 命令1 || 命令2,1执行失败时执行2
        3. `[ condition ] && 命令1 || 命令2`,三元运算符
        ```sh
        a=1
        [ $a -eq 2 ] && echo true || echo false
        ```
    7. 逻辑与,逻辑或
        ```sh
        if [ $a -gt 18 ] && [ $a -lt 35 ]
        then
            echo OK
        fi
        ```
        ```sh
        [ $a -gt 18 ] && [ $a -lt 35 ]
        [ $a -gt 18 -a $a -lt 35 ]
        [ $a -gt 18 -o $a -lt 35 ]
        ```
6. 分支与循环
    1. 单分支
        ```sh
            # if [];then 程序;fi   写在一行里
        if []
        then
            程序
        fi
        ```
    2. 多分支
        ```sh
        if []
        then
            程序
        elif []
        then
            程序
        else
            程序
        fi
        ```
    3. 字符串非空技巧
        ```sh
        if[ "$1"x = "abc" ]
        then
            echo "Welcome $1"
        fi
        ```
    4. case 多分支
        - 两个;;表示break
        - *) 表示default
        ```sh
        case $1 in
        "值1")
            程序
        ;;
        "值2")
            程序
        ;;
        *)
            程序
        ;;
        esac
        ```
    5. 循环
        1. 
            ```sh
            #!/bin/zsh
            for (( i=1; i <= $1; i++ ))
            do
                    sum=$[ $sum + $i ]
            done
            ```
            - 这里使用了双小括号,这样就可以直接写运算符>=了
        2. 最常用
            ```sh
            for 变量 in 1 2 3 
            do
                程序
            done

            #foreach
            for 变量 in {1..100}
            do
                程序
            done
            ```
        3. $*与¥@
            ```sh
            #正常输出
            echo '==$*=='
            for param in $*
            do
                    echo $param
            done

            #正常输出
            echo '==$@=='
            for param in $@
            do
                    echo $param
            done

            #当作整体
            echo '=="$*"=='
            for param in "$*"
            do
                    echo $param
            done
            #正常输出
            echo '=="$@"=='
            for param in "$@"
            do
                    echo $param
            done
            ```
        4. while循环
            ```sh
            #!/bin/zsh
            a=1
            while [ $a -le $1 ]
            do
                    sum=$[ $sum + $a ]
                    a=$[ $a + 1 ]
            done
            echo $sum
            ```
    6. linux本来就不适合做这种高级语言特性
        - 另一种`let语法`
        ```sh
        #!/bin/zsh
        a=1
        while [ $a -le $1 ]
        do
        #       sum=$[ $sum + $a ]
        #       a=$[ $a + 1 ]
                let sum+=a
                let a++
        done
        echo $sum
        ```
    7. 读取控制台输入
        1. read 选项 参数
            1. -p ,指定提示信息 ,-n
            2. -t ,等待用户输入的时间,如果不加-t则一直等待
            ```sh
            read -t 10 -p "输入你的姓名: " name
            echo "Welcome $name"

            #zsh的区别
            read 'name?What are you doing?'
            ```
7. 函数
    1. 之前的脚本可以包装成函数
            ```sh
            #!/bin/zsh
            sum=$[$1+$2]
            echo $sum



            ```
        1. 脚本只能靠$#来查看执行结果
        2. 脚本调用不方便
        3. 函数相当于轻量级脚本
    2. 系统函数
        1. date函数
            1. 显示日期
            2. `date +%s` 时间戳
            3. 在脚本中使用,`$(date +%s)`,形成了一个命令替换的状态
        2. basename string suffname 
            1. $0 会显示路径名
            2. basename是字符串的剪切,获得最后一个/之后的字符串
                - basename /root/scripts.sh
            3. 把`.sh`也去掉
                - basename /root/scripts.sh .sh
                - $(basename $0 .sh)
        3. dirname 
            - $(dirname $0) ,但是可能出现相对路径
            ```sh
            cd $(dirname $0)
            echo script path: $(pwd)

            #或者
            echo script path: $(cd $(dirname $0); $(pwd))
            ```
    3. 自定义函数
        ```sh
        [function] name[()]
        {
            Action;
            [return int;]
        }
        ```
        1. 调用函数前必须先声明,因为是逐步执行的
        2. 返回值只能通过$?获取
            - 但是也不是什么都能返回的,返回结果码
            - 作为返回值,return后面只能是`(0~255)`
            - 必须是整数
        3. 如果没有return,返回的是最后一句的结果