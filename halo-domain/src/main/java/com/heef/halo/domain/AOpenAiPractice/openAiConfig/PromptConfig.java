package com.heef.halo.domain.AOpenAiPractice.openAiConfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 提示词配置--结合yml文件来自动切换(目前无用--2026/02/26)
 *
 * @author kyrie.huang
 * @date 2026/2/26 10:45
 */
@Data
@Component
@ConfigurationProperties(prefix = "ai.prompts")
public class PromptConfig {

    /**
     * 默认系统提示词
     */
    private String system = "你是一个智能AI助手";  // 提供默认值

    /**
     * 角色特定提示词
     */
    private Map<String, String> roles = new HashMap<>();

    /**
     * 根据角色名称获取提示词
     */
    public String getRolePrompt(String role) {
        return roles.getOrDefault(role, system);
    }
}