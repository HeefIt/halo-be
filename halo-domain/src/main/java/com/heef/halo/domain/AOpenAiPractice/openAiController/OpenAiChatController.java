package com.heef.halo.domain.AOpenAiPractice.openAiController;

import com.alibaba.fastjson.JSON;
import com.heef.halo.domain.AOpenAiPractice.openAiConfig.PromptConfig;
import com.heef.halo.domain.AOpenAiPractice.openAiDto.*;
import com.heef.halo.domain.AOpenAiPractice.openAiService.impl.AiSessionServiceImpl;
import com.heef.halo.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Date;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ai")
@RestController
public class OpenAiChatController {

    //注入自定义对话模型
    private final ChatClient chatClient;
    
    @Autowired
    private AiSessionServiceImpl aiSessionService;

    @Autowired
    private PromptConfig promptConfig;


    /**
     * 基础对话测试(流式) - 保留原接口用于测试
     *
     * @return
     */
    @PostMapping("/test/stream")
    public Flux<String> chatClientStream(@RequestBody PromptDTO promptDTO) {
        return chatClient.prompt()
                .user(promptDTO.getPrompt())
                .stream()
                .content();
    }

    /**
     * AI对话接口 - 支持会话记忆和历史会话
     * 前端标准接口
     *
     * @param request 聊天请求，包含消息历史
     * @return AI回复结果
     */
    @PostMapping("/chat")
    public Result<ChatResponseDTO> chat(@RequestBody ChatRequestDTO request,
                                        @RequestHeader(value = "userId", required = false) Long userId) {
        try {
            log.info("收到AI聊天请求，消息数量: {}, userId: {}", request.getMessages().size(), userId);

            // 1. 处理用户ID
            if (userId == null) {
                userId = 0L;
            }

            // 2. 处理会话ID（Advisor需要这个ID来管理记忆）
            String sessionId = request.getSessionID();
            if (sessionId == null || sessionId.isEmpty()) {
                sessionId = userId + "_" + UUID.randomUUID().toString();  // 生成唯一会话ID
                log.info("生成新会话ID: {}", sessionId);
            }

            // 3. 获取最后一条用户消息（只传当前问题）
            String userMessage = request.getMessages().stream()
                    .filter(m -> "user".equalsIgnoreCase(m.getRole()))
                    .map(ChatMessageDTO::getContent)
                    .reduce((first, second) -> second)  // 取最后一条
                    .orElse("");

            log.info("当前用户消息: {}, 会话ID: {}", userMessage, sessionId);

            final String finalSessionId = sessionId;  // 创建final副本(在Lambda表达式中使用的变量必须是final不可变的)

            // 4. 调用AI - Advisor会自动管理历史消息
            String reply = chatClient.prompt()
                    .user(userMessage)  // 只传当前消息！
                    .advisors(advisor -> advisor
                            .param("chat_memory_conversation_id", finalSessionId)  // 告诉Advisor会话ID
                    )
                    .call()
                    .content();

            log.info("AI回复成功: {}", reply);

//            // 5. 可选：同步数据到您的内存Map（如果需要持久化）
//            syncToYourMemory(sessionId, userId, userMessage, reply);

            // 6. 构建响应
            ChatResponseDTO responseDTO = ChatResponseDTO.builder()
                    .reply(reply)
                    .sessionId(sessionId)
                    .build();

            return Result.ok(responseDTO);

        } catch (Exception e) {
            log.error("AI对话失败", e);
            return Result.fail("AI服务异常: " + e.getMessage());
        }
    }

