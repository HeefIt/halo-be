package com.heef.halo.domain.basic.dto.authDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * (AuthRole)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AuthRoleDTO implements Serializable {
    private static final long serialVersionUID = -39620362545696506L;

    private Long id;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色唯一标识
     */
    private String roleKey;
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
     * 是否被删除 0未删除 1已删除
     */
    private Integer isDeleted;

}
