package com.heef.halo.domain.config.mybatisplus;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.heef.halo.domain.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * MyBatis Plus字段自动填充配置类
 * 用于自动填充创建时间和更新时间等字段
 */
/**
 * MyBatis Plus字段自动填充配置类
 * 用于自动填充创建时间和更新时间等字段---要使其生效需要在对应的实体类上加注解@TableField(fill= 1~2~3~4)
 * 1: FieldFill.INSERT	仅在插入时填充
 * 2: FieldFill.UPDATE	仅在更新时填充
 * 3: FieldFill.INSERT_UPDATE	插入和更新时都填充
 * 4: FieldFill.DEFAULT	不填充
 *
 * @author heefM
 * @date 2025-11-21
 */
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private UserUtils userUtils;

    /**
     * 插入时自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始执行insert自动填充...");
        try {
            // 自动填充创建时间
            this.strictInsertFill(metaObject, "createdTime", Date.class, new Date());
            // 自动填充创建人
            String currentUser = getCurrentUser();
            this.strictInsertFill(metaObject, "createdBy", String.class, currentUser);
            // 自动填充更新时间
            this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
            // 自动填充更新人
            this.strictInsertFill(metaObject, "updateBy", String.class, currentUser);
            
            log.info("insert自动填充完成: createdTime={}, createdBy={}, updateTime={}, updateBy={}", 
                metaObject.getValue("createdTime"), 
                metaObject.getValue("createdBy"),
                metaObject.getValue("updateTime"), 
                metaObject.getValue("updateBy"));
        } catch (Exception e) {
            log.error("insert自动填充异常: ", e);
        }
    }

    /**
     * 更新时自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始执行update自动填充...");
        try {
            // 自动填充更新时间
            this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
            // 自动填充更新人
            String currentUser = getCurrentUser();
            this.strictUpdateFill(metaObject, "updateBy", String.class, currentUser);
            
            log.info("update自动填充完成: updateTime={}, updateBy={}", 
                metaObject.getValue("updateTime"), 
                metaObject.getValue("updateBy"));
        } catch (Exception e) {
            log.error("update自动填充异常: ", e);
        }
    }

    /**
     * 获取当前用户信息
     * 实际使用时可根据项目认证方式获取当前用户
     * @return
     */
    private String getCurrentUser() {
        try {
            if (StpUtil.isLogin()) {
                String loginId = StpUtil.getLoginIdAsString();
                log.debug("获取到当前登录用户ID: {}", loginId);
                if (userUtils != null) {
                    String userName = userUtils.getUserName(loginId);
                    log.debug("获取到用户名: {}", userName);
                    return userName != null ? userName : "unknownUser";
                } else {
                    log.warn("UserUtils 未注入成功");
                    return "system";
                }
            } else {
                log.warn("用户未登录，使用默认值");
                return "anonymous";
            }
        } catch (Exception e) {
            log.error("获取当前用户信息异常: ", e);
            return "exceptionSystem";
        }
    }

}