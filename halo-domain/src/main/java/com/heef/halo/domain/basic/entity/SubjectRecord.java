package com.heef.halo.domain.basic.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * (SubjectRecord)实体类
 *
 * @author heef
 * @since 2025-11-20 11:32:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SubjectRecord implements Serializable {
    private static final long serialVersionUID = -91489601141083217L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 题目ID
     */
    private Long subjectId;
    /**
     * 用户答案
     */
    private String userAnswer;
    /**
     * 是否正确(0:错误,1:正确)
     */
    private Integer isCorrect;
    /**
     * 答题时间
     */
    private Date answerTime;
    /**
     * 答题耗时(秒)
     */
    private Integer timeCost;
    /**
     * 题目得分
     */
    private Integer score;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
