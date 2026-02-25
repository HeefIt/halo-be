package com.heef.halo.domain.AOpenAiPractice.openAiDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AI聊天消息DTO
 *
 * @author kyrie.huang
 * @date 2026/2/25 10:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {

    private String role;
    private String content;
    private String timestamp;
}
