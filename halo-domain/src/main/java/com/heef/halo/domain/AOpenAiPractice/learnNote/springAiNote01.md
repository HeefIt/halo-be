# Spring AI 学习回顾文档--截止到tools

## 一、ChatClient 的多Bean设计

### 为什么需要多个ChatClient Bean？
```java
@Bean
public ChatClient chatClient(...) // 通用对话

@Bean  
public ChatClient customerChatClient(...) // 智能客服
```

**目的与好处：**
1. **角色隔离** - 不同场景使用不同的系统提示词（System Prompt）
2. **工具集分离** - 客服需要查询工具，普通对话不需要
3. **配置独立** - 每个Client可以有不同的Advisors组合
4. **职责单一** - 每个ChatClient只处理特定场景的对话

## 二、ChatClient的核心API组件

### 1. Builder构建器
```java
ChatClient.builder(openAiChatModel)
    .defaultSystem("提示词")      // 默认系统消息
    .defaultAdvisors(advisor1, advisor2) // 默认拦截器
    .defaultTools(tool1, tool2)    // 默认工具
    .build();
```

### 2. Advisor（顾问/拦截器）
```java
.defaultAdvisors(
    MessageChatMemoryAdvisor.builder(chatMemory).build(), // 会话记忆
    new SimpleLoggerAdvisor()  // 日志记录
)
```
**常用Advisor：**
- `MessageChatMemoryAdvisor` - 管理对话历史记忆
- `SimpleLoggerAdvisor` - 记录请求/响应日志
- `QuestionAnswerAdvisor` - RAG检索增强
- `SafeGuardAdvisor` - 内容安全过滤

## 三、Tools工具机制

### 1. 工具定义规范
```java
@Component
public class SubjectTools implements ChatTools {  // 标记接口
    
    @Tool(description = "获取网站题目总数")  // 必须提供清晰描述
    public Long getTotalSubjectCount() {
        return subjectInfoMapper.selectCount(null);
    }
    
    @Tool(description = "根据ID查询题目")
    public SubjectInfo getSubjectById(
        @ToolParam(description = "题目ID，必须是数字") Long id  // 参数描述
    ) {
        return subjectInfoMapper.selectById(id);
    }
}
```

**@Tool注解要点：**
- `description` **必须写** - 模型依赖描述决定何时调用
- 方法名默认作为工具名，可自定义`name`属性
- 参数默认必需，可用`@Nullable`或`required=false`标记可选
- 返回值会序列化后返回给模型

### 2. 工具注册流程
```java
// 1. 收集所有工具类（自动注入）
List<ChatTools> chatTools  // Spring自动注入所有实现类

// 2. 转换为ToolCallback数组（关键步骤）
ToolCallback[] toolCallbacks = chatTools.stream()
    .flatMap(tool -> Arrays.stream(ToolCallbacks.from(tool)))  // 每个@Tool转为一个Callback
    .toArray(ToolCallback[]::new);

// 3. 注册到ChatClient
.defaultToolCallbacks(toolCallbacks)  // 注意是toolCallbacks()不是tools()
```

### 3. 工具调用流程
```
用户请求 → 模型判断需要工具 → 模型返回工具名+参数 → 
Spring AI执行对应@Tool方法 → 结果返回给模型 → 
模型整合结果生成最终回复
```

## 四、接口层对话实现

### 1. 核心数据结构
```java
// 请求体
public class ChatRequestDTO {
    private List<ChatMessageDTO> messages;  // 消息历史
    private String sessionID;                // 会话ID
}

// 消息结构  
public class ChatMessageDTO {
    private String role;     // user / assistant / system
    private String content;  // 消息内容
}

// 响应体
public class ChatResponseDTO {
    private String reply;     // AI回复
    private String sessionId; // 会话ID
}
```

### 2. 对话实现要点
```java
public Result<ChatResponseDTO> chat(@RequestBody ChatRequestDTO request) {
    
    // 1. 会话ID管理
    String sessionId = generateSessionId(userId);  // Advisor依赖此ID
    
    // 2. 提取当前消息（关键！）
    String userMessage = extractLastUserMessage(request.getMessages());
    // 只传当前消息，历史由Advisor自动管理
    
    // 3. 调用AI
    String reply = chatClient.prompt()
        .user(userMessage)  // 只传当前消息
        .advisors(advisor -> advisor
            .param("chat_memory_conversation_id", sessionId)  // 传入会话ID
        )
        .call()
        .content();
    
    // 4. 返回结果
    return Result.ok(new ChatResponseDTO(reply, sessionId));
}
```

### 3. MessageChatMemoryAdvisor工作原理
```
用户传 sessionId → Advisor根据ID从ChatMemory加载历史消息 → 
将历史消息追加到当前请求 → 调用模型 → 
将本次交互保存回ChatMemory
```

## 五、关键知识点补充

### 1. 三种工具注册方式对比
```java
.tools(objectWithToolMethods)           // 方式1：直接传工具实例（最简单）
.toolCallbacks(toolCallbackArray)       // 方式2：传预构建的Callback（最灵活）
.toolCallbackProviders(providerArray)    // 方式3：传Provider（最动态）
```

### 2. ChatMemory存储
```java
// 默认内存存储
ChatMemoryRepository  // 基于Map的内存实现

// 可扩展为Redis/MongoDB实现
public interface ChatMemory {
    void add(String conversationId, List<Message> messages);
    List<Message> get(String conversationId, int lastN);
}
```

### 3. 常用ChatClient方法
```java
.prompt()                    // 开始构建提示
.user("消息")                 // 设置用户消息
.system("提示词")             // 设置系统消息（覆盖默认）
.messages(List<Message>)     // 直接传入消息列表
.advisors(advisor -> ...)    // 配置Advisor参数
.tools(tool1, tool2)         // 临时添加工具
.call()                      // 执行调用
.stream()                    // 流式响应
```

### 4. 模型交互数据流
```
前端JSON → ChatRequestDTO → 提取userMessage → 
ChatClient → MessageChatMemoryAdvisor(加载历史) → 
OpenAI模型 → Tool调用(如需) → 模型生成 → 
MessageChatMemoryAdvisor(保存历史) → 返回reply
```

## 六、最佳实践总结

1. **按场景拆分ChatClient** - 不同角色、不同工具集
2. **工具类按业务划分** - 每个类专注一个领域，5-10个方法为宜
3. **@Tool描述要详细** - 模型靠描述理解工具用途
4. **只传当前消息** - 历史由Advisor自动管理
5. **会话ID必须传递** - Advisor依赖它存取记忆
6. **工具类用@Component** - 让Spring管理生命周期


#################################################################################################
@Tool("这里的description很关键")
description可以为AI模型通过工具的描述和参数描述来理解： 这个工具是干什么的（功能） 什么情况下该用（触发条件） 参数怎么填（使用方式）

@ToolParam 在POJO中的作用
是的，它的作用就是让AI模型能根据用户的问题，智能地填充这个对象的字段！


