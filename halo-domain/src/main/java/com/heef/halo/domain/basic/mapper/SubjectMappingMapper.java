package com.heef.halo.domain.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heef.halo.domain.basic.entity.SubjectMapping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 题目分类关系表(SubjectMapping)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:52
 */
@Mapper
public interface SubjectMappingMapper extends BaseMapper<SubjectMapping> {

    /**
     * 分页查询题目分类关系表数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 题目分类关系表数据列表
     */
    List<SubjectMapping> selectPage(@Param("param") SubjectMapping param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询题目分类关系表列表
     *
     * @param param 查询参数
     * @return 题目分类关系表数据列表
     */
    List<SubjectMapping> selectList(@Param("param") SubjectMapping param);

    /**
     * 根据ID查询题目分类关系表详情
     *
     * @param id 题目分类关系表ID
     * @return 题目分类关系表详情信息
     */
    SubjectMapping selectById(@Param("id") Long id);

    /**
     * 新增题目分类关系表数据
     *
     * @param entity 题目分类关系表实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") SubjectMapping entity);

    /**
     * 批量新增题目分类关系表数据
     *
     * @param list 题目分类关系表实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<SubjectMapping> list);

    /**
     * 更新题目分类关系表数据
     *
     * @param entity 题目分类关系表实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") SubjectMapping entity);

    /**
     * 根据ID删除题目分类关系表数据
     *
     * @param id 题目分类关系表ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除题目分类关系表数据
     *
     * @param ids 题目分类关系表ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计题目分类关系表数据数量
     *
     * @param param 查询参数
     * @return 题目分类关系表数据总数
     */
    Long count(@Param("param") SubjectMapping param);

    /**
     * 根据ID判断题目分类关系表是否存在
     *
     * @param id 题目分类关系表ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);

    /**
     * 查询关联表信息
     * @param subjectMapping
     * @return
     */
    List<SubjectMapping> queryMapping(SubjectMapping subjectMapping);

    /**
     * 根据分类id查询对应的题目数量
     * @param id
     * @return
     */
    Integer querySubjectCount(Long id);

    /**
     * 根据分类idList查询关联表数据mapping
     * @param categoryIds
     * @return
     */
    List<SubjectMapping> queryMappingByCategoryIdList(List<Long> categoryIds);
}