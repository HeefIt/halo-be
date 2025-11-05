package com.heef.halo.domain.basic.mapper;

import com.heef.halo.domain.basic.entity.AuthRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色权限关联表(AuthRolePermission)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:44
 */
@Mapper
public interface AuthRolePermissionMapper {

    /**
     * 分页查询角色权限关联表数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 角色权限关联表数据列表
     */
    List<AuthRolePermission> selectPage(@Param("param") AuthRolePermission param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询角色权限关联表列表
     *
     * @param param 查询参数
     * @return 角色权限关联表数据列表
     */
    List<AuthRolePermission> selectList(@Param("param") AuthRolePermission param);

    /**
     * 根据ID查询角色权限关联表详情
     *
     * @param id 角色权限关联表ID
     * @return 角色权限关联表详情信息
     */
    AuthRolePermission selectById(@Param("id") Long id);

    /**
     * 新增角色权限关联表数据
     *
     * @param entity 角色权限关联表实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") AuthRolePermission entity);

    /**
     * 批量新增角色权限关联表数据
     *
     * @param list 角色权限关联表实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<AuthRolePermission> list);

    /**
     * 更新角色权限关联表数据
     *
     * @param entity 角色权限关联表实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") AuthRolePermission entity);

    /**
     * 根据ID删除角色权限关联表数据
     *
     * @param id 角色权限关联表ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除角色权限关联表数据
     *
     * @param ids 角色权限关联表ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计角色权限关联表数据数量
     *
     * @param param 查询参数
     * @return 角色权限关联表数据总数
     */
    Long count(@Param("param") AuthRolePermission param);

    /**
     * 根据ID判断角色权限关联表是否存在
     *
     * @param id 角色权限关联表ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);
}
