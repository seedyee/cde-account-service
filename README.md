# 项目名：account-service

##一、功能介绍
 - 提供注册用户接口服务
 - 查询用户基本信息接口服务
 - 修改用户信息接口服务
 - 获取用户邮箱信息接口服务
 - 修改用户信息接口服务
 - 获取用户电话信息接口服务
 - 修改用户电话信息接口服务
 
##二、开发环境
 - 本项目使用maven管理项目依赖
 - 本项目使用Spring Boot进行开发，依赖cde-parent项目
 - 本项目使用的jdk版本为java8
 - 数据库使用MongoDB
 - 数据缓存使用Redis

##三、参数配置
开发环境访问端口文件、MongoDB数据库参数配置、Redis参数配置等配置信息在resources/application.yml文件中

##四、启动打包和测试访问

###第一步

 - 运行 mongod命令开启MongoDB
 - 运行 redis-server命令开发Redis

###第二步
 - 进入cde-parent项目目录
 - 运行 mvn clean install 下载依赖

###第三步
 - 进入cde-account-service项目目录
 - 运行 mvn clean install 下载依赖
 - 运行 mvn clean package 打包
 - 运行 mvn spring-boot:run 运行
 - 测试访问：注册用户地址：http://localhost:8080/accounts  

注：打包以后的文件存放在target/cde-account-0.1.0-SNAPSHOT.jar
