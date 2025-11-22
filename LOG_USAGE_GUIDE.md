# 日志规范使用指南

## 概述

项目已优化为大厂级别的日志输出规范，提供了 `LoggerUtil` 工具类来统一日志记录方式。

## 日志级别说明

| 级别 | 颜色 | 用途 | 示例 |
|------|------|------|------|
| DEBUG | 绿色 | 开发调试信息 | 方法入出、变量值、执行流程 |
| INFO | 青色 | 关键业务信息 | API请求/响应、重要操作 |
| WARN | 黄色 | 警告信息 | 非致命错误、需要注意的情况 |
| ERROR | 红色 | 错误信息 | 异常堆栈、严重错误 |

## 使用示例

### 1. API请求响应日志

```java
import com.heef.halo.util.LoggerUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/user/login")
    public Result<SaTokenInfo> login(@RequestBody AuthUserDTO authUserDTO) {
        try {
            // 记录API请求
            LoggerUtil.logApiRequest(log, "POST", "/api/auth/user/login", authUserDTO);
            
            SaTokenInfo tokenInfo = authService.login(authUserDTO);
            
            // 记录API响应
            LoggerUtil.logApiResponse(log, 200, "/api/auth/user/login", tokenInfo);
            
            return Result.ok(tokenInfo);
        } catch (Exception e) {
            LoggerUtil.logError(log, "用户登录接口异常", e);
            return Result.fail("登录失败: " + e.getMessage());
        }
    }
}
```

### 2. 业务处理日志

```java
@Service
public class AuthService {
    
    public Boolean insert(AuthUserDTO authUserDTO) {
        try {
            LoggerUtil.logBusiness(log, "新增用户", authUserDTO);
            
            // 业务处理...
            
            LoggerUtil.logBusiness(log, "新增用户成功", true);
            return true;
        } catch (Exception e) {
            LoggerUtil.logError(log, "新增用户失败", e);
            throw e;
        }
    }
}
```

### 3. 方法级日志

```java
public PageResult<AuthUserDTO> selectPage(AuthUserDTO authUserDTO, Integer pageNum, Integer pageSize) {
    LoggerUtil.logMethodIn(log, "AuthService.selectPage", authUserDTO, pageNum, pageSize);
    
    try {
        // 业务逻辑...
        PageResult<AuthUserDTO> result = new PageResult<>();
        
        LoggerUtil.logMethodOut(log, "AuthService.selectPage", result);
        return result;
    } catch (Exception e) {
        LoggerUtil.logError(log, "分页查询失败", e);
        throw e;
    }
}
```

## 输出样式示例

### 控制台输出（带颜色）

```
2025-11-22 16:02:00.123 DEBUG [http-nio-2004-exec-8] c.h.h.d.b.c.AuthController - → [METHOD IN] login | params: {"userName":"admin","password":"***"}
2025-11-22 16:02:00.456 INFO  [http-nio-2004-exec-8] c.h.h.d.b.c.AuthController - → [API REQUEST] POST /api/auth/user/login | params: {"userName":"admin"}
2025-11-22 16:02:00.789 INFO  [http-nio-2004-exec-8] c.h.h.d.b.c.AuthController - ◆ [BUSINESS] 用户登录成功 | data: {"id":1,"userName":"admin"}
2025-11-22 16:02:01.012 INFO  [http-nio-2004-exec-8] c.h.h.d.b.c.AuthController - ← [API RESPONSE] 200 /api/auth/user/login | response: {"code":200,"message":"操作成功"}
2025-11-22 16:02:01.345 ERROR [http-nio-2004-exec-8] c.h.h.d.b.c.AuthController - ✗ [ERROR] 用户登录接口异常 | msg: 用户名或密码错误
```

### 文件输出（不带颜色）

```
2025-11-22 16:02:00.123 DEBUG [http-nio-2004-exec-8] c.h.h.d.b.c.AuthController - → [METHOD IN] login | params: {"userName":"admin","password":"***"}
2025-11-22 16:02:00.456 INFO  [http-nio-2004-exec-8] c.h.h.d.b.c.AuthController - → [API REQUEST] POST /api/auth/user/login | params: {"userName":"admin"}
2025-11-22 16:02:01.345 ERROR [http-nio-2004-exec-8] c.h.h.d.b.c.AuthController - ✗ [ERROR] 用户登录接口异常 | msg: 用户名或密码错误
```

## LoggerUtil API 说明

### 方法列表

#### 1. `logMethodIn()` - 方法入口日志
```java
LoggerUtil.logMethodIn(log, "methodName", param1, param2, ...);
```
- 级别：DEBUG
- 用途：记录方法入口和参数

#### 2. `logMethodOut()` - 方法出口日志
```java
LoggerUtil.logMethodOut(log, "methodName", result);
```
- 级别：DEBUG
- 用途：记录方法返回值

#### 3. `logApiRequest()` - API请求日志
```java
LoggerUtil.logApiRequest(log, "GET|POST|PUT|DELETE", "/api/path", params);
```
- 级别：INFO
- 用途：记录HTTP请求信息

#### 4. `logApiResponse()` - API响应日志
```java
LoggerUtil.logApiResponse(log, httpCode, "/api/path", responseData);
```
- 级别：INFO
- 用途：记录HTTP响应信息

#### 5. `logBusiness()` - 业务处理日志
```java
LoggerUtil.logBusiness(log, "业务描述", businessData);
```
- 级别：INFO
- 用途：记录业务关键节点

#### 6. `logError()` - 错误日志
```java
LoggerUtil.logError(log, "错误上下文", exception);
```
- 级别：ERROR
- 用途：记录异常信息（包含堆栈）

#### 7. `logWarn()` - 警告日志
```java
LoggerUtil.logWarn(log, "警告描述", warnData);
```
- 级别：WARN
- 用途：记录需要关注的情况

#### 8. `formatJson()` - JSON格式化（单行）
```java
String json = LoggerUtil.formatJson(obj);
```
- 返回紧凑的单行JSON
- 用于日志输出，避免换行

#### 9. `formatJsonPretty()` - JSON美化格式化（多行）
```java
String json = LoggerUtil.formatJsonPretty(obj);
```
- 返回带缩进的多行JSON
- 仅用于调试，需手动打印

## 配置说明

### logback-spring.xml 配置

- **控制台输出**：带颜色，格式化输出，便于开发调试
- **文件输出**：不带颜色，按日期和大小滚动，生产级
- **日志级别**：
  - `com.heef.halo`：DEBUG（业务代码）
  - `org.springframework`：INFO（框架日志）
  - `com.zaxxer.hikari`：WARN（数据库连接）

## 最佳实践

1. **API层**：使用 `logApiRequest()` 和 `logApiResponse()`
2. **Service层**：使用 `logBusiness()` 和 `logError()`
3. **异常处理**：总是使用 `logError()` 而不是直接 `log.error()`
4. **敏感信息**：避免在日志中输出密码、Token等敏感数据
5. **性能**：使用 `log.isDebugEnabled()` 检查，避免不必要的JSON序列化

## 注意事项

- 日志输出已优化为大厂规范格式，避免过长行导致的换行问题
- JSON数据在日志中保持单行紧凑格式，提高可读性
- 生产环境可通过调整 logback-spring.xml 中的日志级别来控制输出量
- 定期检查日志文件大小，配置已设置为100MB自动滚动
