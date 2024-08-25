1. Linux
    1. 在 netcrakft网站可以看到各个软件的使用趋势
        1. 可以查询某个网址是运行在什么服务器上的
    2. Linux与windows
        1. Linux严格区分大小写
        2. 所有内容都是以文件形式保存的,包括硬件
        3. Linux不依靠拓展名区分文件类型
            - 而是依靠文件权限来区分的
            1. Linux没有拓展名这个概念,但我们会约定俗称一些拓展名
                1. 压缩包 gz,tar
                2. 二进制软件包 rpm
                3. 脚本文件 sh
                4. 配置文件 conf
            2. windows程序不能直接在linux中运行
2. 常用
    1. [root@localhost ~]#
        1. root当前用户名
        2. localhost 主机名
        3. 当前所在目录
            1. ~ 家目录 ,/ root目录
            2. 当前登录的用户是
                1. 超级用户# 
                2. 普通用户$ 
    2. 命令的格式 
        - 命令 [选项] [参数]
    3. 目录处理命令
        0. pwd
        1. ls ,list
            1. 选项
                - a,能显示隐藏文件
                - l,详细信息
                - d,direct,查看这个目录本身
                - h,humnan,建议`-lh`一起用,人性化信息显示
                - i,inode,i节点,每个文件的id号
        2. mkdir 建立目录
            1. -p 递归创建,即支持创建多层
        3. cd 切换目录 change directory
            1. 选项
                1. cd ~ ,当前用户的家目录
                2. cd   ,也是回到家目录
                3. cd - ,上次的目录
                4. cd .. ,上一级
                5. cd . ,当前目录,没什么用
            2. 相对路径和绝对路径
                1. 相对路径 参照当前目录
                    - `cd ../usr`
                2. 绝对路径 从`根目录`开始指定,从任何路径下都可以从根路径进行查找
                    - `cd /etc/`
        4. rm,删除
            1. 删除目录还有一个rmdir,但是要求目录为空,因此不用
            2. 选项
                1. -r 删除目录,不加的话删目录会报错
                2. -f 强制,不加的话每个文件都要输入y确认
                    - 纯字符界面没有回收站
                    - `rm -rf /`,让Linux自杀
        5. mv,文件夹重命名
            - mv week01 chapter01
    4. 查看文件内容的命令
        1. cat,打印到控制台
        2. head 默认看10行
            - head lines=3 README.md
        3. tail
            - tail lines=3 README.md
        4. less,看完整全文
            - 上下键滚动
            - q是退出
        5. more, 不好用
    5. 文本编辑
        1. vim
    6. 查看文件信息
        1. file README.md, 解释这个文件是什么
        2. where gcc
            - 查找某条命令或者应用程序的位置
    7. echo 打印
3. Sheel
    1. 变量
        ```sh
        h="hello"
        echo $h
        h="hellox"
        echo "abc$hefg"
        echo "abc${h}efg"
        #只要看到$符号就会替换成变量,可以加个{}
        ```
    2. for循环
        1. 批量改名
            1. week* ,week??

            ```sh
            for i in week??
                do
                echo $i
                done
            ```
            - 去掉week,只要数字
            ```sh
            do
            echo ${i#week}
            done
            ```
            - 实现week01 -》 chapter01
            ```sh
            #不要echo就能执行了,但是注意这样是没有撤销机制的
            do
            echo "mv $i chapter${i#week}"
            done
            ```
4. 网络配置
    1. ping 检查两台主机之间是否可以通讯
        - ctrl c 停止
    2. `ifconfig` 查看当前网络信息 network interface config
        - mac: opt+点击右上角的wifi
        ```
        inet 127.0.0.1 netmask 0xff000000
	    inet6 ::1 prefixlen 128
	    inet6 fe80::1%lo0 prefixlen 64 scopeid 0x1
	    inet6 fe80::c0:7f1b:38e1:8a95%en0 prefixlen 64 secured scopeid 0xb
	    inet 192.168.1.8 netmask 0xffffff00 broadcast 192.168.1.255
        ```
    3. 网络知识
        1. 外网ip
            - 上网时外部给你分配的
        2. 内网ip(`IPv4`)
            1. 192.168 C类地址(一般都是这个)
                - 子网掩码 255.255.255.0 表示前三位都一样时两台主机在同一个局域网里
                - C类地址只有最后一位可以作为ip分配地址,0,255还不能用
            2. 172.17 B类地址(公司?)
        3. Network Adapter 网络适配器 网卡 一个网卡介入到对应的一个网络里
            - 实际上没有这张网卡,是一个虚拟网卡,连上了虚拟网络
            - IP地址应当
            1. 网络适配器的网络连接模式
                1. NAT模式(即使ip前三位不一样,也可以ping通)NetWork Address Transation 地址转换
                    - 专用网络
                    - 外网-路由器-PC提供网卡-虚拟一个路由器-VM
                    - 外网-路由器-实际局域网-再虚拟出来一个局域网
                    - 但是这种情况,主机不知道VM的实际ip(只知道虚拟路由器的ip)
                    - 因此主机又虚拟了一个网卡(现在有两个网卡),接入到虚拟路由器上(相当于接入虚拟局域网),这样就能互通了
                        - VMnet8,
                2. 桥接模式(外网-路由器-主机-网桥-交换机-VM)
                    - 需要主机和VM的ip在同一个网段下
                    - 主机只是个网桥(一种网络连接拓展设备),虚拟机直接连接外部物理机
                    - 但网桥不能一下连好多个VM,因此中间还需要一台交换机
                    - 可以由路由器设置DHCP来动态分配地址
                    - 相当于把虚拟机接入实际局域网,主机和VM可以互通
                    - 虚拟机可以直接访问外部地址,简单方便,缺点是占用ip,可能会不够用
                3. 主机模式
                    - VMnet1
                    - 主机虚拟一个网卡,和VM一起连到同一个交换机
                    - 主机和VM可以互通,但VM无法访问外网
            2. 为了防止重启后服务器ip被重新分配,设置静态ip
                - 这里的静态ip指的是在局域网里的ip,而不是连外网的ip
                - 主机要远程操作虚拟机,因此指定虚拟机的静态ip
                    ```sh
                    vim /etc/sysconfig/network-script/ifcfg-ens33

                    BOOTPROTO="dhcp" -> BOOTPROTO="static"
                    #跳到第四行 4G
                    #移动到下一个单词 w
                    #删除当前的单词 dw
                    ```
                - 修改ip地址需要再加上三项
                    ```sh
                    #IP地址(和nat设置一样即可)
                    IPADDR=192.168.111.100
                    #网关
                    GATEWAY=192.168.111.2
                    #域名解析器
                    DNS1=192.168.111.2
                    ```
                - C类子网掩码默认是255.255.255.0
                - 重启网络服务
                    ```sh
                    service network restart
                    ```
            3. 给每一个主机配一个好记的主机名
                ```sh
                vim /etc/hostname
                ```
                - 应该保存一个`通讯录` 主机名->ip地址
                - 也就是`hosts`文件
                ```sh
                vim /etc/hosts
                ```
                - 域名劫持:即更改你的host文件,这样输入网址后会被跳转到另一个网址里

            
        
