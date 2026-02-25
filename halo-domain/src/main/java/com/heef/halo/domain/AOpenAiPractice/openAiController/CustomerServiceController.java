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
 * 智能客服控制器
 * 功能：7x24小时智能客服，快速解答用户疑问
 *
 * @author kyrie.huang
 * @date 2026/2/25
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ai/customer")
@RestController
public class CustomerServiceController {

    private final ChatClient chatClient;

    /**
     * 智能客服问答
     *
     * @param request 用户问题
     * @return AI回复
     */
    @PostMapping("/chat")
    public Result<ChatResponseDTO> customerService(@RequestBody ChatRequestDTO request) {
        try {
            log.info("收到智能客服请求");

            String systemPrompt = """
                你是一个专业的智能客服助手，7x24小时在线服务用户。
                你需要快速、准确、友好地解答用户关于平台使用、刷题、学习计划等方面的问题。
                如果问题涉及具体操作，请给出详细的步骤说明。
                如果无法解答，请礼貌地引导用户联系人工客服。
                保持友好、专业的语气。
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
            log.error("智能客服服务失败", e);
            return Result.fail("AI服务异常: " + e.getMessage());
        }
    }

    /**
     * 常见问题查询
     *
     * @param request 用户查询
     * @return 常见问题答案
     */
    @PostMapping("/faq")
    public Result<ChatResponseDTO> getFAQ(@RequestBody ChatRequestDTO request) {
        try {
            log.info("收到常见问题查询请求");

            String systemPrompt = """
                你是一个FAQ（常见问题）助手，专门回答用户关于Halo刷题网的常见问题。
                重点关注以下方面：
                1. 账号注册与登录
                2. 题库使用方法
                3. 学习计划创建
                4. 排行榜规则
                5. 练习记录查询
                请给出简洁明了的答案。
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
            log.error("FAQ查询失败", e);
            return Result.fail("AI服务异常: " + e.getMessage());
        }
    }

}
