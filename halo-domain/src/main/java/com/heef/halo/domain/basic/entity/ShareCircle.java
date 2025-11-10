package com.heef.halo.domain.basic.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 圈子信息(ShareCircle)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShareCircle implements Serializable {
    private static final long serialVersionUID = -98583474760450980L;
    /**
     * 圈子ID
     */
    private Long id;
    /**
     * 父级ID,-1为大类
     */
    private Long parentId;
    /**
     * 圈子名称
     */
    private String circleName;
    /**
     * 圈子图片
     */
    private String icon;
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
