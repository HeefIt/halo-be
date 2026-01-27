# Halo 后端项目

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5.0-green" alt="Spring Boot Version">
  <img src="https://img.shields.io/badge/Java-17-blue" alt="Java Version">
  <img src="https://img.shields.io/badge/License-MIT-yellow" alt="License">
</p>

## 项目简介

Halo是一个现代化的在线学习平台后端系统，专注于提供高质量的编程题目练习和智能学习路径规划。该项目采用了微服务架构设计，基于Spring Boot 3构建，具有高性能、易扩展的特点。

## 技术栈

### 核心框架
- **Spring Boot 3.5.0**: 主要应用框架
- **Java 17**: 编程语言
- **Maven**: 项目构建工具

### 数据库相关
- **MySQL**: 关系型数据库
- **MyBatis Plus**: ORM框架
- **Redis**: 缓存和排行榜功能

### 工具和库
- **Lombok**: 简化Java代码
- **MapStruct**: 对象映射
- **FastJSON**: JSON处理
- **Guava**: Google核心库
- **Apache Commons Lang3**: Apache工具库

### 安全和认证
- **Sa-Token**: 权限认证框架

### 第三方集成
- **MinIO**: 文件存储
- **XXL-JOB**: 分布式任务调度

## 项目结构

```
halo-be
├── halo-common      # 通用模块，包含工具类、枚举、结果封装等
├── halo-domain      # 领域模块，包含业务逻辑、实体类、Mapper等
├── halo-starter     # 启动模块，Spring Boot启动类和配置
└── doc.sql          # 数据库初始化脚本
```

## 功能模块

### 核心功能
1. **题目管理**
   - 支持多种题型（单选、多选、判断、简答）
   - 题目标签和分类管理
   - 题目难度分级

2. **练习系统**
   - 智能组卷
   - 练习记录追踪
   - 答题统计分析

3. **用户系统**
   - 用户注册登录
   - 学习计划制定
   - 个人中心管理

4. **排行榜系统**
   - 实时排名展示
   - 基于Redis的高性能排行计算

5. **分享社区**
   - 学习心得分享
   - 社区互动评论

### 技术特性
- 使用Redis实现高性能排行榜
- 基于MyBatis Plus的高效数据访问层
- Sa-Token实现的安全认证体系
- MapStruct实现的高性能对象转换
- XXL-JOB实现定时任务处理

## 数据库设计

项目包含多个核心数据表：

- `subject_info`: 题目信息表
- `subject_category`: 题目分类表
- `subject_label`: 题目标签表
- `subject_record`: 刷题记录表
- `practice_set`: 套题信息表
- `auth_user`: 用户信息表
- `share_moment`: 分享动态表

完整数据库结构请参考 [doc.sql](../data/doc.sql) 文件。

## 快速开始

### 环境要求
- JDK 17+
- MySQL 8.0+
- Redis 5.0+
- Maven 3.6+

### 配置步骤

1. 克隆项目到本地
```bash
git clone <repository-url>
```

2. 导入数据库
```sql
# 执行 doc.sql 文件初始化数据库
```

3. 修改配置文件
```yaml
# halo-starter/src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/halo?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
  
  redis:
    host: localhost
    port: 6379
    password: your_password
```

4. 启动项目
```bash
cd halo-be
mvn clean install
mvn spring-boot:run -pl halo-starter
```

## API文档

项目遵循RESTful API设计规范，详细接口说明请参考[接口文档](HandlerSystem.md)。

## 部署说明

推荐使用Docker容器化部署：

```dockerfile
# Dockerfile示例
FROM openjdk:17-jdk-alpine
COPY halo-starter/target/halo-starter-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

## 许可证

本项目采用 MIT 许可证，详情请查看 [LICENSE](LICENSE) 文件。

## 贡献指南

欢迎提交Issue和Pull Request来改进项目。

## 联系方式

如有问题，请联系项目维护者。