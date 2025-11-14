package com.heef.halo.domain.basic.mapper;

import com.heef.halo.domain.basic.entity.SubjectBrief;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 简答题(SubjectBrief)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:50
 */
@Mapper
public interface SubjectBriefMapper {

    /**
     * 分页查询简答题数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 简答题数据列表
     */
    List<SubjectBrief> selectPage(@Param("param") SubjectBrief param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询简答题列表
     *
     * @param param 查询参数
     * @return 简答题数据列表
     */
    List<SubjectBrief> selectList(@Param("param") SubjectBrief param);

    /**
     * 根据ID查询简答题详情
     *
     * @param id 简答题ID
     * @return 简答题详情信息
     */
    SubjectBrief selectById(@Param("id") Long id);

    /**
     * 新增简答题数据
     *
     * @param entity 简答题实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") SubjectBrief entity);

    /**
     * 批量新增简答题数据
     *
     * @param list 简答题实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<SubjectBrief> list);

    /**
     * 更新简答题数据
     *
     * @param entity 简答题实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") SubjectBrief entity);

    /**
     * 根据ID删除简答题数据
     *
     * @param id 简答题ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除简答题数据
     *
     * @param ids 简答题ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计简答题数据数量
     *
     * @param param 查询参数
     * @return 简答题数据总数
     */
    Long count(@Param("param") SubjectBrief param);

    /**
     * 根据ID判断简答题是否存在
     *
     * @param id 简答题ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);

    /**
     * 查询简答题详情信息
     * @param subjectId
     * @return
     */
    SubjectBrief queryBrief(int subjectId);
}
