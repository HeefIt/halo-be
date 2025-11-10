package com.heef.halo.domain.basic.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 套题信息表(PracticeSet)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PracticeSet implements Serializable {
    private static final long serialVersionUID = -58723541122424249L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 套题名称
     */
    private String setName;
    /**
     * 套题类型 1实时生成 2预设套题
     */
    private Integer setType;
    /**
     * 热度
     */
    private Integer setHeat;
    /**
     * 套题描述
     */
    private String setDesc;
    /**
     * 大类id
     */
    private Long primaryCategoryId;
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
