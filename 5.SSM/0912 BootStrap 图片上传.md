# JSP -> Java
## bootstrap应用
1. 模态框
    1. 结构
        1. 外三层:必须,设置外观样式
            1. 动画和索引
                1. class="modal fade" fade淡入淡出
                2. tabindex="-1" 不要动
                3. role="dialog" aria-labelledby="myModalLabel" 默认用
        2. 设置外观,大小
            1. modal-lg 变宽
        3. 设置内容 modal-content必须
            - 内三层:可选,设置内容
            1. modal-header
            2. modal-body
            3. modal-footer
    2. 使用
        ```html
        <!-- 打开modal -->
            <button type="button" class="btn btn-primary btn-default" data-toggle="modal" data-target="#sceneryModal">添加</button>
        <!-- 定义modal -->
            <div class="modal" id="sceneryModal" tabindex="-1">
                <!-- 。。。 -->
            </div>
        ```

2. 栅格化
    - 一行两个项目
    - row + col-md-6 
    ```html
    <!-- 第一行 -->
        <div class="row">
            <!-- 项目1 -->
            <div class="col-md-6">
                <div class="form-group"><!-- 给个外边距 -->
                    <div class="input-group">
                        <span class="input-group-addon">景点名称</span>
                        <input class="form-control" id="scename" name="scename" placeholder="景点名称">
                    </div>
                </div>
            </div>
            <!-- 项目2 -->
            <div class="col-md-6 ">
                <div class="form-group">
                    <div class="input-group">
                        <span class="input-group-addon">景点类别</span>
                        <select class="form-control" id="scetypeid" name="scetypeid">
                            <option value="">====请选择====</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    ```
3. 和bootstrap同种风格的alert，bootbox对话框
    - 下载并导入bootbox
    ```html
    <%--对话框插件--%>
    <script src="${pageContext.request.contextPath}/bootbox/bootbox.all.min.js"></script>
    <script>
        <!-- Ajax里面 -->
            success(data){
                //1.提示信息
                bootbox.alert({     //alert是同步的，可以确保按下确认前程序卡在这里，但bootbox异步对话框,用回调函数确保顺序
                    message: data.message,
                    size: 'small',
                    callback() {    //要在按钮点击‘后’callback
                        if(data.success){
                            //2.关闭窗口的方法
                            $('#sceneryModal').modal('hide')
                            //3.刷新主页面
                            location.reload()
                        }
                    }
                });
            }
    </script>
    ```
## 添加Senery(JSP->Java)
1. html
    1. 考虑到POST提交，多个按钮，因此按钮可以放在form外面
    ```html
    <form id="addScenery">
        <!-- 1.各种项目 -->
    </form>
    <!-- 2.按钮 -->
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="handleButton">保存</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
    </div>
    ```
    2. JS部分
        - 可以使用事件或者函数两种方法打开modal
        - show.bs.modal表示打开modal之后加载，其他事件看官网
        - 但这个`show.bs.modal`事件似乎会导致两次打开是同一个窗口,后面会改用另一种方法
        ```html
        <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
        <script>
        //1. 打开modal之后，初始化各个选项   
        $('#sceneryModal').on('show.bs.modal',function(){
            //加载类别
            $.ajax({
                url:'${pageContext.request.contextPath}/scetype/findAll',
                success(data){
                    data.forEach(function(scetype,i){
                        if('${param.scetypeid}' == scetype.scetypeid){
                            $('#scetypeid').append(`<option selected value="\${scetype.scetypeid}">\${scetype.scename}</oprtion>`)
                        }else{
                            $('#scetypeid').append(`<option value="\${scetype.scetypeid}">\${scetype.scename}</oprtion>`)
                        }
                    })
                }
            })
            //其他选项
        })

        //2. '保存'按钮,没有上传文件的时候，注册单击事件
        $('#handleButton').on('click',function(){
            //获取form表单,serialize直接序列化
            let data = $('#addScenery').serialize()
            //默认是get,绝对路径
            $.ajax({
                type:'post',
                url:'${pageContext.request.contextPath}/scenery/addScenery',
                data,   //序列化后的表格
                success(data){
                    //1.提示信息
                    bootbox.alert({ //异步对话框,alert是同步的
                        message: data.message,
                        size: 'small',
                        callback() {    //要在按钮点击后callback
                            if(data.success){
                                //2.关闭窗口,
                                $('#sceneryModal').modal('hide')
                                //3.刷新页面
                                location.reload()
                            }
                        }
                    });
                }
            })
        })
        </script>
        ```