    /**
     * AI对话接口 - 流式输出版本，支持会话记忆
     * 
     * @param request 聊天请求，包含消息历史
     * @return 流式AI回复结果
     */
    @PostMapping(value = "/chat/stream", produces = "text/event-stream")
    public Flux<ChatResponseDTO> chatStream(@RequestBody ChatRequestDTO request,
                                           @RequestHeader(value = "userId", required = false) Long userId) {
        try {
            log.info("收到AI流式聊天请求，消息数量: {}, userId: {}", request.getMessages().size(), userId);

            // 1. 处理用户ID
            if (userId == null) {
                userId = 0L;
            }

            // 2. 处理会话ID（Advisor需要这个ID来管理记忆）
            String sessionId = request.getSessionID();
            if (sessionId == null || sessionId.isEmpty()) {
                sessionId = userId + "_" + UUID.randomUUID().toString();  // 生成唯一会话ID
                log.info("生成新会话ID: {}", sessionId);
            }

            // 3. 获取最后一条用户消息（只传当前问题）
            String userMessage = request.getMessages().stream()
                    .filter(m -> "user".equalsIgnoreCase(m.getRole()))
                    .map(ChatMessageDTO::getContent)
                    .reduce((first, second) -> second)  // 取最后一条
                    .orElse("");

            log.info("当前用户消息: {}, 会话ID: {}", userMessage, sessionId);

            final String finalSessionId = sessionId;  // 创建final副本(在Lambda表达式中使用的变量必须是final不可变的)

            // 4. 调用AI流式输出
            return chatClient.prompt()
                    .user(userMessage)  // 只传当前消息！
                    .advisors(advisor -> advisor
                            .param("chat_memory_conversation_id", finalSessionId)  // 告诉Advisor会话ID
                    )
                    .stream()
                    .content()
                    .map(content -> ChatResponseDTO.builder()
                            .reply(content)
                            .sessionId(finalSessionId)
                            .build());

        } catch (Exception e) {
            log.error("AI流式对话失败", e);
            return Flux.error(new RuntimeException("AI服务异常: " + e.getMessage()));
        }
    }


//    /**
//     * 阻塞式--AI对话接口 - 手动管理历史对话信息--在clientConfig中可以删除advisor中的MessageChatMemoryAdvisor
//     * @param request
//     * @param userId
//     * @return
//     */
//    @PostMapping("/chat")
//    public Result<ChatResponseDTO> chat(@RequestBody ChatRequestDTO request,
//                                        @RequestHeader(value = "userId", required = false) Long userId) {
//        try {
//            log.info("收到AI聊天请求，消息数量: {}, userId: {}", request.getMessages().size(), userId);
//
//            // 1. 处理用户ID
//            if (userId == null) {
//                userId = 0L;
//            }
//
//            // 2. 处理会话ID
//            String sessionId = request.getSessionID();
//            if (sessionId == null || sessionId.isEmpty()) {
//                // 创建新会话
//                SessionDTO newSession = aiSessionService.createSession(userId, "新对话");
//                sessionId = newSession.getSessionId();
//                log.info("创建新会话: {}", sessionId);
//            }
//
//            // 3. 从内存获取历史消息
//            List<ChatMessageDTO> historyMessages = aiSessionService.getSessionMessages(sessionId, userId);
//            log.info("历史消息数量: {}", historyMessages.size());
//
//            // 4. 构建消息列表（历史 + 当前）
//            List<Message> messages = new ArrayList<>();
//
//            // 4.1 添加历史消息
//            for (ChatMessageDTO msg : historyMessages) {
//                if ("user".equalsIgnoreCase(msg.getRole())) {
//                    messages.add(new UserMessage(msg.getContent()));
//                } else if ("assistant".equalsIgnoreCase(msg.getRole())) {
//                    messages.add(new AssistantMessage(msg.getContent()));
//                }
//            }
//
//            // 4.2 添加当前用户消息
//            String currentUserMessage = null;
//            for (ChatMessageDTO msg : request.getMessages()) {
//                if ("user".equalsIgnoreCase(msg.getRole())) {
//                    messages.add(new UserMessage(msg.getContent()));
//                    currentUserMessage = msg.getContent();
//                }
//            }
//
//            log.info("最终发送给AI的消息总数: {}", messages.size());
//
//            // 5. 调用AI
//            String reply = chatClient.prompt()
//                    .messages(messages)
//                    .call()
//                    .content();
//
//            log.info("AI回复成功: {}", reply);
//
//            // 6. 保存对话到内存
//            // 6.1 保存用户消息
//            if (currentUserMessage != null) {
//                ChatMessageDTO userMsg = new ChatMessageDTO();
//                userMsg.setRole("user");
//                userMsg.setContent(currentUserMessage);
//                userMsg.setTimestamp(String.valueOf(System.currentTimeMillis()));
//                aiSessionService.addMessageToSession(sessionId, userId, userMsg);
//            }
//
//            // 6.2 保存AI回复
//            ChatMessageDTO assistantMsg = new ChatMessageDTO();
//            assistantMsg.setRole("assistant");
//            assistantMsg.setContent(reply);
//            assistantMsg.setTimestamp(String.valueOf(System.currentTimeMillis()));
//            aiSessionService.addMessageToSession(sessionId, userId, assistantMsg);
//
//            log.info("对话已保存到内存，会话ID: {}", sessionId);
//
//            // 7. 构建响应
//            ChatResponseDTO responseDTO = ChatResponseDTO.builder()
//                    .reply(reply)
//                    .sessionId(sessionId)
//                    .build();
//
//            return Result.ok(responseDTO);
//
//        } catch (Exception e) {
//            log.error("AI对话失败", e);
//            return Result.fail("AI服务异常: " + e.getMessage());
//        }
//    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 创建新会话
     */
    @PostMapping("/session/create")
    public Result<SessionDTO> createSession(@RequestParam(required = false) String title,
                                           @RequestHeader(value = "userId", required = false) Long userId) {
        try {
            if (userId == null) {
                userId = 0L;
            }
            
            SessionDTO session = aiSessionService.createSession(userId, title);
            return Result.ok(session);
        } catch (Exception e) {
            log.error("创建会话失败", e);
            return Result.fail("创建会话失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取用户会话列表
     */
    @GetMapping("/session/list")
    public Result<List<SessionDTO>> getUserSessions(@RequestHeader(value = "userId", required = false) Long userId) {
        try {
            if (userId == null) {
                userId = 0L;
            }
            
            List<SessionDTO> sessions = aiSessionService.getUserSessions(userId);
            return Result.ok(sessions);
        } catch (Exception e) {
            log.error("获取会话列表失败", e);
            return Result.fail("获取会话列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取会话详情
     */
    @GetMapping("/session/{sessionId}")
    public Result<SessionDTO> getSession(@PathVariable String sessionId,
                                        @RequestHeader(value = "userId", required = false) Long userId) {
        try {
            if (userId == null) {
                userId = 0L;
            }
            
            log.info("获取会话详情: sessionId={}, userId={}", sessionId, userId);
            SessionDTO session = aiSessionService.getSessionById(sessionId, userId);
            if (session == null) {
                log.warn("会话不存在或无权限访问: sessionId={}, userId={}", sessionId, userId);
                return Result.fail("会话不存在或无权限访问");
            }
            
            log.info("成功获取会话详情: sessionId={}, 消息数量={}", sessionId, session.getMessages() != null ? session.getMessages().size() : 0);
            return Result.ok(session);
        } catch (Exception e) {
            log.error("获取会话详情失败", e);
            return Result.fail("获取会话详情失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除会话
     */
    @DeleteMapping("/session/{sessionId}")
    public Result<Boolean> deleteSession(@PathVariable String sessionId,
                                        @RequestHeader(value = "userId", required = false) Long userId) {
        try {
            if (userId == null) {
                userId = 0L;
            }
            
            boolean result = aiSessionService.deleteSession(sessionId, userId);
            if (result) {
                return Result.ok(true);
            } else {
                return Result.fail("删除会话失败：会话不存在或无权限");
            }
        } catch (Exception e) {
            log.error("删除会话失败", e);
            return Result.fail("删除会话失败: " + e.getMessage());
        }
    }
    
    /**
     * 清空会话消息
     */
    @PostMapping("/session/{sessionId}/clear")
    public Result<Boolean> clearSessionMessages(@PathVariable String sessionId,
                                               @RequestHeader(value = "userId", required = false) Long userId) {
        try {
            if (userId == null) {
                userId = 0L;
            }
            
            boolean result = aiSessionService.clearSessionMessages(sessionId, userId);
            if (result) {
                return Result.ok(true);
            } else {
                return Result.fail("清空会话消息失败：会话不存在或无权限");
            }
        } catch (Exception e) {
            log.error("清空会话消息失败", e);
            return Result.fail("清空会话消息失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新会话状态（包括消息内容）
     */
    @PutMapping("/session/{sessionId}")
    public Result<Boolean> updateSession(@PathVariable String sessionId,
                                        @RequestBody SessionDTO sessionDTO,
                                        @RequestHeader(value = "userId", required = false) Long userId) {
        try {
            if (userId == null) {
                userId = 0L;
            }
            
            log.info("更新会话: sessionId={}, userId={}, 标题={}, 消息数量={}", 
                    sessionId, userId, 
                    sessionDTO.getTitle(), 
                    sessionDTO.getMessages() != null ? sessionDTO.getMessages().size() : 0);
            
            // 验证会话ID匹配
            if (!sessionId.equals(sessionDTO.getSessionId())) {
                log.warn("会话ID不匹配: path={}, body={}", sessionId, sessionDTO.getSessionId());
                return Result.fail("会话ID不匹配");
            }
            
            // 设置用户ID
            sessionDTO.setUserId(userId);
            
            boolean result = aiSessionService.updateSession(sessionDTO);
            if (result) {
                log.info("会话更新成功: sessionId={}", sessionId);
                return Result.ok(true);
            } else {
                log.warn("会话更新失败：会话不存在或无权限, sessionId={}, userId={}", sessionId, userId);
                return Result.fail("更新会话失败：会话不存在或无权限");
            }
        } catch (Exception e) {
            log.error("更新会话失败", e);
            return Result.fail("更新会话失败: " + e.getMessage());
        }
    }


    /**
     * 持久化--对话消息
     */
    private void syncToYourMemory(String sessionId, Long userId, String userMessage, String assistantMessage) {
        try {
            // 检查会话是否已存在
            SessionDTO session = aiSessionService.getSessionById(sessionId, userId);
            if (session == null) {
                // 创建新会话
                session = aiSessionService.createSession(userId, "新对话");
                // 注意：这里sessionId可能不同，需要处理
            }

            // 保存用户消息
            ChatMessageDTO userMsg = new ChatMessageDTO();
            userMsg.setRole("user");
            userMsg.setContent(userMessage);
            userMsg.setTimestamp(String.valueOf(System.currentTimeMillis()));
            aiSessionService.addMessageToSession(sessionId, userId, userMsg);

            // 保存AI回复
            ChatMessageDTO assistantMsg = new ChatMessageDTO();
            assistantMsg.setRole("assistant");
            assistantMsg.setContent(assistantMessage);
            assistantMsg.setTimestamp(String.valueOf(System.currentTimeMillis()));
            aiSessionService.addMessageToSession(sessionId, userId, assistantMsg);

            log.info("同步到内存Map成功: {}", sessionId);
        } catch (Exception e) {
            log.error("同步到内存Map失败", e);
        }
    }


}
