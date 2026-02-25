package com.heef.halo.domain.AOpenAiPractice.openAiController;

import com.heef.halo.domain.AOpenAiPractice.openAiDto.*;
import com.heef.halo.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 刷题助手控制器
 * 功能：智能推荐题目、提供题目解析、个性化学习建议
 *
 * @author kyrie.huang
 * @date 2026/2/25
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ai/practice")
@RestController
public class PracticeAssistantController {

    private final ChatClient chatClient;

    /**
     * 智能推荐题目
     *
     * @param request 包含用户历史和学习偏好的请求
     * @return 推荐的题目或建议
     */
    @PostMapping("/recommend")
    public Result<ChatResponseDTO> recommendQuestions(@RequestBody ChatRequestDTO request) {
        try {
            log.info("收到刷题推荐请求");

            // 构建系统提示词 - 针对刷题推荐
            String systemPrompt = """
                你是一个智能刷题助手，能够根据用户的学习情况推荐合适的题目。
                请根据用户的刷题历史和学习偏好，提供个性化的题目推荐和学习建议。
                回复格式：1. 推荐理由；2. 题目类型；3. 学习建议。
                """;

            List<Message> messages = new ArrayList<>();
            messages.add(new SystemMessage(systemPrompt));

            // 添加用户消息
            for (ChatMessageDTO messageDTO : request.getMessages()) {
                if ("user".equalsIgnoreCase(messageDTO.getRole())) {
                    messages.add(new UserMessage(messageDTO.getContent()));
                }
            }

            String reply = chatClient.prompt()
                    .messages(messages)
                    .call()
                    .content();

            ChatResponseDTO responseDTO = ChatResponseDTO.builder()
                    .reply(reply)
                    .build();

            return Result.ok(responseDTO);

        } catch (Exception e) {
            log.error("刷题推荐失败", e);
            return Result.fail("AI服务异常: " + e.getMessage());
        }
    }

    /**
     * 题目解析
     *
     * @param request 包含题目内容和用户答案的请求
     * @return 题目解析和正确答案
     */
    @PostMapping("/analyze")
    public Result<ChatResponseDTO> analyzeQuestion(@RequestBody ChatRequestDTO request) {
        try {
            log.info("收到题目解析请求");

            String systemPrompt = """
                你是一个专业的题目解析助手，能够提供详细的题目解析和解题思路。
                请分析用户提供的题目，给出：1. 正确答案；2. 详细解析；3. 解题思路；4. 相关知识点。
                """;

            List<Message> messages = new ArrayList<>();
            messages.add(new SystemMessage(systemPrompt));

            for (ChatMessageDTO messageDTO : request.getMessages()) {
                if ("user".equalsIgnoreCase(messageDTO.getRole())) {
                    messages.add(new UserMessage(messageDTO.getContent()));
                }
            }

            String reply = chatClient.prompt()
                    .messages(messages)
                    .call()
                    .content();

            ChatResponseDTO responseDTO = ChatResponseDTO.builder()
                    .reply(reply)
                    .build();

            return Result.ok(responseDTO);

        } catch (Exception e) {
            log.error("题目解析失败", e);
            return Result.fail("AI服务异常: " + e.getMessage());
        }
    }

    /**
     * 个性化学习建议
     *
     * @param request 包含用户学习数据的请求
     * @return 个性化学习建议
     */
    @PostMapping("/suggest")
    public Result<ChatResponseDTO> getStudySuggestion(@RequestBody ChatRequestDTO request) {
        try {
            log.info("收到学习建议请求");

            String systemPrompt = """
                你是一个学习规划专家，能够根据用户的学习数据提供个性化的学习建议。
                请分析用户的学习情况，提供：1. 学习进度评估；2. 薄弱知识点分析；3. 学习计划建议；4. 学习方法推荐。
                """;

            List<Message> messages = new ArrayList<>();
            messages.add(new SystemMessage(systemPrompt));

            for (ChatMessageDTO messageDTO : request.getMessages()) {
                if ("user".equalsIgnoreCase(messageDTO.getRole())) {
                    messages.add(new UserMessage(messageDTO.getContent()));
                }
            }

            String reply = chatClient.prompt()
                    .messages(messages)
                    .call()
                    .content();

            ChatResponseDTO responseDTO = ChatResponseDTO.builder()
                    .reply(reply)
                    .build();

            return Result.ok(responseDTO);

        } catch (Exception e) {
            log.error("学习建议生成失败", e);
            return Result.fail("AI服务异常: " + e.getMessage());
        }
    }

}
