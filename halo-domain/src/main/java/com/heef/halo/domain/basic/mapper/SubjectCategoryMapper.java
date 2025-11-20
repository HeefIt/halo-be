package com.heef.halo.domain.basic.mapper;

import com.heef.halo.domain.basic.entity.SubjectCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 题目分类(SubjectCategory)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:50
 */
@Mapper
public interface SubjectCategoryMapper {

    /**
     * 分页查询题目分类数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 题目分类数据列表
     */
    List<SubjectCategory> selectPage(@Param("param") SubjectCategory param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询题目分类列表
     *
     * @param param 查询参数
     * @return 题目分类数据列表
     */
    List<SubjectCategory> selectList(@Param("param") SubjectCategory param);

    /**
     * 根据ID查询题目分类详情
     *
     * @param id 题目分类ID
     * @return 题目分类详情信息
     */
    SubjectCategory selectById(@Param("id") Long id);

    /**
     * 新增题目分类数据
     *
     * @param entity 题目分类实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") SubjectCategory entity);

    /**
     * 批量新增题目分类数据
     *
     * @param list 题目分类实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<SubjectCategory> list);

    /**
     * 更新题目分类数据
     *
     * @param entity 题目分类实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") SubjectCategory entity);

    /**
     * 根据ID删除题目分类数据
     *
     * @param id 题目分类ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除题目分类数据
     *
     * @param ids 题目分类ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计题目分类数据数量
     *
     * @param param 查询参数
     * @return 题目分类数据总数
     */
    Long count(@Param("param") SubjectCategory param);

    /**
     * 根据ID判断题目分类是否存在
     *
     * @param id 题目分类ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);

    /**
     * 根据父分类id查询子分类
     * @param subjectCategory
     * @return
     */
    List<SubjectCategory> selectCategoryByParentId(SubjectCategory subjectCategory);
}
