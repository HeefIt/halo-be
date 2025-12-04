package com.heef.halo.domain.basic.controller;

import com.heef.halo.domain.basic.dto.openAIDTO.RequestChatDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * ai大模型调用controller
 *
 * @author heefM
 * @date 2025-12-04
 */
@RestController
@RequestMapping("/api/openAI")
@Slf4j
public class OpenAIController {


    @Autowired
    private ChatModel chatModel;

    /**
     * 测试DeepSeek模型
     */
    @PostMapping("/deepSeek/chat")
    public String testDeepSeek(@RequestBody RequestChatDTO requestChatDTO) {
        try {
            ChatResponse response = chatModel.call(
                new Prompt(new UserMessage(requestChatDTO.getMessage()))
            );
            log.info("testDeepSeek: {}",response.getResult().getOutput().getText());
            return response.getResult().getOutput().getText();
        } catch (Exception e) {
            return "DeepSeek调用失败: " + e.getMessage();
        }
    }

    /**
     * 测试通义千问模型
     */
    @PostMapping("/qwen")
    public String testQwen(@RequestBody RequestChatDTO requestChatDTO) {
        try {
            ChatResponse response = chatModel.call(
                new Prompt(new UserMessage(requestChatDTO.getMessage()))
            );
            log.info("testQwen: {}",response.getResult().getOutput().getText());
            return response.getResult().getOutput().getText();

        } catch (Exception e) {
            return "通义千问调用失败: " + e.getMessage();
        }
    }

    /**
     * 同时测试两个模型
     */
    @PostMapping("/compare")
    public Map<String, Object> compareModels(@RequestBody RequestChatDTO requestChatDTO) {
        long startTime1 = System.currentTimeMillis();
        String deepseekResponse = testDeepSeek(requestChatDTO);
        long deepseekTime = System.currentTimeMillis() - startTime1;

        long startTime2 = System.currentTimeMillis();
        String qwenResponse = testQwen(requestChatDTO);
        long qwenTime = System.currentTimeMillis() - startTime2;

        return Map.of(
            "deepseek", Map.of(
                "response", deepseekResponse,
                "time", deepseekTime + "ms"
            ),
            "qwen", Map.of(
                "response", qwenResponse,
                "time", qwenTime + "ms"
            ),
            "question", requestChatDTO.getMessage()
        );
    }

    /**
     * DeepSeek流式响应
     */
    @GetMapping("/deepSeek/stream/chat")
    public Flux<String> deepSeekStream(@RequestParam String message) {
        return chatModel.stream(new Prompt(new UserMessage(message)))
                .map(chatResponse -> chatResponse.getResult().getOutput().getText());
    }

}