package com.heef.halo.domain.basic.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 题目点赞表(SubjectLiked)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SubjectLiked implements Serializable {
    private static final long serialVersionUID = 446019137606238614L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 题目id
     */
    private Long subjectId;
    /**
     * 点赞人id
     */
    private String likeUserId;
    /**
     * 点赞状态 1点赞 0不点赞
     */
    private Integer status;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private Date updateTime;

    private Integer isDeleted;

}
