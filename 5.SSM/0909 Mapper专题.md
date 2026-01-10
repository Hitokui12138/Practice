# DTO
1. 基本类型不能设为null,但数据库里有空
2. 多对一,Scenery
    - private Scetype scetype;
    - private Area area;
3. 一对多,Scetype
    - private List<Scenery> sceneries;

# 一般的多表查询
1. 需要标明数据库字段和java字段的联系
    - column是数据库字段,property是java字段
2. `<select>`
    1. `parameterType`表示参数类型,如果是int之类的可以不写
    2. `resultType`表示返回类型
        1. `resultType`自动映射,属性名和表字段`一致`的情况
        2. `resultMap`自定义映射,
            - 属性名和表字段`不一致`的情况
            - 一对多或者多对一的情况
    3. 注意select的Dao应该返回一个`List<entity>`
        - TooManyResultException
```xml
<!-- 主表 -->
<resultMap type="com.example.demo.entity.MRoom" id="room">
    <id column="id" property="id"></id>
    <result column="room_name" property="roomName"></result>
    <result column="created_at" property="createdAt"></result>
    <collection property="roomUserList" resultMap="roomUser"><collection>
</resultMap>
<!-- 分表 -->
<resultMap type="com.example.demo.entity.TRoomUser" id="roomUser">
    <id column="id" property="id"></id>
    <result column="room_id" property="roomId"></result>
    <result column="current_user_id" property="currentUserId">
</resultMap>
<!-- 查询 -->
<!-- select必须有resultType或者resultMap -->
<select id="findLoginUserRooms" resultMap="room">
        select
            room_name
        from rooms r
        inner join room_users ru
            on r.id = ru.room_id
        inner join users u
            on ru.current_user_id = u.id
        where
            ru.current_user_id = #{currentUserId,jdbcType=INTEGER}
    </select>
```

# 1. 查询(开启驼峰映射后?)
1. 查单表,添加数据
    1. `<mapper>`namespace表示Dao类
    ```xml
    <mapper namespace="com.gsd.dao.EmpDao">        
        <select id="findAll" resultType="com.gsd.entity.Emp">
            select empno,ename,job,mgr from emp
        </select>
    </mapper>
    ```
2. 查多表
    - 类似于相关子查询,先查主表,再根据主表的每一行去查分表
    1. 在`<resultMap>`上设置
        1. `type`: 代替之前的`parameterType`,表示返回的主表的DTO类型
    2. 在`<association>`上设置
        1. `property`: 子表的DTO类型
        1. `select`: 子查询的id,跨包查询要带上路径
        2. `column`: 主表给子查询传的参数
            - 子表.XXX = 主表2.#{column}
    1. 主表的Mapper文件
        ```xml
        <!-- SceneryMapper.xml -->
        <!-- 1.只查主表,不要使用JOIN -->
        <select id="findAll" resultMap="sceneryMap">
            select sceid,scename,scetypeid,context,pic,recommendareaid,ticket,favorite,season,collection,sce_gradelocation,start_time,tic_count
            from t_scenery
        </select>
        <!-- 2. 手动映射表,select和column -->
        <resultMap id="sceneryMap" type="com.gsd.entity.Scenery">
            <!-- 聚合属性的property,column是传给聚合属性的select的参数-->
            <association property="scetype" select="com.gsd.daoScetypeDao.findScetypeBytypeId" column="scetypeid">
                <id property="scetypeid" column="scetypeid"/>
                <result property="scename" column="scename"/>
            </association>
        </resultMap>
        ```
    2. 分表的Mapper文件
        - 子查询的语句
        - 可以把mapper看作一个类,select是里面的方法
        ```xml
        <!-- 跨包调用时要注意namespace别写错 -->
        <mapper namespace="com.gsd.dao.ScetypeDao">
            <!--  这个方法只是在mapper里面用的话,可以不写interfaceScetypeDao的方法  -->
            <select id="findScetypeBytypeId" resultType="com.gsdentity.Scetype" parameterType="int" >
                select scetypeid, scename from t_scetype WHERE scetypeid = #{scetypeid}
            </select>
        </mapper>
        ```s
## 一对一
- private Scetype scetype;
- private Area area;
1. 查景点主表和Type名称
    1. 主表(有外键的是主表)
    - 关键在于在scenery里面定义两个聚合属性`Scetype scetype`和`Area area`
        ```xml
        <!-- 1.主表findAll() -->
        <select id="findAll" resultMap="sceneryMap">
            select sceid,scename,scetypeid,context,pic,recommend,areaid,ticket,favorite,season,collection,sce_grade,location,start_time,tic_count
            from t_scenery
        </select>

        <!-- 2.主表映射表，返回类型为主表entity -->
        <resultMap id="sceneryMap" type="com.gsd.entity.Scenery">
            <!--    子表1  property聚合属性(本质是setter())，column连接属性， 自动映射，不需要写property  -->
            <association property="scetype" select="com.gsd.dao.ScetypeDao.findScetypeBytypeId" column="scetypeid"></association>
            <!--    子表2  id主键， result其他项目  -->
            <association property="area" select="com.gsd.dao.AreaDao.findAreaByareaId" column="areaid">
                <!-- <id property="areaid" column="areaid"/>
                <result property="areaname" column="areaname"/> -->
            </association>
        </resultMap>
        ```
    
    2. 子表
        1. 子表1
        ```xml
        <!-- 3. 子表findById(),返回结果为子表entity，parameterType可以不写 -->
        <select id="findScetypeBytypeId" resultType="com.gsd.entity.Scetype" parameterType="int" >
            select scetypeid, scename from t_scetype WHERE scetypeid = #{scetypeid}
        </select>
        ```
        2.子表2
        ```xml
        <select id="findAreaByareaId" resultType="com.gsd.entity.Area" parameterType="int">
            SELECT areaid, areaname from t_area WHERE areaid = #{areaid}
        </select>
        ```

