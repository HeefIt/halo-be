package com.heef.halo.domain.basic.dto.openAIDTO;

import lombok.Data;

/**
 * 大模型请求参数DTO
 *
 * @author heefM
 * @date 2025-11-21
 */
@Data
public class RequestChatDTO {

    /**
     * 请求消息实体
     */
    private String message;
}