## 修改添加Senery
- 修改modal也想共用添加的modal
- 两次打开之间需要清空数据
    - 以前使用脚本添加`data-toggle="modal" data-target="#sceneryModal"`,会导致每次打开都是同一个modal
        - ` $('#sceneryModal').on('show.bs.modal',function(){}`
    - 改成`onclick="openmodal()"`,通过方法打开,然后DOM有清除表单的功能
1. 添加
    - `&nbsp;` 表示空格
    1. 按钮改成onclick()事件,(在激活)
        ```html
        <button type="button" class="btn btn-primary btn-default" onclick="openModel()">
            <span class="glyphicon glyphicon-plus"></span>&nbsp;添加
        </button>
        ```
    2. 点击事件    
        ```javascript
        //点击`添加按钮`
        function openModel(){
            //1. 使用modal('show')打开模态框
            $('#sceneryModal').modal('show')
            //2. 修改lable和按钮的文字
            $('#sceneryModalLabel').html('添加景点')
            $('#handleButton').html('添加')
            //3. 重置表单
            $('#addScenery')[0].reset();//但好像不能清空隐藏的值
            scenery = null //手动清空景点对象
        }
        ```
    3.     
2. 修改
    1. 根据sceid获取景点,把success返回的数据先用一个全局变量保存起来
        - `let scenery`
        - 但异步往往会稍慢一些,导致下面的执行完了scenery也不一定有值
            - 用`async:false`强制同步
    2. 打开模态框
        - 每次打开模态框时,都会`append`追加选项,应该有一个清空选项机制
            - 每次foreach Append之前先清空一下`$('#scetypeid')[0].length=0`
    3. 回显
        - 修改值与修改属性的不同方法
        - 对于sceid这种不显示但是对更新很重要的值,需要一个隐藏控件

    4. 具体操作
        1. 修改按钮
            - 点击事件,把这一行的sceid传进去
            ```html
            <td>
                <button class="btn btn-info btn-sm" onclick="toUpdate(${scenery.sceid})">
                    <span class="glyphicon glyphicon-pencil"></span>修改
                </button>
            </td>    
            ```
        2. 点击事件
            ```javascript
            let scenery //1. 用一个全局变量接受一下sucess的返回结果
            //获取ID打开窗口
            function toUpdate(sceid){
                //1.取得Scenery
                $.ajax({
                    //2.只传一个值的话可以使用restful传值
                    url:`${pageContext.request.contextPath}/scenery/\${sceid}`, //模板字符串和EL表达式冲突了
                    async: false,   //没有执行完success,不让执行后面的方法,就是按顺序执行
                    success(data){
                        scenery = data
                    }
                })
                //console.log(scenery) //之前异步的时候可能在这里拿不到data

                //3.打开模态框
                $('#sceneryModal').modal('show')

                //4.数据回显,打开模态框之前是没有用的
                $('#sceid').val(scenery.sceid)
                $('#scename').val(scenery.scename)
                $('#scetypeid').val(scenery.scetypeid)
                $('#context').val(scenery.context)
                $('#ticket').val(scenery.ticket)
                $('#areaid').val(scenery.areaid)
                $('#season').val(scenery.season)
                $('#sceGrade').val(scenery.sceGrade)
                $('#location').val(scenery.location)
                $('#startTime').val(scenery.startTime)
                $('#ticCount').val(scenery.ticCount)
                if(scenery.pic!=null){
                    $('#preview').prop("src",'/'+scenery.pic)   //变成绝对路径
                }
                $('#sceneryModalLabel').html('修改景点')
                $('#handleButton').html('修改')

            }
            ```
3. 添加和修改共用的保存按钮
    - 所调用的URL不一样,可以根据scenery是否为空来判断
    1. 按钮不变
        ```java
        <button type="button" class="btn btn-primary" id="handleButton">保存</button>
        ```
    2. 主要修改一下之前的URL,根据scenery是否为空来判断
        ```javascript
        $('#handleButton').on('click',function(){ 
            let data = new FormData($('#addScenery')[0])
            $.ajax({
                type:'post',
                url:`${pageContext.request.contextPath}/scenery/\${scenery==null?'addScenery':'updateScenery'}`,
                data,
                processData:false,  //不自动处理data成字符串
                contentType:false, //设置成false可以自动检测data类型,multipart/form-data
                success(data){
                    //1.提示信息
                    bootbox.alert({ //异步对话框,alert是同步的
                        message: data.message,
                        size: 'small',
                        callback() {    //要在按钮点击后callback
                            if(data.success){
                                //2.关闭窗口,
                                $('#sceneryModal').modal('hide')
                                //3.刷新页面
                                location.reload()
                            }
                        }
                    });
                }
            })
        })
        ```    


