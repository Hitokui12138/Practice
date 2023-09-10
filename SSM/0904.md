- 批量删除
- LocalStorage SessionStorage
- maven服务器
    - 各个结构作用,POM,JAR,WAR
    - 构建指令,生命周期

- 0905 
 - 发布jar包
## Maven
### 为什么MAVEN
1. 传统JAVAWEB的缺点:
    1. JAR包要自己去导入,有的JAR包之间有各种依赖关系,不应该自己去到导入
    2. 像是SVN里面的二进制文件(JAR包)容易损坏
2. MAVEN
    1. 工作流程
        1. 使用POM文件(字符文件)记录需要的JAR包,上传只需要上传POM文件,不易损坏
        2. 先看本地仓库有没有,没有的话去远程仓库(或者镜像)拉取到本地仓库
        3. 第二个项目的JAR包直接从本地取得,减少冗余
    2. 使用G(包名),A(项目名),V(版本)来确定唯一一个JAR包
3. MAVEN的使用
    1. 安装MAVEN服务器
    2. 配置Maven,打开Setting.xml
        1. 配置本地仓库 localRepository
            - 也可以用默认的
        2. 配置镜像 mirrors
        3. 编译版本 profiles 默认打包版本是1.5?
    4. 建Maven项目,配置Eclipse和Idea
4. 配置Eclipse
    1. Windows -> Preferences -> Maven
        1. -> Installation ,配置本地Maven
            - 可以看到有一个Embedded的,自己Add本地下载好的那个
        2. -> User Settings ,使用本地的配置文件settings.xml
6. Maven是一个构建工具
    1. 清理
    2. 编译
    3. 测试
    4. 报告
    5. 打包
    6. 部署
7. 和Eclipse的区别
- eclispe是一次全部完成
- maven是可以一步一步调试的
## Maven项目
1. 对项目结构要求很严格,开发和测试严格分开
- Project
    - src 源码包
        - main 源码包
            - java .java文件目录
            - resource 资源文件目录
        - test 测试用的源码包
            - java
            - resource
    - target class文件,报告的存储位置
    - pom.xml maven工程的描述文件
2. 创建MAVEN项目
    1. 勾选(skip archetype)跳过骨架
    2. packing选哪个?(打包方式)
        1. JAR,普通的java项目
        2. POM,聚合工程,多个MAVEN项目组合在一起
        3. WAR,建网站要使用war包,能在tomcat里自动解压
## Maven项目的构建指令
- 
## MAVEN构建Web项目(Eclipse)
1. 跳过模板,选`war包`
    - main 源码包
        - webapp 多出来一个文件夹,但里面什么也没有
            - WEB-INF 使用下面的方法创建
                - web.xml
2. 创建`WEB-INF`
    - 右键项目 -> Java EE tools -> Generate DDS
    - 创建后pom也不报错了
3. 把之前做好的java文件JSP文件等等粘进去还是报错?
    - 原因是少了`Server Runtime[Tomcat]`包 
    1. 以前的解决方法,整个引进来
        1. 项目右键 -> proeprties
        2. -> java Build Path -> Library -> Add Library
        3. 选择 Server Runtime -> Tomcat
        4. 这样的缺点是一下子把所有的包都到到进来了
            - 现在只需要JSP和Servlet
    2. 另一种方法,使用pom文件
        - 把servlet-api和jsp-api引进来
        - 老师喜欢把servlet-api,jsp-api,jstl放一起
4. 可能还会遇到JRE报错
    1. Windows -> Preferences -> Java -> Installed JRE
        - 可以看到Eclipse在使用一个默认的JRE,但是我们需要JDK
        - 不要删除默认的JRE,选择ADD
    2. 仅Add -> StandardVM -> 选择JDK路径 -> 设为默认
    - 加号以后改当前项目
    1. 项目右键 -> proeprties -> java Build Path -> Library
    2. remove掉以前的JRE
    3. Add Library -> 选JRE System Library -> 选择默认

## Eclipse切换jdk
1. Windows -> Perference ->Java -> Installed JRES
    - 添加JDK
2. Java -> Compiler ->修改版本
3. maven项目默认1.8?
    1. 改maven配置文件
    2. 右键maven项目->buildpath->Javabuildpath->选中1.8,edit->WorkSpacedefault

## 创建War包后POM文件报错
1. 右键项目 -> Java EE tools -> Generate DDS
2. 加入下面这段代码

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.gsd</groupId>
  <artifactId>travel_eclipse</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>war</packaging>
  

	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-war-plugin</artifactId>
				<version>3.3.1</version>
			</plugin>
		</plugins>
	</build>
</project>
```

## 右键不能newclass文件
- windows->prespective->custom prespective->shortcut->勾选java
## 展开package