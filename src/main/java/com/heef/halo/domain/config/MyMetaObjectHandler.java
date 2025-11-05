package com.heef.halo.domain.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * MyBatis Plus字段自动填充配置类
 * 用于自动填充创建时间和更新时间等字段
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入时自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始执行insert自动填充...");
        // 自动填充创建时间
        this.strictInsertFill(metaObject, "createdTime", Date.class, new Date());
        // 自动填充更新时间
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        // 自动填充创建人 (这里可以根据实际需求从上下文中获取当前用户)
        // this.strictInsertFill(metaObject, "createdBy", String.class, getCurrentUser());
        // 自动填充更新人
        // this.strictInsertFill(metaObject, "updateBy", String.class, getCurrentUser());
    }

    /**
     * 更新时自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始执行update自动填充...");
        // 自动填充更新时间
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
        // 自动填充更新人
        // this.strictUpdateFill(metaObject, "updateBy", String.class, getCurrentUser());
    }

    /**
     * 获取当前用户信息（示例方法）
     * 实际使用时可根据项目认证方式获取当前用户
     * @return
     */
    /*
    private String getCurrentUser() {
        try {
            // 根据项目实际使用的认证框架获取当前用户
            // 例如使用Sa-Token:
            // return StpUtil.getLoginIdAsString();
            return "system";
        } catch (Exception e) {
            return "system";
        }
    }
    */
}