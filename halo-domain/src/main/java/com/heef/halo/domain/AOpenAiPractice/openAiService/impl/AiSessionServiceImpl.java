package com.heef.halo.domain.AOpenAiPractice.openAiService.impl;

import com.heef.halo.domain.AOpenAiPractice.openAiDto.ChatMessageDTO;
import com.heef.halo.domain.AOpenAiPractice.openAiDto.SessionDTO;
import com.heef.halo.domain.AOpenAiPractice.openAiService.AiSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AI会话管理服务实现类
 * 使用内存存储实现会话管理（生产环境建议使用Redis或数据库）
 *
 * @author kyrie.huang
 * @date 2026/2/25 15:40
 */
@Slf4j
@Service
public class AiSessionServiceImpl implements AiSessionService {
    
    // 内存存储：用户ID -> 会话列表
    private final Map<Long, List<SessionDTO>> userSessions = new ConcurrentHashMap<>();
    
    // 内存存储：会话ID -> 会话详情
    private final Map<String, SessionDTO> sessionMap = new ConcurrentHashMap<>();
    
    @Override
    public SessionDTO createSession(Long userId, String title) {
        String sessionId = UUID.randomUUID().toString();
        Date now = new Date();
        
        SessionDTO session = SessionDTO.builder()
                .sessionId(sessionId)
                .userId(userId)
                .title(title != null && !title.isEmpty() ? title : "新对话")
                .createdAt(now)
                .updatedAt(now)
                .messages(new ArrayList<>())
                .build();
        
        // 存储会话
        sessionMap.put(sessionId, session);
        
        // 添加到用户会话列表
        userSessions.computeIfAbsent(userId, k -> new ArrayList<>()).add(0, session);
        
        log.info("创建新会话: sessionId={}, userId={}", sessionId, userId);
        return session;
    }
    
    @Override
    public List<SessionDTO> getUserSessions(Long userId) {
        List<SessionDTO> sessions = userSessions.getOrDefault(userId, new ArrayList<>());
        // 按更新时间倒序排列
        sessions.sort((s1, s2) -> s2.getUpdatedAt().compareTo(s1.getUpdatedAt()));
        return sessions;
    }
    
    @Override
    public SessionDTO getSessionById(String sessionId, Long userId) {
        SessionDTO session = sessionMap.get(sessionId);
        if (session != null && session.getUserId().equals(userId)) {
            return session;
        }
        return null;
    }
    
    @Override
    public boolean updateSession(SessionDTO sessionDTO) {
        if (sessionDTO == null || sessionDTO.getSessionId() == null) {
            log.warn("更新会话失败：sessionDTO为空或sessionId为空");
            return false;
        }
        
        SessionDTO existingSession = sessionMap.get(sessionDTO.getSessionId());
        if (existingSession == null) {
            log.warn("更新会话失败：会话不存在，sessionId={}", sessionDTO.getSessionId());
            return false;
        }
        
        if (!existingSession.getUserId().equals(sessionDTO.getUserId())) {
            log.warn("更新会话失败：用户无权限，sessionId={}, existingUserId={}, requestUserId={}", 
                    sessionDTO.getSessionId(), existingSession.getUserId(), sessionDTO.getUserId());
            return false;
        }
        
        // 更新会话信息
        existingSession.setTitle(sessionDTO.getTitle() != null ? sessionDTO.getTitle() : existingSession.getTitle());
        
        // 安全地更新消息列表
        if (sessionDTO.getMessages() != null) {
            existingSession.setMessages(new ArrayList<>(sessionDTO.getMessages()));
            log.info("更新会话消息：sessionId={}, 消息数量={}", sessionDTO.getSessionId(), sessionDTO.getMessages().size());
        } else {
            log.info("更新会话标题：sessionId={}, 新标题={}", sessionDTO.getSessionId(), sessionDTO.getTitle());
        }
        
        existingSession.setUpdatedAt(new Date());
        
        // 更新用户会话列表中的顺序
        List<SessionDTO> userSessionList = userSessions.get(existingSession.getUserId());
        if (userSessionList != null) {
            userSessionList.remove(existingSession);
            userSessionList.add(0, existingSession);
        }
        
        log.info("更新会话成功: sessionId={}, userId={}", sessionDTO.getSessionId(), sessionDTO.getUserId());
        return true;
    }
    
    @Override
    public boolean deleteSession(String sessionId, Long userId) {
        SessionDTO session = sessionMap.get(sessionId);
        if (session == null || !session.getUserId().equals(userId)) {
            return false;
        }
        
        // 从会话映射中删除
        sessionMap.remove(sessionId);
        
        // 从用户会话列表中删除
        List<SessionDTO> userSessionList = userSessions.get(userId);
        if (userSessionList != null) {
            userSessionList.removeIf(s -> s.getSessionId().equals(sessionId));
        }
        
        log.info("删除会话: sessionId={}, userId={}", sessionId, userId);
        return true;
    }
    
    @Override
    public boolean clearSessionMessages(String sessionId, Long userId) {
        SessionDTO session = sessionMap.get(sessionId);
        if (session == null || !session.getUserId().equals(userId)) {
            return false;
        }
        
        // 清空消息列表
        session.setMessages(new ArrayList<>());
        session.setUpdatedAt(new Date());
        
        log.info("清空会话消息: sessionId={}, userId={}", sessionId, userId);
        return true;
    }
    
    /**
     * 添加消息到会话
     *
     * @param sessionId 会话ID
     * @param userId 用户ID
     * @param message 消息
     * @return 是否成功
     */
    public boolean addMessageToSession(String sessionId, Long userId, ChatMessageDTO message) {
        SessionDTO session = getSessionById(sessionId, userId);
        if (session == null) {
            return false;
        }
        
        session.getMessages().add(message);
        session.setUpdatedAt(new Date());
        
        // 更新用户会话列表中的顺序
        List<SessionDTO> userSessionList = userSessions.get(userId);
        if (userSessionList != null) {
            userSessionList.remove(session);
            userSessionList.add(0, session);
        }
        
        return true;
    }
    
    /**
     * 获取会话的消息历史
     *
     * @param sessionId 会话ID
     * @param userId 用户ID
     * @return 消息列表
     */
    public List<ChatMessageDTO> getSessionMessages(String sessionId, Long userId) {
        SessionDTO session = getSessionById(sessionId, userId);
        return session != null ? session.getMessages() : new ArrayList<>();
    }
}