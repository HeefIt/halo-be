package com.heef.halo.domain.AOpenAiPractice.openAiDto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 会话DTO实体类
 *
 * @author kyrie.huang
 * @date 2026/2/25 15:30
 */
@Data
@Builder
@JsonDeserialize(builder = SessionDTO.SessionDTOBuilder.class)
public class SessionDTO {
    
    @JsonPOJOBuilder(withPrefix = "")
    public static class SessionDTOBuilder {}
    
    
    /**
     * 会话ID
     */
    private String sessionId;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 会话标题
     */
    private String title;
    
    /**
     * 创建时间
     */
    private Date createdAt;
    
    /**
     * 更新时间
     */
    private Date updatedAt;
    
    /**
     * 会话消息列表
     */
    private List<ChatMessageDTO> messages;
}