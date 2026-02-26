package com.heef.halo.domain.AOpenAiPractice.openAiDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI聊天响应DTO
 *
 * @author kyrie.huang
 * @date 2026/2/25 10:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponseDTO {

    private String reply;
    private String sessionId;  // 新增：返回会话ID给前端
}
