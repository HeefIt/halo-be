package com.heef.halo.domain.basic.mapper;

import com.heef.halo.domain.basic.entity.AuthRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (AuthRole)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:44
 */
@Mapper
public interface AuthRoleMapper {

    /**
     * 分页查询${tableInfo.comment}数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return ${tableInfo.comment}数据列表
     */
    List<AuthRole> selectPage(@Param("param") AuthRole param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询${tableInfo.comment}列表
     *
     * @param param 查询参数
     * @return ${tableInfo.comment}数据列表
     */
    List<AuthRole> selectList(@Param("param") AuthRole param);

    /**
     * 根据ID查询${tableInfo.comment}详情
     *
     * @param id ${tableInfo.comment}ID
     * @return ${tableInfo.comment}详情信息
     */
    AuthRole selectById(@Param("id") Long id);

    /**
     * 新增${tableInfo.comment}数据
     *
     * @param entity ${tableInfo.comment}实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") AuthRole entity);

    /**
     * 批量新增${tableInfo.comment}数据
     *
     * @param list ${tableInfo.comment}实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<AuthRole> list);

    /**
     * 更新${tableInfo.comment}数据
     *
     * @param entity ${tableInfo.comment}实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") AuthRole entity);

    /**
     * 根据ID删除${tableInfo.comment}数据
     *
     * @param id ${tableInfo.comment}ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除${tableInfo.comment}数据
     *
     * @param ids ${tableInfo.comment}ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计${tableInfo.comment}数据数量
     *
     * @param param 查询参数
     * @return ${tableInfo.comment}数据总数
     */
    Long count(@Param("param") AuthRole param);

    /**
     * 根据ID判断${tableInfo.comment}是否存在
     *
     * @param id ${tableInfo.comment}ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);
}
