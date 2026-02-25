package com.heef.halo.domain.AOpenAiPractice.openAiController;

import com.heef.halo.domain.AOpenAiPractice.openAiDto.*;
import com.heef.halo.result.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 多模态解析控制器
 * 功能：支持PDF、图片等多模态文件的智能解析
 *
 * @author kyrie.huang
 * @date 2026/2/25
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ai/multimodal")
@RestController
public class MultimodalParserController {

    private final ChatClient chatClient;

    /**
     * 文本解析
     *
     * @param request 包含文本内容的请求
     * @return 解析结果
     */
    @PostMapping("/parse/text")
    public Result<ChatResponseDTO> parseText(@RequestBody ChatRequestDTO request) {
        try {
            log.info("收到文本解析请求");

            String systemPrompt = """
                你是一个智能文本解析助手，能够分析和理解各种类型的文本内容。
                请对用户提供的文本进行分析，提供：
                1. 内容摘要
                2. 关键信息提取
                3. 重点内容标注
                4. 相关建议
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
            log.error("文本解析失败", e);
            return Result.fail("AI服务异常: " + e.getMessage());
        }
    }

    /**
     * 文件上传解析（PDF、图片等）
     * 注意：此功能需要Spring AI的多模态支持
     *
     * @param file 上传的文件
     * @param prompt 用户的解析要求
     * @return 解析结果
     */
    @PostMapping("/parse/file")
    public Result<ChatResponseDTO> parseFile(
            @RequestPart("file") MultipartFile file,
            @RequestPart("prompt") String prompt) {
        try {
            log.info("收到文件解析请求，文件名: {}", file.getOriginalFilename());

            // 检查文件类型
            String contentType = file.getContentType();
            if (contentType == null || (!contentType.startsWith("image/") && !contentType.equals("application/pdf"))) {
                return Result.fail("仅支持图片和PDF文件");
            }

            // 构建系统提示词
            String systemPrompt = """
                你是一个多模态文件解析助手，能够分析PDF文档和图片内容。
                请仔细分析用户上传的文件，提供：
                1. 文件内容概述
                2. 重要信息提取
                3. 结构化整理
                4. 相关问题解答
                """;

            List<Message> messages = new ArrayList<>();
            messages.add(new SystemMessage(systemPrompt));

            // 注意：实际的多模态解析需要Spring AI的GPT-4 Vision等模型支持
            // 这里先用文本方式处理，实际需要配置支持多模态的模型
            String userPrompt = "请分析以下内容：" + prompt;
            messages.add(new UserMessage(userPrompt));

            String reply = chatClient.prompt()
                    .messages(messages)
                    .call()
                    .content();

            ChatResponseDTO responseDTO = ChatResponseDTO.builder()
                    .reply(reply)
                    .build();

            return Result.ok(responseDTO);

        } catch (Exception e) {
            log.error("文件解析失败", e);
            return Result.fail("AI服务异常: " + e.getMessage());
        }
    }

    /**
     * 图片内容识别
     *
     * @param request 包含图片描述或上传图片信息的请求
     * @return 识别结果
     */
    @PostMapping("/recognize/image")
    public Result<ChatResponseDTO> recognizeImage(@RequestBody ChatRequestDTO request) {
        try {
            log.info("收到图片识别请求");

            String systemPrompt = """
                你是一个智能图片识别助手，能够理解和分析图片内容。
                请对用户提供的图片信息进行识别和分析，提供：
                1. 图片内容描述
                2. 识别到的文字内容
                3. 图表数据分析（如有）
                4. 相关信息提取
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
            log.error("图片识别失败", e);
            return Result.fail("AI服务异常: " + e.getMessage());
        }
    }

}
