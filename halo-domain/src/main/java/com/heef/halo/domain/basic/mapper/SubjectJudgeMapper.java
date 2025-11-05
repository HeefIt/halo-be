package com.heef.halo.domain.basic.mapper;

import com.heef.halo.domain.basic.entity.SubjectJudge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 判断题(SubjectJudge)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:51
 */
@Mapper
public interface SubjectJudgeMapper {

    /**
     * 分页查询判断题数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 判断题数据列表
     */
    List<SubjectJudge> selectPage(@Param("param") SubjectJudge param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询判断题列表
     *
     * @param param 查询参数
     * @return 判断题数据列表
     */
    List<SubjectJudge> selectList(@Param("param") SubjectJudge param);

    /**
     * 根据ID查询判断题详情
     *
     * @param id 判断题ID
     * @return 判断题详情信息
     */
    SubjectJudge selectById(@Param("id") Long id);

    /**
     * 新增判断题数据
     *
     * @param entity 判断题实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") SubjectJudge entity);

    /**
     * 批量新增判断题数据
     *
     * @param list 判断题实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<SubjectJudge> list);

    /**
     * 更新判断题数据
     *
     * @param entity 判断题实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") SubjectJudge entity);

    /**
     * 根据ID删除判断题数据
     *
     * @param id 判断题ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除判断题数据
     *
     * @param ids 判断题ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计判断题数据数量
     *
     * @param param 查询参数
     * @return 判断题数据总数
     */
    Long count(@Param("param") SubjectJudge param);

    /**
     * 根据ID判断判断题是否存在
     *
     * @param id 判断题ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);
}