## 一对多
- private List<Scenery> sceneries;
1. 查询所有type和对应的景点
    - 关键在于要在type类里定义`List<Scenery> sceneries`
    1. 主表
        - 查询时发现主表的`scename`莫名变为null，因此在主表映射表上又写了一遍
        ```xml
        <!-- 1.主表findAll() -->
        <select id="findAll" resultMap="ScetypeMap" >
            select scetypeid, scename from t_scetype;
        </select>

        <!-- 2.主表映射表 -->
        <resultMap id="ScetypeMap" type="com.gsd.entity.Scetype">
            <!--    scename为null时的处理    -->
            <id property="scetypeid" column="scetypeid"/>
            <result property="scename" column="scename"/>
            <!--    子表，聚合属性sceneries是Type里定义的景点列表List<Scenery>    -->
            <association property="sceneries" select="com.gsd.dao.SceneryDao.findSceneryBytypeid" column="scetypeid">
                <!--      可以自动映射      -->
            </association>
        </resultMap>
        ```
    2. 子表
        ```xml
        <select id="findSceneryBytypeid" resultType="com.gsd.entity.Scenery">
            <!-- 3.子表findById() -->
            select sceid,scename,scetypeid,context,pic,recommend,areaid,ticket,favorite,season,collection,sce_grade,location,start_time,tic_count
            from t_scenery
            WHERE scetypeid = #{scetypeid}
        </select>
        ```
----
# 动态SQL
1. 两种获取数据的方法
    1. 仅一个参数的时候,或者直接传一个Entity的时候
        1. OGNL表达式 #{get方法}，底层使用PreparedStatement接口，先生成？，再替换
            - 字符串和日期会自动帮你加单引号
        2. ${} == Statement 接口 字符串拼接
            - 可能需要手动加单引号,`'${userName}'`
    2. 多个参数(不是一个entity),会自动整合在一个Map里面
        1. 以arg0,arg1为值,`从0开始`
        2. 以param1,param2为值,`从1开始`
        ```xml
        <!-- func(String id, String name) -->
        <select id="login" resultType="User">
            select * from t_user
            where
            userid = #{arg0} and username = #{arg1}
        </select>
        ```
    3. 多个参数,但是使用`@Param("XXX")`
        - 本质也是map,只不过给每个key起了名字
        ```xml
        <!-- func(@Param("id") String id, @Param("name") String name) -->
        ```
    4. 直接把多个参数提前放到map里面,可以直接使用key取值
        ```xml
        <!-- func(Map<String, Object> map) -->
        #{id},#{name}
        ```
    

1. 增删改
    - insert delete update 标签可以混用
    ```xml
    <!-- 增 -->
    <insert id="addEmp" parameterType="com.gsd.dao.EmpDao">
        INSERT INTO emp VALUES (0, #{ename}, #{job}, #{mgr}, now(), #{sal}, #{comm}, #{deptno}, 1)
    </insert>
    <!-- 改 -->
    <update id="updateEmp" parameterType="com.gsd.dao.EmpDao">
        update emp set ename=#{ename},job=#{job},mgr=#{mgr} WHERE empno=#{empno}
    </update>
    <!-- 删 -->
    <delete id="deleteById">
        DELETE FROM t_scenery WHERE sceid = #{sceid}
    </delete>
    ```
2. `<where><if>`,条件查询
    1. where标签
        1. 不写where标签的话默认`where 1=1`
        2. 需要和if标签一起使用
    2. if标签
        1. 会自动去掉第一个`AND`
    ```xml
    <select id="findAll" resultMap="sceneryMap">
        select sceid,scename,scetypeid,context,pic,recommend,areaid,ticket,favorite,season,collection,sce_grade,location,start_time,tic_count
        from t_scenery
        <where>
            <!--1. 与LIKE结合使用,避免 LIKE ? -->
            <if test="scename!=null and scename!=''">
                <!--scename like '%#{scename}%',当心识别成'%?%'-->
                <!-- 第一个项目也要带 AND -->
                AND scename like '%${scename}%'
            </if>
            <!-- 2. 一般用法 -->
            <if test="scetypeid!=null and scetypeid!=0">
                AND scetypeid = #{scetypeid}
            </if>
            <!-- 3. 表格没有的数据,往entity里加两个属性或者创建一个新的VO类去继承entity-->
            <if test="lowprice!= null and highprice!= null">
                AND ticket BETWEEN #{lowprice} AND #{highprice}
            </if>
        </where>
        order by sceid desc
    </select>
    ```
3. `<set>`,在条件insert时,避免使用trim去掉逗号
    - 可以自动去掉最后一个逗号
    ```xml
    <update id="updateScenerySelective">
        UPDATE travle.t_scenery
        <set>
            <if test="scename!=null and scename!=''">
                scename = #{scename},
            </if>
            <!-- 最后一个项目后面也一定要带逗号 -->
            <if test="ticCount!=null">
                tic_count = #{ticCount},
            </if>
        </set>
        WHERE sceid = #{sceid};
    </update>
    ```
4. `<foreach>`批量操作
    1. collection表示遍历的的对象,可以是数组和List,数组就写array,如果是List就写list
    2. open遍历前添加内容
    3. close遍历后添加内容
    4. item:遍历项
    5. separator:分隔符号,最后一个不加分隔符号
    ```xml
    <delete id="batchDelete">
        <foreach collection="array" open="DELETE FROM t_scenery  WHERE sceid IN (" close=")" item="sceid" separator=",">
                ${sceid}
        </foreach>
    </delete>
    ```
    