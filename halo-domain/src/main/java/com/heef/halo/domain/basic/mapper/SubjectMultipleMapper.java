package com.heef.halo.domain.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heef.halo.domain.basic.entity.SubjectMultiple;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 多选题信息表(SubjectMultiple)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:52
 */
@Mapper
public interface SubjectMultipleMapper extends BaseMapper<SubjectMultiple> {

    /**
     * 分页查询多选题信息表数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 多选题信息表数据列表
     */
    List<SubjectMultiple> selectPage(@Param("param") SubjectMultiple param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询多选题信息表列表
     *
     * @param param 查询参数
     * @return 多选题信息表数据列表
     */
    List<SubjectMultiple> selectList(@Param("param") SubjectMultiple param);

    /**
     * 根据ID查询多选题信息表详情
     *
     * @param id 多选题信息表ID
     * @return 多选题信息表详情信息
     */
    SubjectMultiple selectById(@Param("id") Long id);

    /**
     * 新增多选题信息表数据
     *
     * @param entity 多选题信息表实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") SubjectMultiple entity);

    /**
     * 批量新增多选题信息表数据
     *
     * @param list 多选题信息表实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<SubjectMultiple> list);

    /**
     * 更新多选题信息表数据
     *
     * @param entity 多选题信息表实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") SubjectMultiple entity);

    /**
     * 根据ID删除多选题信息表数据
     *
     * @param id 多选题信息表ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除多选题信息表数据
     *
     * @param ids 多选题信息表ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计多选题信息表数据数量
     *
     * @param param 查询参数
     * @return 多选题信息表数据总数
     */
    Long count(@Param("param") SubjectMultiple param);

    /**
     * 根据ID判断多选题信息表是否存在
     *
     * @param id 多选题信息表ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);

    /**
     * 查询多选题详情信息
     * @param subjectId
     * @return
     */
    List<SubjectMultiple> queryMultiple(int subjectId);
}