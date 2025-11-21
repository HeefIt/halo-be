package com.heef.halo.domain.util;

import com.heef.halo.domain.basic.entity.AuthUser;
import com.heef.halo.domain.basic.mapper.AuthUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户工具类
 *
 * @author heefM
 * @date 2025-11-21
 */
@Slf4j
@Component
public class UserUtils {

    @Autowired
    private AuthUserMapper authUserMapper;

    
    /**
     * 根据登录用户id获取用户名称
     * @param loginId
     * @return
     */
    public String getUserName(String loginId){
        try {
            if (loginId == null || loginId.isEmpty()) {
                log.warn("登录ID为空");
                return "anonymous";
            }
            
            AuthUser authUser = authUserMapper.selectById(loginId);
            if (authUser != null) {
                String userName = authUser.getUserName();
                log.debug("根据ID {} 获取到用户名: {}", loginId, userName);
                return userName != null ? userName : "unknownUser";
            } else {
                log.warn("未找到ID为 {} 的用户", loginId);
                return "unknownUser";
            }
        } catch (Exception e) {
            log.error("根据登录ID获取用户名异常, loginId: {}", loginId, e);
            return "exceptionUser";
        }
    }
    
}