- https://plantuml.com/zh/sequence-diagram
## 1. 序列图
1. 基本用法
```plantuml
actor 用户 as A
database 数据库 as B
A -> B : 实线
note left : 用户调用数据库

A <-- B : 虚线
note right : 返回数据

B --> B : 
note right
1. 调用各种procedure
2. 自己指自己
end note
```
2. 参与者关键字(提供更多图形)
    1. actor角色
    2. boundary边界
    3. entity 实体
    4. database数据库
    5. conllections 集合
    6. queue 序列
3. as 可以给一个别名
    - 写逻辑时更好写
4. 信息注释
    - note left
    - note right
    - end note
5. 生命线
    - activate ++
    - deactivate --
    - destroy
        ```plantuml
        User -> 浏览器 : click
        activate 浏览器

        浏览器 -> B : request

        B -> C : work
        activate C
        destroy C
        B <- C : work done

        浏览器 <- B : response

        deactivate 浏览器
        User <- 浏览器 : display
        ``` 
    - 自动激活生命线
        - autoactivate
        - return
6. 举例
```plantuml
device -> boot ++: 上电
boot -> device ++: 初始化
return 完成

boot -> linux --++:入口点//控制权转移
linux -> device ++:初始化
return 完成

linux -> init ++: main()
    init -> gnome ++: main()
    init -> xserver ++: main()

```

# 流程图
1. 基础
    - start stop end
    - : ;
    ```plantuml
    start
    :Hello world;
    
    if(world is good?)then(yes) 
    :hello;
    else(no)
    :no hello;
    end if

    end
    ```
2. 重复 
    - repeat
    ```plantuml
    start

    repeat 
    :step1;
    :step2;
    repeat while(judge?)

    stop
    ```
3. switch
    ```plantuml
    @startuml
    start
    switch (测试?)
    case ( 条件 A )
    :Text 1;
    case ( 条件 B ) 
    :Text 2;
    case ( 条件 C )
    :Text 3;
    case ( 条件 D )
    :Text 4;
    case ( 条件 E )
    :Text 5;
    endswitch
    stop
    @enduml
    ```
4. 泳道图
    1. 使用||

        ```plantuml
        |Swimlane1|
        start
        :step1;
        |Swimlane2|
        :step2;
        :step3;
        |Swimlane1|
        :step4;
        end
        ```
    2. 复杂一些

        ```plantuml
        start
        if (2 Yellow Card) then (yes)
            :何もしない;
            stop
        (no)elseif (pt < 3?) then (yes)
            repeat
            :6時間ごとにWoFを回す;
            repeat while(pt > 3?)
            :何もしない;
            stop
        (no) elseif (欲張り？) then (yes)
            repeat
            :RG;
            repeat while(Freeze)
            :+1h/20min;
            :一生懸命Share Link;  
            stop
        else(no)
        endif
        :Pillory;
        stop
         ```
        
