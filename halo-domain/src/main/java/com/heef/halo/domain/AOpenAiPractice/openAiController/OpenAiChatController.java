package com.heef.halo.domain.AOpenAiPractice.openAiController;

import com.heef.halo.domain.AOpenAiPractice.openAiDto.*;
import com.heef.halo.domain.AOpenAiPractice.openAiService.impl.AiSessionServiceImpl;
import com.heef.halo.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Date;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ai")
@RestController
public class OpenAiChatController {

    //注入自定义对话模型
    private final ChatClient chatClient;
    
    @Autowired
    private AiSessionServiceImpl aiSessionService;


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

            // 如果没有用户ID，使用默认ID
            if (userId == null) {
                userId = 0L;
            }

            // 构建消息列表
            List<Message> messages = new ArrayList<>();

            // 添加系统提示词
            messages.add(new SystemMessage("你是一个智能AI助手，能够回答各种问题，提供有用的建议和帮助。"));

            // 添加历史消息
            for (ChatMessageDTO messageDTO : request.getMessages()) {
                if ("user".equalsIgnoreCase(messageDTO.getRole())) {
                    messages.add(new UserMessage(messageDTO.getContent()));
                } else if ("assistant".equalsIgnoreCase(messageDTO.getRole())) {
                    messages.add(new org.springframework.ai.chat.messages.AssistantMessage(messageDTO.getContent()));
                }
            }

            // 调用AI获取回复
            String reply = chatClient.prompt()
                    .messages(messages)
                    .call()
                    .content();

            log.info("AI回复成功");

            // 构建响应
            ChatResponseDTO responseDTO = ChatResponseDTO.builder()
                    .reply(reply)
                    .build();

            return Result.ok(responseDTO);

        } catch (Exception e) {
            log.error("AI对话失败", e);
            return Result.fail("AI服务异常: " + e.getMessage());
        }
    }

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

}
