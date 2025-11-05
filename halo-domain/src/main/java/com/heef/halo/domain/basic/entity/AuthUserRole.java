package com.heef.halo.domain.basic.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户角色表(AuthUserRole)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AuthUserRole implements Serializable {
    private static final long serialVersionUID = 647181091372773553L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 角色id
     */
    private Long roleId;
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
