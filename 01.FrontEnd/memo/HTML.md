## 豆知识
1. 首页html文件一般是index.html
## HTML
1. 标签语言
    1. 开始标签,结束标签<tageName></tagName>
    2. 自闭合标签<tagName/>
        1. html5可以不写自闭合标签最后的/ 
        2. 一些天生就是自闭合的标签
            1. <br>
            2. <hr>
            3. <input>
2. HTML文件结构
    1. 直接用!生成即可
    2. 手打
        ```html
        <!DOCTYPE html>
        <html>
            <head>
                <title></title>
            </head>
            <body></body>
        </html>
        ```
3. 常用标签
    - 这些标签自带默认的样式,可以通过css修改
    1. 标题标签h
        - <h1><h2><h6>
    2. 段落标签p
        - <p>Lorem</p>
        - 不同
        1. 加粗与斜体
            1. <strong>
            2. <em>
    3. <h><p>
        1. 用户代理样式表,User Agent Style Sheet
            - 表示浏览器提供给这个标签的默认样式
        2. 两个标签都有
            - display: block;
    4. <a href=""></a>
        - hyper text refrence
        1. target属性
            1. 默认不写,在原页面打开
            2. _blank,新标签打开
    5. 列表标签
        1. 无序ul,li
        2. 有序ol,li
    6. 表格table
    7. 表单form
        1. <lable>
        2. <input type>
        3. 都是行级元素,因此会排在一行里
            1. 为了纵向排列,可以在外面包一个<div>
        4. textarea
        5. select,option
            - value属性
    8. 按钮,button
    9. 图片,img,自闭合,行级
        - src,可以是本地也可以是网络
        - alt,图片的备选文本,加载不出来时显示这个
    
4. 行级元素,块级元素
    1. 行级元素display: inline
        1. 行级元素之间不会新起一段
        2. 占用空间与元素大小本身有关
        3. span,img,a,strong,em
    2. 块级元素display:block
        1. 会新起一段
        2. 并占用当前行100%宽度
        3. div,h,p,form
5. 列表标签和