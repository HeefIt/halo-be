package com.heef.halo.domain.basic.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 简答题(SubjectBrief)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SubjectBrief implements Serializable {
    private static final long serialVersionUID = 394979806597993685L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 题目id
     */
    private Integer subjectId;
    /**
     * 题目答案
     */
    private String subjectAnswer;
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

    private Integer isDeleted;

}
