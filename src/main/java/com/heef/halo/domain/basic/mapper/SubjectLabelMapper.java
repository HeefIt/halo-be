package com.heef.halo.domain.basic.mapper;

import com.heef.halo.domain.basic.entity.SubjectLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 题目标签表(SubjectLabel)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:51
 */
@Mapper
public interface SubjectLabelMapper {

    /**
     * 分页查询题目标签表数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 题目标签表数据列表
     */
    List<SubjectLabel> selectPage(@Param("param") SubjectLabel param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询题目标签表列表
     *
     * @param param 查询参数
     * @return 题目标签表数据列表
     */
    List<SubjectLabel> selectList(@Param("param") SubjectLabel param);

    /**
     * 根据ID查询题目标签表详情
     *
     * @param id 题目标签表ID
     * @return 题目标签表详情信息
     */
    SubjectLabel selectById(@Param("id") Long id);

    /**
     * 新增题目标签表数据
     *
     * @param entity 题目标签表实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") SubjectLabel entity);

    /**
     * 批量新增题目标签表数据
     *
     * @param list 题目标签表实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<SubjectLabel> list);

    /**
     * 更新题目标签表数据
     *
     * @param entity 题目标签表实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") SubjectLabel entity);

    /**
     * 根据ID删除题目标签表数据
     *
     * @param id 题目标签表ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除题目标签表数据
     *
     * @param ids 题目标签表ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计题目标签表数据数量
     *
     * @param param 查询参数
     * @return 题目标签表数据总数
     */
    Long count(@Param("param") SubjectLabel param);

    /**
     * 根据ID判断题目标签表是否存在
     *
     * @param id 题目标签表ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);
}
