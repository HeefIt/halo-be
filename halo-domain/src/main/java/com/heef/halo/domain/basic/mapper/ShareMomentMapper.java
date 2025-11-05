package com.heef.halo.domain.basic.mapper;

import com.heef.halo.domain.basic.entity.ShareMoment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 动态信息(ShareMoment)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:50
 */
@Mapper
public interface ShareMomentMapper {

    /**
     * 分页查询动态信息数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 动态信息数据列表
     */
    List<ShareMoment> selectPage(@Param("param") ShareMoment param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询动态信息列表
     *
     * @param param 查询参数
     * @return 动态信息数据列表
     */
    List<ShareMoment> selectList(@Param("param") ShareMoment param);

    /**
     * 根据ID查询动态信息详情
     *
     * @param id 动态信息ID
     * @return 动态信息详情信息
     */
    ShareMoment selectById(@Param("id") Long id);

    /**
     * 新增动态信息数据
     *
     * @param entity 动态信息实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") ShareMoment entity);

    /**
     * 批量新增动态信息数据
     *
     * @param list 动态信息实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<ShareMoment> list);

    /**
     * 更新动态信息数据
     *
     * @param entity 动态信息实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") ShareMoment entity);

    /**
     * 根据ID删除动态信息数据
     *
     * @param id 动态信息ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除动态信息数据
     *
     * @param ids 动态信息ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计动态信息数据数量
     *
     * @param param 查询参数
     * @return 动态信息数据总数
     */
    Long count(@Param("param") ShareMoment param);

    /**
     * 根据ID判断动态信息是否存在
     *
     * @param id 动态信息ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);
}
