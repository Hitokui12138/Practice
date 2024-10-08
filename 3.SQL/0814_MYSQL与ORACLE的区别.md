# 0814
- MYSQL与ORACLE的区别
    - 字段类型
    - 常用函数
        - 字符串处理,非空判断,时间计算
- 增删改查
- 复制,插入,比较
- IN,LIKE
## MYSQL与ORACLE
### 1. 数据库字段类型
1. MYSQL
    - 整数类型
    1. TINYINT
    2. INT
    - 字符类型
    3. CHAR(M),定长,固定长度8,用于身份证号,性别,电话等等,查询速度会快一些
    4. VARCHAR(M),变长,更加节省内存
    - 日期类型
    5. DATETIME, YY-MM-DD hh:mm:ss
    6. TIMESTAMP, YYYYMMDDhhmmss
2. ORACLE
    * MYSQL的也能用,不过更推荐用下面的
    - 数字类型
    1. NUMBER
    - 字符类型
    2. VARCHAR2(M)
    - 日期类型
    3. DATETIME, 和MYSQL基本一样
----
### 2. 引擎
- 5.0之前默认MyISAM,速度快,不支持事务,外键
- 现在默认推荐InnoDB
----
### 3. 文件分类
1. DML
2. DDL 定义语句
3. DCL 控制语句 授权,回滚,提交
### 4. 增删改查
```sql
-- 1. 增
INSERT into stu 
	VALUES(3,'桥本环奈','女',25), -- 写一个VALUES就够了
	(4,'泽尻绘理美','女',25),
	(5,'星野源','男',25);
INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....);
-- 2. 删
DELETE FROM stu1; -- 不用*
TRUNCATE stu1; -- TRUNCATE 相当于把表删了重建,不能加WHERE条件,自增键之类的也会重置
-- 3. 改
UPDATE stu SET age = 26, sex = '女' WHERE id = 1;
-- 4. 复制
-- 4.1 INSERT INTO 搭配 SECLECT
CREATE TABLE stu1(
	id int,
	name varchar(20)
); -- 字段数量可以不一样
INSERT INTO stu1 SELECT id, name FROM stu;

-- 4.2 INSERT INTO 搭配 SECLECT
CREATE TABLE stu2 (SELECT id,name,sex FROM stu);
```
----
### 5. 常用
#### 1.特殊比较运算符
```sql
-- 在jdbc的时候记得要加单引号
BETWEEN'1982-01-01' AND '1985-12-31';
IN(7902,7566,7788);
```
#### 2.模糊匹配
```sql
LIKE '__l%'; -- 第三个字母是L,Windows不区分大小写
LIKE '%l%'; -- 相当于CONTAIN
LIKE 'MAN@_%'; -- 查询MAN_开头的,转义字符
```
#### 3. 运算优先级
- 注意: AND 的优先级大于 OR 不是从从左往右的顺序
```sql
WHERE (deptno = 10 OR deptno = 20) AND (sal >= 3000 AND sal <= 5000);
```
#### 4.
1. `INSTR(ename,'m')`返回位置,没有的话返回0
2. `TRIM('S' FROM 'SSMITH');` -- 比java高级,java只能去掉空格
----
### 6.常用函数MYSQL和ORACLE的区别
1. SUBSTR(str, index, len)
    1. MYSQL index从1开始计算 `SUBSTR(hiredate,3,2) = '81'`
    2. Oracle index从0开始计算 `SUBSTR(hiredate,3,2) = '1-'`
    3. 带INDEX的 2023-10-05
        - SUBSTRING_INDEX(hiredate,'-',2) = 2023-10
        - SUBSTRING_INDEX(hiredate,'-',-2) = 10-05
        - SUBSTRING_INDEX(SUBSTRING_INDEX(hiredate,'-',-1),'-',2) = 10
2. 非空判断
    - NULL和任何值计算结果都是NULL,因此需要非空判断
    1. MYSQL `IFNULL(comm,0)`
    2. Oracle `NVL(comm,0)`
3. 拼接字符串
    1. MYSQL 
        - `CONCAT(ename," 第一年的收入为: ",sal*6+sal*1.2*6)`
    2. ORACLE 
        - `ename||" 第一年的收入为: "||sal*6+sal*1.2*6`
4. 取得时间
    1. MYSQL 
        1. `NOW()`, 2023-08-14 16:07:20

    2. ORACLE 
        1. `SYSDATE`, 1-5月-1981, 注意格式
        ```sql
        WHERE hiredate BETWEEN '1-1月-1982' AND '31-12月-1983'
        ```
5. 日期格式化
    1. MYSQL
        - `DATE_FORMAT(hiredate,'%Y-%m-%d %H:%i%s')`
        - `YEAR(hiredate)`, 2023
    2. ORACLE
        - `to_char(SYSDATE, 'yyyy-MM-dd hh24-mm-ss')`
        - `extract(year from hiredate)`
5. 时间计算
    1. MYSQL
        1. 取得天数之差, `DATEDIFF(NOW(),hiredate)`
        2. 取得年,月之差, `TIMESTAMPDIFF(MONTH,hiredate,NOW())`
        3. 加减天数, `ADDDATE(hiredate,5)`
        4. 带类型, `DATE_ADD(hiredate,INTERVAL 1 YEAR)`
        5. 取得某月最后一天, `last_day(hiredate)` 直接给日期就行,不需要转月份, 1981-02-28
    