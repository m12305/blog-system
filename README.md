# 📝 Blog System（博客系统）

一个轻量级的个人博客系统，基于 **Spring Boot 3** + **MyBatis-Plus** 构建，支持 JWT 鉴权、Markdown 编辑和简洁的静态前端页面。

## ✨ 功能特性

- **用户系统** — 注册、登录、个人资料管理、头像上传、密码修改
- **博客管理** — 文章的发布、查看、编辑与软删除
- **Markdown 编辑器** — 基于 [Editor.md](https://pandao.github.io/editor.md/) 的富文本 Markdown 编辑体验
- **评论系统** — 发表和删除文章评论
- **JWT 鉴权** — 基于 Token 的无状态认证，有效期 24 小时
- **分页查询** — 博客列表支持可配置的分页大小
- **软删除** — 所有实体（用户、博客、评论）均采用逻辑删除
- **全局异常处理** — 统一的错误响应格式
- **响应式 UI** — 纯 HTML/CSS/JS + jQuery 实现的前端界面

## 🛠 技术栈

| 层级 | 技术 |
|------|------|
| 后端框架 | Spring Boot 3.4.12 |
| 开发语言 | Java 17 |
| ORM | MyBatis-Plus 3.5.5 + MyBatis 3.0.5 |
| 数据库 | MySQL |
| 认证鉴权 | JJWT（JSON Web Token） |
| 构建工具 | Maven |
| 前端 | HTML/CSS/JS + jQuery + Editor.md |
| 工具库 | Lombok、Spring Validation |

## 📁 项目结构

```
src/main/java/com/my_project/bolg_system/
├── BolgSystemApplication.java    # 应用启动入口
├── blog_enums/
│   └── ResultCodeEnum.java        # API 响应状态码
├── configration/
│   ├── LoginConfig.java           # 拦截器注册配置
│   ├── FileConfig.java            # 文件上传配置
│   └── MyWebConfigration.java     # 静态资源映射
├── controller/
│   ├── BlogController.java        # /blog/* 接口
│   ├── UserController.java        # /user/* 接口
│   └── CommentController.java     # /comment/* 接口
├── exception/
│   └── ExceptionAdvice.java       # 全局异常捕获
├── interceptor/
│   └── LoginInterception.java     # JWT 令牌校验拦截器
├── mapper/
│   ├── BlogMapper.java
│   ├── UserMapper.java
│   └── CommentMapper.java
├── model/
│   ├── BlogInfo.java              # 博客实体
│   ├── UserInfo.java              # 用户实体
│   ├── CommentInfo.java           # 评论实体
│   ├── BlogListInfo.java          # 分页列表 DTO
│   ├── Result.java                # 统一 API 响应封装
│   └── Constant.java              # 常量定义
├── service/
│   ├── BlogService.java
│   ├── UserService.java
│   └── CommentService.java
└── utils/
    ├── JWTUtils.java              # JWT 生成与解析工具
    └── GetFromHead.java           # 请求头 Token 提取工具

src/main/resources/
├── application.yml                # 环境配置激活
├── application-dev.yml            # 开发环境配置
├── application-prod.yml           # 生产环境配置
└── static/                        # 前端页面与静态资源
    ├── blog_list.html             # 博客列表页
    ├── blog_detail.html           # 博客详情页
    ├── blog_edit.html             # 博客编辑页
    ├── blog_login.html            # 登录页
    ├── blog_register.html         # 注册页
    ├── blog_profile.html          # 个人中心页
    ├── css/                       # 样式文件
    ├── js/                        # JavaScript 脚本
    ├── pic/                       # 图片资源
    ├── avatars/                   # 用户头像目录
    └── blog-editormd/             # Editor.md 编辑器
```

## 🚀 快速开始

### 环境要求

- **JDK 17** 及以上
- **Maven 3.6+**
- **MySQL 8.0+**

### 1. 克隆项目

```bash
git clone https://github.com/your-username/bolg_system.git
cd bolg_system
```

### 2. 创建数据库

```sql
CREATE DATABASE blog_system DEFAULT CHARACTER SET utf8mb4;
```

然后执行以下建表语句：

```sql
-- 博客文章表
CREATE TABLE blog_article (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content MEDIUMTEXT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

-- 用户表
CREATE TABLE blog_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(50),
    avatar VARCHAR(500),
    email VARCHAR(100),
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);

-- 评论表
CREATE TABLE blog_comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    blog_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT DEFAULT 0
);
```

### 3. 配置数据库连接

修改 [application-dev.yml](src/main/resources/application-dev.yml) 或 [application-prod.yml](src/main/resources/application-prod.yml) 中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/blog_system?characterEncoding=utf8&useSSL=false
    username: root
    password: 你的密码
```

### 4. 启动项目

```bash
# 开发模式（启用 SQL 日志）
mvn spring-boot:run -P dev

# 生产模式
mvn spring-boot:run -P prod
```

项目启动后访问：**http://localhost:8080**

### 5. 访问前端页面

在浏览器中打开以下地址：

- **博客列表** — http://localhost:8080/blog_list.html
- **登录** — http://localhost:8080/blog_login.html
- **注册** — http://localhost:8080/blog_register.html

## 📡 API 接口

### 博客接口（`/blog`）

| 方法 | 路径 | 鉴权 | 说明 |
|------|------|------|------|
| GET | `/blog/getList?page=&pageSize=` | 否 | 获取博客分页列表 |
| GET | `/blog/getDetail?blogId=` | 否 | 获取博客详情 |
| POST | `/blog/postBlog` | 是 | 发布新博客 |
| GET | `/blog/getUserBlog?userId=` | 否 | 获取某用户的博客 |
| POST | `/blog/updateBlog` | 是 | 更新博客（仅限作者） |
| GET | `/blog/editPower?blogId=` | 是 | 检查编辑权限 |
| POST | `/blog/deleteBlog` | 是 | 删除博客（仅限作者） |

### 用户接口（`/user`）

| 方法 | 路径 | 鉴权 | 说明 |
|------|------|------|------|
| POST | `/user/login?username=&password=` | 否 | 登录，返回 JWT |
| POST | `/user/register` | 否 | 注册新用户 |
| GET | `/user/getUserInfo` | 是 | 获取当前用户信息 |
| POST | `/user/updateUserInfo` | 是 | 更新个人资料 |
| POST | `/user/updatePassword` | 是 | 修改密码 |
| POST | `/user/delete` | 是 | 注销账号 |
| POST | `/user/updateAvatar` | 是 | 上传头像（multipart） |

### 评论接口（`/comment`）

| 方法 | 路径 | 鉴权 | 说明 |
|------|------|------|------|
| POST | `/comment/postComment` | 是 | 发表评论 |
| GET | `/comment/getCommentList?blogId=` | 否 | 获取文章评论 |
| POST | `/comment/deleteComment?commentId=` | 是 | 删除评论 |

### 统一响应格式

所有 API 接口返回统一的 JSON 结构：

```json
{
    "code": 200,
    "errMsg": "",
    "data": { ... }
}
```

- `code: 200` — 请求成功
- `code: -1` — 请求失败（`errMsg` 中返回错误信息）

## 🔐 鉴权说明

系统使用 JWT（JSON Web Token）实现无状态认证：

1. 调用 `POST /user/login` 传入用户名和密码
2. 响应中获取 JWT Token
3. 后续请求在 HTTP 头 `user_token` 中携带该 Token
4. Token 有效期为 **24 小时**

博客列表、博客详情、登录和注册接口无需鉴权，其余接口均需携带有效的 JWT Token。

## 🖼 界面截图

> *部署后在此处添加截图*

## 📝 License

本项目仅用于个人学习和交流目的。

## 🙏 致谢

- [Editor.md](https://pandao.github.io/editor.md/) — 开源 Markdown 编辑器
- [MyBatis-Plus](https://baomidou.com/) — MyBatis 增强工具包
- [Spring Boot](https://spring.io/projects/spring-boot) — Java 应用开发框架
