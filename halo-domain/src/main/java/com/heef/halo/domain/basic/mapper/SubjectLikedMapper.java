package com.heef.halo.domain.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heef.halo.domain.basic.entity.SubjectLiked;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 题目点赞表(SubjectLiked)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:52
 */
@Mapper
public interface SubjectLikedMapper extends BaseMapper<SubjectLiked> {

    /**
     * 分页查询题目点赞表数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 题目点赞表数据列表
     */
    List<SubjectLiked> selectPage(@Param("param") SubjectLiked param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询题目点赞表列表
     *
     * @param param 查询参数
     * @return 题目点赞表数据列表
     */
    List<SubjectLiked> selectList(@Param("param") SubjectLiked param);

    /**
     * 根据ID查询题目点赞表详情
     *
     * @param id 题目点赞表ID
     * @return 题目点赞表详情信息
     */
    SubjectLiked selectById(@Param("id") Long id);

    /**
     * 新增题目点赞表数据
     *
     * @param entity 题目点赞表实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") SubjectLiked entity);

    /**
     * 批量新增题目点赞表数据
     *
     * @param list 题目点赞表实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<SubjectLiked> list);

    /**
     * 更新题目点赞表数据
     *
     * @param entity 题目点赞表实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") SubjectLiked entity);

    /**
     * 根据ID删除题目点赞表数据
     *
     * @param id 题目点赞表ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除题目点赞表数据
     *
     * @param ids 题目点赞表ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计题目点赞表数据数量
     *
     * @param param 查询参数
     * @return 题目点赞表数据总数
     */
    Long count(@Param("param") SubjectLiked param);

    /**
     * 根据ID判断题目点赞表是否存在
     *
     * @param id 题目点赞表ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);
}