## 上传文件
1. 顺序
    1. 先上传到TomCat的`Temp文件夹`里面,请求完之后立即会被清理掉
    2. 因此要在上传文件之后立即保存到以下地方
        1. 在项目本地建一个目录(但是不能手动建,不然下次部署就没了)
        2. 阿里云等额外一个服务器(对象存储OOS)
    3. 将文件的路径保存在数据库里

2. 配置工作
    1. 注册文件上传解析处理器(没有处理MultipartFile的配置) SpringMVC.xml
        - 在SpringMVC中配置文件上传解析器`multipartResolver`
    2. 解析处理器本身还需要两个JAR包,导一个有依赖的就行
        - 文件上传需要`commons-fileupload`JAR包,这个是`apache提供`的

2. 普通的文件上传(客户端)
    - 具体处理要分为`客户端`和`服务器`两部分
    - 上传又分为`同步`和`异步两种
    2. `同步上传`
        1. 客户端
            - 使用表单,且请求方式必须为`POST`,否则只能传文件名
                - 因为默认的ContentType是`form-urlencodes`,表单域,不能传文件
            - enctype 属性指定将数据回发到服务器时浏览器使用的编码类型
            - 传输文件必须使用`enctype="multipart/form-data"`
                - 二进制
            ```html
            <!--1.客户端-->
            <h1>同步上传</h1>
            <form action="up" method="POST" enctype="multipart/form-data">
                文件:<input type="file" name="file"><br>
                <button>文件上传</button>
            </form>
            ```
        2. 查看pyload里面的二进制流,火狐浏览器看的更明显一些
    3. `异步上传`
        - 还是通过form上传,不需要标明enctype="multipart/form-data"
            - 因为是在Ajax里面标注enctype的
        ```html
        <h1>异步上传</h1>
        <form id="form">
            用户名:<input name="username"><br>
            文件:<input type="file" name="file" id="file"><br>
            <button type="button" id="btn">文件上传</button>
        </form>
        ```
        - 主要是现在增加景点的form是个异步上传
        1. 修改后
            - 主要修改三个点
            1. ES5提供的存放上传文件的对象,使用`new FormData($('#addScenery')[0])`去接收form的DOM元素
                - FormData对象本质上是个map
            2. `processData:false`,  //默认为true会把data转换成字符串,内置的转换器
            3. `contentType:false`, //设置成false可以自动检测数据类型,multipart/form-data
            ```javascript
            $('#handleButton').on('click',function(){
                //let data = $('#addScenery').serialize() 文件本身显然不能通过`serialize()`去序列化
                let data = new FormData($('#addScenery')[0])
                $.ajax({
                    type:'post',
                    url:'${pageContext.request.contextPath}/scenery/addScenery',
                    data,
                    processData:false,  //不自动处理data成字符串
                    contentType:false, //设置成false可以自动检测data类型,multipart/form-data
                    success(data){
                        //1.提示信息
                        bootbox.alert({ //异步对话框,alert是同步的
                            message: data.message,
                            size: 'small',
                            callback() {    //要在按钮点击后callback
                                if(data.success){
                                    //2.关闭窗口,
                                    $('#sceneryModal').modal('hide')
                                    //3.刷新页面
                                    location.reload()
                                }
                            }
                        });
                    }
                })
            })
            ```

## 服务端处理上传文件
- 同步上传异步上传都一样
- 发现form表单的格式是`multipart/form-data`,这种类型需要文件解析器
1. 在Controller中,使用`MultipartFile`和`HttpServletRequest`接收参数
    - `MultipartFile`会把物理文件解析成一个对象
        - file.getOriginalFileName(),获取原始文件名
        - file.isEmpty(),判断文件是否为空
    - `HttpServletRequest`用于获取当前服务器路径,用于创建文件夹
    ```java
    //1. Controller接收参数,调用Uitl方法处理文件
    @PostMapping("up")
    public String up(MultipartFile file, HttpServletRequest request) throws IOException {
        FileUtils.up(file, request);
        return "index";
    }
    ```
2. 创建一个FileUtil来处理各种上传文件
    - 如果所有文件放到一个文件夹下,IO会很慢,让目录变成一个`时间戳`的目录
        - 导入时间处理工具类`joda-time`JAR包
            - `DateTime()`
    - 为了避免文件名重复,用`UUID`修改每个上传文件的文件名
        - UUID 生成唯一文件,20万
    ```java
    //2. 自己建一个FileUtil处理所有的上传文件
    public static String up(MultipartFile file, HttpServletRequest request) throws IOException {
        //1. 文件为空时直接返回
        if(file.isEmpty()) return null;

        //文件
        //2.为了避免文件名重复导致源文件被覆盖,使用UUID生成文件名
        //去掉'-'后当作文件名用
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //3.取得后缀名,使用导入的commos包的工具类,但注意这个不带".",也可以自己去取
        String extension = FilenameUtils.getExtension(originalFilename);

        //路径
        //1. 通过request获取当前服务器路径
        String uploadPath = request.getRealPath("upload/");//获取服务器路径,路径可以先不存在
        //2.可以借助日期工具类去设置文件名 joda-time 
        String datePath = new DateTime().toString("yyyy/MM/dd/HH");
        uploadPath = uploadPath + datePath;
        //3.创建文件夹
        File uploadFile = new File(uploadPath);
        if(!uploadFile.exists()){
            uploadFile.mkdirs();//路径不存在时创建路径
        }

        //把上传的文件复制文件到这个路径, temp->upload
        file.transferTo(new File(uploadFile,uuid +'.'+extension));
        return "upload/"+datePath +'/'+ uuid +'.'+extension;//返回路径+文件名用于保存DB
    }
    ```

## 上传文件控件与图片预览
- 浏览器默认的上传控件太丑了,且在不同浏览器上长的也不一样
    ```html
    <form action="up" method="POST" enctype="multipart/form-data">
        文件:<input type="file" name="file"><br>
        <button>文件上传</button>
    </form>
    ```
- 思路:
    1. 隐藏原始控件,在外面显示一个按钮,按下按钮后,调用被隐藏的上传文件控件
        - 在没有上传图片之前,用一个默认图片占据整这个空间
        ```html
        <div class="input-group">
            <!-- 1. 用一个按钮和图标来调用控件  -->
            <button class="btn btn-info" id="fileBtn" type="button">
                <span class="glyphicon glyphicon-cloud-upload">上传图片</span>
            </button>
            <!-- 2. 隐藏原始上传控件控件, class="hidden" -->
            <input type="file" name="file"  class="hidden" id="file" >
            <!-- 3. onerror: src没有的时候显示一个默认图片 -->
            <img src="" class="img" onerror="this.src='${pageContext.request.contextPath}/images/DefaultHotel.jpg'" id="preview">
        </div>
        ``` 
    2. 点击按钮后激活上传控件
        - 注册一个点击事件, `trigger()`方法触发被选元素上指定事件以及事件默认行为（比如表单的提交）
        ```javascript
        $('#fileBtn').on('click',function(){
            $('#file').trigger('click')//注意这里激活上传控件的方法
        })
        ```
    3. 上传图片后使用新图片替换旧图片
        1. 改变文件后,触发`change`事件,创建一个FileReader对象
            - ES5提供的用来做文件预览的
        2.  `fr.readAsDataURL(this.files[0])`使用这个方法去加载图片
            - `files[]`似乎是个数组
            - 加载好的图片会被放在`fr.result`里面,使用`base64`编码成的字符串
            - base64指的是把图片转成字符串,只适合小的图片
        3. 加载完成后的事件,使用
        ```javascript
        $('#file').on('change',function(){
            //1.创建fileReader对象
            let fr = new FileReader();
            //2.加载图片
            //console.log(this.files[0])//是个数组?
            fr.readAsDataURL(this.files[0])//加载图片,其他方法和含义去看MDN网站
            //3.注册加载完后的事件,替换img的src
            fr.onload = function (){
                //console.log(fr.result)//结果会被保存result里面
                $('#preview').prop('src',fr.result)
            }
        })
        ```
        4. 根据表格里的图片路径预览图片
        ```html
        <td>${hotel.pic}<img class="img" src="${pageContext.request.contextPath}/${hotel.pic}" onerror="this.src='${pageContext.request.contextPath}/images/DefaultHotel.jpg'"></td>
            
        ```
    