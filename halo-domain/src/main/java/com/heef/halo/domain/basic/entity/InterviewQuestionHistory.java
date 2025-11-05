package com.heef.halo.domain.basic.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * (InterviewQuestionHistory)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InterviewQuestionHistory implements Serializable {
    private static final long serialVersionUID = -66643631377973937L;

    private Integer id;

    private Integer interviewId;

    private Object score;

    private String keyWords;

    private String question;

    private String answer;

    private String userAnswer;

    private String createdBy;

    private Date createdTime;

    private String updateBy;

    private Date updateTime;

    private Integer isDeleted;

}
