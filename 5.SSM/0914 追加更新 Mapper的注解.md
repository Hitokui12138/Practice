## Web2
- 不能
    - c:if本质上还是服务端在判断
    - 不是响应式的,删一条,只是删除一个DOM
## Wenb4 VUE
    - 从第三代语言开始可以在服务端进行if等判断
    - VUE就是响应式的,底层通过虚拟DOM实现,可以监听,发现虚拟DOM变了,可以将页面重绘

#
##
1. 模板字符串
2. EL表达式(在模板字符串里面要加个反斜线?)
## 按钮
1. 注册事件
2. 使用函数
3. Jquery的各种选择器
4. size()已经不能用了

##
- 单表的增删改查
- free mybatis tool 插件

## MyBatis动态SQL
1. 多条件查询
    - `<where><if>`
    - test里面是get方法
    ```xml
    <!-- 1. where -->
    SELECT * FROM
        <where>
            <if test="scename!=null and scename!=''">
                <!--scename like '%#{scename}%',当心识别成'%?%'-->
                AND scename like '%${scename}%'
            </if>
            <if test="scetypeid!=null and scetypeid!=0">
                    AND scetypeid = #{scetypeid}
            </if>
        </where>
    ```
2. 选择性添加景点
    - `<trim><if>`
    - 前缀后缀，去掉最后一个逗号(但这也意味着加的最后一个项目后面必须带逗号)
    ```xml
    <!-- 1. where -->
    INSERT INTO travle.t_scenery
        <trim suffixOverrides="," prefix="(" suffix=")">
            <if test="scename!=null and scename!=''">
                scename,
            </if>
        </trim>

        <trim suffixOverrides="," prefix="VALUES (" suffix=")">
            <if test="scename!=null and scename!=''">
                #{scename},
            </if>
        </trim>
    ```
3. 选择性修改景点
    - UPDATE专用的`set`,动态sql的`<set><if>`可以自动去掉内部最后一个逗号
    - 最后一个项目后面也一定要带逗号
    ```xml
    UPDATE travle.t_scenery
        <set>
            <if test="scename!=null and scename!=''">
                scename = #{scename},
            </if>
        </set>
    WHERE sceid = #{sceid};
    ```
4. 批量删除<foreach
    - collection表示遍历的的对象,可以是数组和List
    - 数组就写array,如果是List就写list
    - open遍历前添加内容
    - close遍历后添加内容
    - item:遍历项
    - separator:分隔符号,最后一个不加分隔符号
    ```xml
    <delete id="batchDelete">
        <foreach collection="array" open="DELETE FROM t_scenery  WHERE sceid IN (" close=")" item="sceid" separator=",">
            ${sceid}
        </foreach>
    </delete>
    ```

4. sql代码片段，把一段sql定义成一个变量
    - `<sql id="baseScenery">`<include refid="baseScenery"/>`
    ```xml
    <sql id="baseScenery">
        sceid,scename,scetypeid,context,pic,recommend,areaid,ticket,favorite,season,collection,sce_grade,location,start_time,tic_count
    </sql>

    <!--  sql代码片段,使用例  -->
    <select id="findSceneryBysceid" resultType="Scenery">
        select <include refid="baseScenery"/>
        from t_scenery
        WHERE sceid = #{sceid}
    </select>
    ```
5. Mapper里面的class的别名
    - beans里面
    ```xml
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--    5.mapper 文件class的别名    -->
        <!--    com.gsd.entity.Scenery ->  Scenery   -->
        <property name="typeAliasesPackage" value="com.gsd.entity"/>
    </bean>
    ```

## 映射器Mapper注解
- @Insert,@Update,@Delete,@Select
- @Results @Result

1. 查一条(带条件)
- @Select 的括号里还要写`select`
```java
// DAO接口
    @Select("select hotelid,hotelname,in_time,out_time,pic,hotelareaid,roomtypeid,context,collection,favorite,room_count,site,hotelphone,price FROM t_hotel WHERE hotelid = #{hotelid}")
    public Hotel findHotelByHotelid(Long hotelid);
```
2. 查多表
    1. 使用`@Results`注解表示ResultMap
        - 有id和value1两个属性
        - value是个@Result数组
            - property是entity的里定义的,column是数据库带下划线的
            - many/one,相当于`assosiation`的作用,many表示一对多查询
                - 这里也可以写one,one的时候如果查出来多条会报异常
        ```java
            //1.选择所有
            //用注解来写
            //@Select("select hotelid,hotelname,in_time,out_time,pic,hotelareaid,roomtypeid,context,collection,favorite,room_count,site,hotelphone,price FROM t_hotel")
            @SelectProvider(type = HotelSqlBuilder.class, method = "builderFindAll")
            @Results(id="HotelMap2", value={
                @Result(property="hotelarea",column = "hotelareaid",many = @Many(select = "com.gsd.dao.HotelAreaDao.findHotelAreaById")),
                @Result(property="roomtype",column = "roomtypeid",many = @Many(select = "com.gsd.dao.RoomTypeDao.findRoomTypeById"))
            })//表示resultmap,默认也是自动映射,@Many表示多对一
            public List<Hotel> findAll2(Hotel hotel);
        ```
        - 对比以前的ResultType
        ```xml
            <resultMap id="HotelMap" type="com.gsd.entity.Hotel">
                <!--    子表1    -->
                <association property="hotelarea" column="hotelareaid" select="com.gsd.dao.HotelAreaDao.findHotelAreaById"></association>
                <!--    子表2    -->
                <association property="roomtype" column="roomtypeid" select="com.gsd.dao.RoomTypeDao.findRoomTypeById" ></association>
            </resultMap>
        ```
    2. 选择性查询与`@SelectProvider`
        - 把SELECT语句写在@Select里面太乱了,可以再建一个类专门写Select语句.还能实现选择性查询
        - `@SelectProvider(type = HotelSqlBuilder.class, method = "builderFindAll")`需要提供类type和方法method
        - 使用springcore提供的`StringUtils.isEmpty()`方法简化`!=null`
        ```java
        public class HotelSqlBuilder {
            public String builderFindAll(final Hotel hotel){
                String sql = new SQL() {{   //两个大括号?
                    SELECT("hotelid,hotelname,in_time,out_time,pic,hotelareaid,roomtypeid,context,collection,favorite,room_count,site,hotelphone,price");
                    FROM("t_hotel");
        //            if (hotel.getHotelname() != null && !hotel.getHotelname().equals("")) {
        //                WHERE("hotelname like '%${hotelname}%'");
        //            }
                    //spring core提供的工具类,可以简化写法
                    //注意这里第一条不能写AND()
                    if(StringUtils.isEmpty(hotel.getHotelname())){
                        WHERE("hotelname like '%${hotelname}%'");
                    }
                    if (StringUtils.isEmpty(hotel.getHotelareaid())) {
                        AND();
                        WHERE("hotelareaid = #{hotelareaid}");
                    }
                    ORDER_BY("hotelid desc");
                }}.toString();
                System.out.println("builderFindAll:" + sql);
                return sql;
            }
        }
        ```

