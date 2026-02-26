package com.heef.halo.domain.AOpenAiPractice.openAiConfig;

import com.heef.halo.domain.AOpenAiPractice.openAiConstantSystem.SystemConstants;
import com.heef.halo.domain.AOpenAiPractice.openAiTools.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义大模型聊天对话配置(定义多个chatClient--来实现多种角色,当接口需要调用时候,可以根据chatClient名称来选择调用那些)
 *
 * @author kyrie.huang
 * @date 2026/2/24 17:48
 */
@Configuration
@RequiredArgsConstructor
public class ClientConfiguration {


    @Autowired
    private  ChatMemory chatMemory;

//    @Autowired
//    private ChatMemoryRepository chatMemoryRepository; //默认使用内存存储


    /**
     * 机器人对话client
     * @param openAiChatModel
     * @param chatMemory
     * @return
     */
    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        return ChatClient
                .builder(openAiChatModel)
                .defaultSystem(SystemConstants.SYSTEM_CLIENT_PROMPT)
                .defaultAdvisors(
                        //会话记忆advisors
                        MessageChatMemoryAdvisor.builder(chatMemory).build(), // ✅ Builder方式  之前的new MessageChatMemoryAdvisor(chatMemory)已废弃
                        //日志advisors
                        new SimpleLoggerAdvisor()
                )
                .build();
    }


    /**
     * 智能客服角色Client
     * @param openAiChatModel
     * @param chatMemory
     * @param chatTools
     * @return
     */
    @Bean
    public ChatClient customerChatClient(OpenAiChatModel openAiChatModel,
                                         ChatMemory chatMemory,
                                         List<ChatTools> chatTools
    ) {

        // 将List<ChatTool>转换为ToolCallback数组
        ToolCallback[] toolCallbacks = chatTools.stream()
                .flatMap(tool -> Arrays.stream(ToolCallbacks.from(tool)))  // 每个工具类可能包含多个@Tool方法
                .toArray(ToolCallback[]::new);

        return ChatClient
                .builder(openAiChatModel)
                .defaultSystem(SystemConstants.WEB_CLIENT_PROMPT)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor()
                )
                //工具tools
                .defaultToolCallbacks(toolCallbacks)
                .build();
    }
}