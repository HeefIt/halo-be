package com.heef.halo.domain.AOpenAiPractice.openAiConfig;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 自定义大模型聊天对话配置
 *
 * @author kyrie.huang
 * @date 2026/2/24 17:48
 */
@Configuration
public class ClientConfiguration {

    //OpenAiChatModel这个是yml文件里面的spring ai配置注入的文件
    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel, ChatMemoryRepository chatMemoryRepository) {
        return ChatClient
                .builder(openAiChatModel)
                //定义系统模型提示词角色--
                .defaultSystem("你是一个刷题智能助手,精通java后端开发的所有知识点,你的名字叫做kyrie.huang,请以专业的开发人员的语气来回答相关问题")
                //定义日志通知(在spring ai 中提供基于aop的环绕通知--可以记录ai对话之间的日志)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor())
                .build();
    }
    
    // 配置内存聊天记忆存储
    @Bean
    public ChatMemoryRepository chatMemoryRepository() {
        return new InMemoryChatMemoryRepository();
    }
}
