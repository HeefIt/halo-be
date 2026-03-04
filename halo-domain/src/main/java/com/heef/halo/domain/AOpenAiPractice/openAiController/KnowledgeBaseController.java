package com.heef.halo.domain.AOpenAiPractice.openAiController;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RAG-知识增强相关controller
 *
 * @author kyrie.huang
 * @date 2026/3/2 14:20
 */
@Data
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai/knowledge")
public class KnowledgeBaseController {

    @Autowired
    private ChatClient ragChatClient;


    /**
     *
     */
    @RequestMapping("/rag")
    public String query(String question) {
        return null;
    }
}
