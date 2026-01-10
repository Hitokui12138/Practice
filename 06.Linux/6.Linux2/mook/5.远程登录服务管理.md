# 远程登录
1. 基于ssh登陆 最简单的方式
    1. ssh root@hadoop100
        - 第二次可以免密登陆
    2. ssh -i "OpenSSH.pem" ubuntu@ec2-52-195-175-74.ap-northeast-1.compute.amazonaws.com
    3. 使用远程工具基于ssh进行登陆
        - Xshell, SSH SecureShell等等
        - 想看图形化界面可以使用VNC
# 系统管理
## 服务管理
1. 重启网络
    - 在修改了ifconfig之后
    - service network restart
2. ls /usr/bin/
    - 查看可以使用的指令
3. 进程与服务
    1. 一个正在执行的程序或命令,称为`process` 进程
    2. 启动后一直存在,常驻内存的进程称为`service` 服务
4. 系统服务
    1. 系统启动后自动启动,结束时结束
        - 统称为`系统进程`
        - 具体执行这些服务的进程称为`守护进程` daemon process
            - systemd就是这个
        - 在linux里面可以吧系统进程和守护进程看作一个
    2. `service 服务名 start|stop|restart|status`
        - CentOS 6
        - 新版本也能兼容使用
    3. `systemctl start|stop|restart|status 服务名`
        - CentOS 7
        - /urs/lib/system
5. ping不通的时候可以关闭`NetWork|NetworkManager`服务
    - systemctl stop NetworkManager 停止 禁用是disable
    - restart
    - 或者保留NM,关闭Network

## 系统运行级别
- 配置开机自启动
1. `setup`, 系统设置交互界面
    1. 有*的表示会开机自启动
        - 选中之后按下空格
        1. SysV initscript ,SysV的启动脚本,就是老版本
        2. systemd service
2. 具体选哪些自启动和系统的运行级别有关
    1. Linux一共有7种级别,主要是用3和5
        1. 3 多用户,有网,无图形界面 multi-user.target
        2. 5 多用户,有网,有图形界面, graphical.target
        3. 查看运行级别 systemctl get-default
    2. 开机步骤
        1. 开机
        2. BIOS 自检启动
        3. /boot 整个Linux内核的初始化
        4. init进程,启动其他服务的根源
        5. 判断运行级别
        6. 根据级别启动服务
3. 配置开机自启动项
    1. `chkconfig -list`,老版本的方法
4. 防火墙
    1. `iptables`centOS6之前
        - 相当于ip名单的筛选机制
    2. `firewalld`centOS7,有d表示守护进程
        ```sh
        systemctl stop firewalld
        systemctl start firewalld
        ```
# 关机与重启
1. 服务器一般不会做关机操作
2. shutdown
    1. 默认是1分钟后关机,因为默认会做一个同步操作
    2. -c 取消之前的关机指令
    3. now 立即关机,也会先同步后关机
    4. 15:28 定时关机
3. sync 同步
    - 手动将数据立即从内存同步到硬盘
    - Linux有预先读取和延迟写入的特性
    - 比如vim,使用`:w`后不会立即写入硬盘
    - 会设置一个缓冲区,等缓冲区满了后,统一写入硬盘
    - 好处是提高效率,坏处是关机时,buffer的数据可能会丢失
4. halt 停机,关系统,不断电
5. poweroff 关机,断电
6. reboot
    - restart -r now
