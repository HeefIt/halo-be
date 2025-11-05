package com.heef.halo.domain.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heef.halo.domain.basic.entity.AuthUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户角色表(AuthUserRole)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:45
 */
@Mapper
public interface AuthUserRoleMapper extends BaseMapper<AuthUserRole> {

    /**
     * 分页查询用户角色表数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 用户角色表数据列表
     */
    List<AuthUserRole> selectPage(@Param("param") AuthUserRole param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询用户角色表列表
     *
     * @param param 查询参数
     * @return 用户角色表数据列表
     */
    List<AuthUserRole> selectList(@Param("param") AuthUserRole param);

    /**
     * 根据ID查询用户角色表详情
     *
     * @param id 用户角色表ID
     * @return 用户角色表详情信息
     */
    AuthUserRole selectById(@Param("id") Long id);

    /**
     * 新增用户角色表数据
     *
     * @param entity 用户角色表实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") AuthUserRole entity);

    /**
     * 批量新增用户角色表数据
     *
     * @param list 用户角色表实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<AuthUserRole> list);

    /**
     * 更新用户角色表数据
     *
     * @param entity 用户角色表实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") AuthUserRole entity);

    /**
     * 根据ID删除用户角色表数据
     *
     * @param id 用户角色表ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除用户角色表数据
     *
     * @param ids 用户角色表ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计用户角色表数据数量
     *
     * @param param 查询参数
     * @return 用户角色表数据总数
     */
    Long count(@Param("param") AuthUserRole param);

    /**
     * 根据ID判断用户角色表是否存在
     *
     * @param id 用户角色表ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);
}
