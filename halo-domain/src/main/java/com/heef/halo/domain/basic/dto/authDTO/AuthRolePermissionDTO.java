package com.heef.halo.domain.basic.dto.authDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限关联表(AuthRolePermission)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AuthRolePermissionDTO implements Serializable {
    private static final long serialVersionUID = 184184539973999021L;

    private Long id;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 权限id
     */
    private Long permissionId;
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
