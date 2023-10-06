1. Group BY 多个项目
    - GROUP BY gender,university;
2. COUNT()
    1. COUNT()里面是可以使用DISTINCT的
        - 不要看见平均就觉得是AVG,除以的参数可能不是总数量
        - 相同id当作一条数据
        ```sql
        -- 求平均回答数量,总数量/id数
        SELECT university,COUNT(1)/COUNT(distinct A.device_id) avg_answer_cnt FROM user_profile A
        JOIN question_practice_detail B ON A.device_id = B.device_id
        GROUP BY university
        ```
    2. COUNT(DISTINCT param1,param2)里面可以使用多个参数
        - 表示param1和param2任何一个参数不同就当作一条数据
3. MIN()表示全局最小,如果题问的是每个学校最小的话既不应该使用MIN
    1. 方法1:
        - 先用GROUP BY求得各个学校最小的值,再用一个SELECT,然后多列匹配
        ```sql
        SELECT device_id,university,gpa
        FROM user_profile
        WHERE
            (university,gpa) IN (
                SELECT university,MIN(gpa)
                FROM user_profile
                GROUP BY university
            )
        ```
    2. 方法2: 窗口函数
        - 因为不想看到rownum,因此在外面再包一层SELECT
        ```sql
        SELECT
            b.device_id,
            b.university,
            b.gpa
        FROM
            (
                SELECT
                    ROW_NUMBER() OVER (
                        PARTITION BY
                            university
                        ORDER BY
                            gpa
                    ) rownum,
                    device_id,
                    university,
                    gpa
                FROM
                    user_profile
            ) b
        WHERE
            b.rownum = '1'
        ```
3. SUM()
    1. 计算答题结果为right的数量,不能用COUNT()因为COUNT计算的是总数量
        - SUM(IF(result='right',1,0))
3. UNION
    1. UNION,会删除重复的
    2. UNION ALL,两张表连在一起,不会删除重复的
    3. 一种特殊的用法
        ```sql
        -- 计算25岁以上和以下的用户数量
        SELECT '25岁以下' age_cut,COUNT(1) number FROM user_profile
        WHERE age < 25 OR age IS NULL
        UNION
        SELECT '25岁及以上' age_cut,COUNT(1) number FROM user_profile
        WHERE age >=25

        -- 另一种不使用UNION的写法
        SELECT
            CASE 
                WHEN age < 25 OR age IS NULL THEN '25岁以下'
                WHEN age >= 25 THEN '25岁及以上'
            END age_cut,
            COUNT(1) number
        FROM user_profile
        GROUP BY age_cut
        ```
4. CASE WHEN
    1. 不能这样写 `WHEN 20 <= age <=24`
        - 必须`WHEN 20 <= age AND age <=24`

5. MYSQL的时间计算
    1. TIMESTAMPIDFF(YEAR,date,NOW())
    2. YEAR(),MONTH(),DATE()
## SQL难题
1. 计算用户的平均次日留存率
    1. 连续两天的数量/总数
        1. 怎么判断两天连续? 前一个date - 后一个date = 1
        2. 为了让一列的数据进行计算,应该笛卡尔积连接A1B1,A1B2,A1B3
        3. 计算每一行的日期差,得到差值为1的就是连续两天