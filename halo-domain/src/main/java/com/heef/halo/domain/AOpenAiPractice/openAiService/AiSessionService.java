package com.heef.halo.domain.AOpenAiPractice.openAiService;

import com.heef.halo.domain.AOpenAiPractice.openAiDto.SessionDTO;

import java.util.List;

/**
 * AI会话管理服务接口
 *
 * @author kyrie.huang
 * @date 2026/2/25 15:35
 */
public interface AiSessionService {
    
    /**
     * 创建新会话
     *
     * @param userId 用户ID
     * @param title 会话标题
     * @return 会话DTO
     */
    SessionDTO createSession(Long userId, String title);
    
    /**
     * 获取用户的所有会话
     *
     * @param userId 用户ID
     * @return 会话列表
     */
    List<SessionDTO> getUserSessions(Long userId);
    
    /**
     * 根据会话ID获取会话详情
     *
     * @param sessionId 会话ID
     * @param userId 用户ID
     * @return 会话DTO
     */
    SessionDTO getSessionById(String sessionId, Long userId);
    
    /**
     * 更新会话
     *
     * @param sessionDTO 会话DTO
     * @return 是否成功
     */
    boolean updateSession(SessionDTO sessionDTO);
    
    /**
     * 删除会话
     *
     * @param sessionId 会话ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean deleteSession(String sessionId, Long userId);
    
    /**
     * 清理会话消息（保留会话但清除消息历史）
     *
     * @param sessionId 会话ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean clearSessionMessages(String sessionId, Long userId);
}