package com.heef.halo.domain.AOpenAiPractice.openAiController;

import com.heef.halo.domain.AOpenAiPractice.openAiDto.*;
import com.heef.halo.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.codec.ServerSentEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.time.Duration;
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

    private final ChatClient customerChatClient;

    /**
     * 智能客服问答
     *
     * @param request 用户问题
     * @return AI回复
     */
    @PostMapping("/chat")
    public Result<ChatResponseDTO> customerService(@RequestBody ChatRequestDTO request) {
        try {
            log.info("智能客服收到请求");

            //消息队列
            List<Message> messages = new ArrayList<>();

            // 添加系统提示词
            String systemPrompt = """
                你是一个专业的智能客服助手，请以友好、专业的方式回答用户的问题。
                回答时请注意：
                1. 语言简洁明了
                2. 态度热情友好
                3. 提供准确有用的信息
                4. 如遇到无法回答的问题，请建议用户联系人工客服
                """;
            messages.add(new SystemMessage(systemPrompt));

            for (ChatMessageDTO messageDTO : request.getMessages()) {
                if ("user".equalsIgnoreCase(messageDTO.getRole())) {
                    messages.add(new UserMessage(messageDTO.getContent()));
                } else if ("assistant".equalsIgnoreCase(messageDTO.getRole())) {
                    messages.add(new AssistantMessage(messageDTO.getContent()));
                }
            }

            String reply = customerChatClient.prompt()
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
     * 智能客服问答 - 标准SSE流式输出版本
     * 使用ServerSentEvent包装每个流式响应块，确保SSE协议标准
     * 移除了复杂的字符级缓冲逻辑，直接转发AI的每个响应块
     *
     * @param request 用户问题
     * @return 标准SSE流式响应
     */
    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> customerServiceStream(@RequestBody ChatRequestDTO request) {
        try {
            log.info("智能客服收到流式请求");

            //消息队列
            List<Message> messages = new ArrayList<>();

            // 添加系统提示词
            String systemPrompt = """
                你是一个专业的智能客服助手，请以友好、专业的方式回答用户的问题。
                回答时请注意：
                1. 语言简洁明了
                2. 态度热情友好
                3. 提供准确有用的信息
                4. 如遇到无法回答的问题，请建议用户联系人工客服
                """;
            messages.add(new SystemMessage(systemPrompt));

            for (ChatMessageDTO messageDTO : request.getMessages()) {
                if ("user".equalsIgnoreCase(messageDTO.getRole())) {
                    messages.add(new UserMessage(messageDTO.getContent()));
                } else if ("assistant".equalsIgnoreCase(messageDTO.getRole())) {
                    messages.add(new AssistantMessage(messageDTO.getContent()));
                }
            }

            // 使用ServerSentEvent包装每个chunk，改为增量传输模式
            return customerChatClient.prompt()
                    .messages(messages)
                    .stream()
                    .content()
                    .map(chunk -> {
                        // 构建标准的ChatResponseDTO，直接发送当前chunk（增量内容）
                        ChatResponseDTO responseDTO = ChatResponseDTO.builder()
                                .reply(chunk)  // 只发送当前chunk，不累积
                                .build();
                        
                        // 将DTO转换为JSON字符串
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            String jsonString = mapper.writeValueAsString(responseDTO);
                            log.debug("发送客服SSE事件，内容长度: {}", jsonString.length());
                            return ServerSentEvent.<String>builder()
                                    .data(jsonString)  // 完整的JSON字符串作为data
                                    .build();
                        } catch (Exception e) {
                            log.error("JSON序列化失败", e);
                            // fallback: 发送原始内容
                            return ServerSentEvent.<String>builder()
                                    .data("{\"reply\":\"" + chunk.replace("\"", "\\\"") + "\"}")
                                    .build();
                        }
                    });

        } catch (Exception e) {
            log.error("智能客服流式服务失败", e);
            return Flux.error(new RuntimeException("AI服务异常: " + e.getMessage()));
        }
    }

    /**
     * 修复Unicode编码问题
     * @param content 原始内容
     * @return 修复后的内容
     */
    private String fixUnicodeEncoding(String content) {
        if (content == null || content.isEmpty()) {
            return content;
        }
        
        try {
            // 方法1: 使用StringEscapeUtils.unescapeJava (如果可用)
            // return StringEscapeUtils.unescapeJava(content);
            
            // 方法2: 手动处理常见的Unicode转义序列
            Pattern unicodePattern = Pattern.compile("\\\\u([0-9a-fA-F]{4})");
            Matcher matcher = unicodePattern.matcher(content);
            StringBuffer sb = new StringBuffer();
            
            while (matcher.find()) {
                try {
                    int codePoint = Integer.parseInt(matcher.group(1), 16);
                    String unicodeChar = new String(Character.toChars(codePoint));
                    matcher.appendReplacement(sb, unicodeChar);
                } catch (Exception e) {
                    // 如果转换失败，保留原始内容
                    matcher.appendReplacement(sb, matcher.group(0));
                }
            }
            matcher.appendTail(sb);
            
            return sb.toString();
            
        } catch (Exception e) {
            log.warn("Unicode编码修复失败，返回原始内容: {}", e.getMessage());
            return content;
        }
    }

//    /**
//     * 常见问题查询
//     *
//     * @param request 用户查询
//     * @return 常见问题答案
//     */
//    @PostMapping("/faq")
//    public Result<ChatResponseDTO> getFAQ(@RequestBody ChatRequestDTO request) {
//        try {
//            log.info("收到常见问题查询请求");
//
//            String systemPrompt = """
//                你是一个FAQ（常见问题）助手，专门回答用户关于Halo刷题网的常见问题。
//                重点关注以下方面：
//                1. 账号注册与登录
//                2. 题库使用方法
//                3. 学习计划创建
//                4. 排行榜规则
//                5. 练习记录查询
//                请给出简洁明了的答案。
//                """;
//
//            List<Message> messages = new ArrayList<>();
//            messages.add(new SystemMessage(systemPrompt));
//
//            for (ChatMessageDTO messageDTO : request.getMessages()) {
//                if ("user".equalsIgnoreCase(messageDTO.getRole())) {
//                    messages.add(new UserMessage(messageDTO.getContent()));
//                }
//            }
//
//            String reply = chatClient.prompt()
//                    .messages(messages)
//                    .call()
//                    .content();
//
//            ChatResponseDTO responseDTO = ChatResponseDTO.builder()
//                    .reply(reply)
//                    .build();
//
//            return Result.ok(responseDTO);
//
//        } catch (Exception e) {
//            log.error("FAQ查询失败", e);
//            return Result.fail("AI服务异常: " + e.getMessage());
//        }
//    }

}
