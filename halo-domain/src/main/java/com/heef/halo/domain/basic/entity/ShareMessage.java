package com.heef.halo.domain.basic.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 消息表(ShareMessage)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShareMessage implements Serializable {
    private static final long serialVersionUID = -66355867773635783L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 来自人
     */
    private String fromId;
    /**
     * 送达人
     */
    private String toId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 是否被阅读 1是 2否
     */
    private Integer isRead;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
    /**
     * 是否被删除 0为删除 1已删除
     */
    private Integer isDeleted;

}
