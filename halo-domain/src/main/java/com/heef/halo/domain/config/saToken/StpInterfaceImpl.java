package com.heef.halo.domain.config.saToken;

import cn.dev33.satoken.stp.StpInterface;
import com.heef.halo.domain.basic.entity.AuthPermission;
import com.heef.halo.domain.basic.entity.AuthRole;
import com.heef.halo.domain.basic.entity.AuthUser;
import com.heef.halo.domain.basic.mapper.AuthPermissionMapper;
import com.heef.halo.domain.basic.mapper.AuthRoleMapper;
import com.heef.halo.domain.basic.mapper.AuthUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 自定义权限验证接口扩展
 * Sa-Token 将从此实现类获取每个账号拥有的权限码和角色
 * @author heefM
 * @date 2025-10-
 */
@Slf4j
@Component
public class StpInterfaceImpl implements StpInterface {
    
    @Autowired
    private AuthUserMapper authUserMapper;

    @Autowired
    private AuthRoleMapper authRoleMapper;

    @Autowired
    private AuthPermissionMapper authPermissionMapper;
    /**
     * 获取权限信息
     * @param loginId
     * @param loginType
     * @return
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 暂时返回空列表，后续可扩展实现权限码控制
        List<String> permissions = getPermissionsByUserId(Long.valueOf(loginId.toString()));
        log.info("用户ID: {} 的权限列表: {}", loginId, permissions); // 添加日志
        return permissions;
    }

    /**
     * 获取角色信息
     * @param loginId
     * @param loginType
     * @return
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roles = getRolesByUserId(Long.valueOf(loginId.toString()));
        log.info("用户ID: {} 的角色列表: {}", loginId, roles); // 添加日志
        return roles;
    }

    /**
     * 根据用户ID查询角色列表
     */
    private List<String> getRolesByUserId(Long userId) {
        try {
            List<AuthRole> roleList = authRoleMapper.selectRolesByUserId(userId);
//            return roleList.stream()
//                    .map(AuthRole::getRoleKey)
//                    .collect(Collectors.toList());
            return roleList.stream()
                    .map(AuthRole::getRoleKey)  // 返回 role_key: "admin_user", "normal_user"
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询用户角色列表失败, userId: {}", userId, e);
            return Collections.emptyList();
        }
    }

    /**
     * 根据用户ID查询权限列表
     */
    private List<String> getPermissionsByUserId(Long userId) {
        try {
            List<AuthPermission> permissionList = authPermissionMapper.selectPermissionsByUserId(userId);
//            return permissionList.stream()
//                    .map(AuthPermission::getName)
//                    .collect(Collectors.toList());
            return permissionList.stream()
                    .map(AuthPermission::getPermissionKey)  // ★★★ 重要：改为返回 permission_key
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询用户权限列表失败, userId: {}", userId, e);
            return Collections.emptyList();
        }
    }


}