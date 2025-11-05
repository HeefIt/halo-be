package com.heef.halo.domain.basic.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 判断题(SubjectJudge)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SubjectJudge implements Serializable {
    private static final long serialVersionUID = -28180212307904275L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 题目id
     */
    private Long subjectId;
    /**
     * 是否正确
     */
    private Integer isCorrect;
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
