package com.heef.halo.domain.basic.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 练习详情表(PracticeDetail)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PracticeDetail implements Serializable {
    private static final long serialVersionUID = -11150916828877557L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 练题id
     */
    private Long practiceId;
    /**
     * 题目id
     */
    private Long subjectId;
    /**
     * 题目类型
     */
    private Integer subjectType;
    /**
     * 回答状态
     */
    private Integer answerStatus;
    /**
     * 回答内容
     */
    private String answerContent;
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
