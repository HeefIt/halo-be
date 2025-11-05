package com.heef.halo.domain.basic.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 练习表(PracticeInfo)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PracticeInfo implements Serializable {
    private static final long serialVersionUID = 754502855345580887L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 套题id
     */
    private Long setId;
    /**
     * 是否完成 1完成 0未完成
     */
    private Integer completeStatus;
    /**
     * 用时
     */
    private String timeUse;
    /**
     * 交卷时间
     */
    private Date submitTime;
    /**
     * 正确率
     */
    private Double correctRate;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否被删除 0为删除 1已删除
     */
    private Integer isDeleted;

}
