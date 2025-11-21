package com.heef.halo.domain.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heef.halo.domain.basic.entity.PracticeSet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 套题信息表(PracticeSet)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:47
 */
@Mapper
public interface PracticeSetMapper extends BaseMapper<PracticeSet> {

    /**
     * 分页查询套题信息表数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 套题信息表数据列表
     */
    List<PracticeSet> selectPage(@Param("param") PracticeSet param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询套题信息表列表
     *
     * @param param 查询参数
     * @return 套题信息表数据列表
     */
    List<PracticeSet> selectList(@Param("param") PracticeSet param);

    /**
     * 根据ID查询套题信息表详情
     *
     * @param id 套题信息表ID
     * @return 套题信息表详情信息
     */
    PracticeSet selectById(@Param("id") Long id);

    /**
     * 新增套题信息表数据
     *
     * @param entity 套题信息表实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") PracticeSet entity);

    /**
     * 批量新增套题信息表数据
     *
     * @param list 套题信息表实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<PracticeSet> list);

    /**
     * 更新套题信息表数据
     *
     * @param entity 套题信息表实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") PracticeSet entity);

    /**
     * 根据ID删除套题信息表数据
     *
     * @param id 套题信息表ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除套题信息表数据
     *
     * @param ids 套题信息表ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计套题信息表数据数量
     *
     * @param param 查询参数
     * @return 套题信息表数据总数
     */
    Long count(@Param("param") PracticeSet param);

    /**
     * 根据ID判断套题信息表是否存在
     *
     * @param id 套题信息表ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);
}