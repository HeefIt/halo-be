package com.heef.halo.domain.basic.mapper;

import com.heef.halo.domain.basic.entity.SubjectRadio;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 单选题信息表(SubjectRadio)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:53
 */
@Mapper
public interface SubjectRadioMapper {

    /**
     * 分页查询单选题信息表数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 单选题信息表数据列表
     */
    List<SubjectRadio> selectPage(@Param("param") SubjectRadio param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询单选题信息表列表
     *
     * @param param 查询参数
     * @return 单选题信息表数据列表
     */
    List<SubjectRadio> selectList(@Param("param") SubjectRadio param);

    /**
     * 根据ID查询单选题信息表详情
     *
     * @param id 单选题信息表ID
     * @return 单选题信息表详情信息
     */
    SubjectRadio selectById(@Param("id") Long id);

    /**
     * 新增单选题信息表数据
     *
     * @param entity 单选题信息表实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") SubjectRadio entity);

    /**
     * 批量新增单选题信息表数据
     *
     * @param list 单选题信息表实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<SubjectRadio> list);

    /**
     * 更新单选题信息表数据
     *
     * @param entity 单选题信息表实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") SubjectRadio entity);

    /**
     * 根据ID删除单选题信息表数据
     *
     * @param id 单选题信息表ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除单选题信息表数据
     *
     * @param ids 单选题信息表ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计单选题信息表数据数量
     *
     * @param param 查询参数
     * @return 单选题信息表数据总数
     */
    Long count(@Param("param") SubjectRadio param);

    /**
     * 根据ID判断单选题信息表是否存在
     *
     * @param id 单选题信息表ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);

    /**
     * 查询单选题详情信息
     * @param subjectId
     * @return
     */
    List<SubjectRadio> queryRadio(int subjectId);
}
