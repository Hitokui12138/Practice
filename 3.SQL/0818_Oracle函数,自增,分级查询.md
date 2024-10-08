# 0818
- Oracle的函数
- 使用序列SEQUENCE实现自增
#### 1. Oracle 和 MYSQL的区别
- number,varcher2
- oracle 两个隔离级别 mysql 4个
- oracle SELECT后面需要跟FROM,可以用虚拟表dual
- oracle sysdate, mysql NOW()
- COMMIT的自动与手动
#### 2. ORACLE的函数
1. 字符串函数
```sql
SELECT INITCAP('hello world') FROM dual; -- 只把第一个字母大写
SELECT ename FROM emp WHERE ename = 'smith'; -- ORACLE在WINDOWS平台区分大小写
SELECT ename FROM emp WHERE ename = UPPER('smith');
SELECT INSTR('hellllo','l',4,2) FROM dual;
```
2. 数值函数
3. 日期函数
```sql
SELECT sysdate - hiredate FROM emp;
-- 2.查询部门10,20的员工截止到2000年1月1日，工作了多少个月，入职的月份。(提示：使用months_between,extract)
SELECT MONTHS_BETWEEN('1-1月-2000',hiredate), EXTRACT(MONTH FROM hiredate)FROM emp WHERE deptno IN(10,20);
-- 3.如果员工试用期6个月，查询职位不是MANAGER的员工姓名，入职日期，转正日期，入职日期后的第一个星期一,入职当月的最后一天日期。（提示：使用add_months,next_day,last_day)
SELECT ename,hiredate,ADD_MONTHS(hiredate,6),NEXT_DAY(hiredate,'星期一') ,LAST_DAY(hiredate) 
    FROM emp a
    WHERE a.empno NOT IN(SELECT DISTINCT mgr FROM emp WHERE mgr IS NOT NULL);
```
4. 显式转换
    1. TO_INT,没什么用,隐式转换可以替代这个
    2. -- TO_CHAR, 日期的格式化,金额的格式化
        ```sql
        to_char(sysdate,'YYYY-MM-DD HH24:mi:mm')
        TO_CHAR(sysdate,'DAY')
        TO_CHAR(sal,'L999,999.99') -- ￥999,999.99
        trunc(sysdate - to_date('2015-3月-18 13:13:13','yyyy-mm"月"-dd hh24:mi:ss')) as 相差的天数
        ```
    3. -- TO_DATE, 解析一个日期
        ```sql
        to_date('2015-3月-18 13:13:13','yyyy-mm"月"-dd hh24:mi:ss') as 日期
        ```

3. 序列 SEQUENCE
- 因为Oracle没有AUTO_INCREAMENT,为了实现自增需要用到序列
```sql
-- 序列的一些参数
CREATE SEQUENCE seq_users2
START WITH 5 -- 第一次循环从5开始 5,10,15,20,3,8,13
MINVALUE 3 -- 超出最大范围后,循环的第二次开始初始值为3
MAXVALUE 22
INCREMENT BY 5
CYCLE
NOCACHE; -- 默认缓存,这里关掉缓存
```

```sql
SELECT seq_emp. from dual;

INSERT INTO t_user VALUES (seq_userid.nextval, 'bill', '222', '0', null, null, null);
```

4. GUID(),字符串主键
```sql
CREATE table users1(
    id varchar2(40) DEFAULT SYS_GUID() primary key,  -- 32位
    name varchar2(20) not null
);
```

4. 分级查询
- 分级查询,分层查询 ORACLE专用的
- emp是一个树形结构,一个Boss->多个经理->多个员工
```sql
-- 从上往下
SELECT empno, ename, job, mgr, LEVEL
    FROM emp
    START WITH empno = 7839 -- 先给一个起始的根节点
    CONNECT BY PRIOR empno = mgr; -- 指定存在父与子行的关系列,那STARTWITH的empno = 其他行的mgr
```
- 注意`CONNECT BY PRIOR  mgr = empno;`,这里等号两边的顺序很重要,现在时从下往上