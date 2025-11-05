package com.heef.halo.domain.basic.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * (InterviewHistory)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:46
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InterviewHistory implements Serializable {
    private static final long serialVersionUID = -75041781637152070L;

    private Integer id;

    private Object avgScore;

    private String keyWords;

    private String tip;

    private String interviewUrl;

    private String createdBy;

    private Date createdTime;

    private String updateBy;

    private Date updateTime;

    private Integer isDeleted;

}
