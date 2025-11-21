package com.heef.halo.domain.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heef.halo.domain.basic.entity.SubjectInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 题目信息表(SubjectInfo)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:51
 */
@Mapper
public interface SubjectInfoMapper extends BaseMapper<SubjectInfo> {

    /**
     * 分页查询题目信息表数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 题目信息表数据列表
     */
    List<SubjectInfo> selectPage(@Param("param") SubjectInfo param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询题目信息表列表
     *
     * @param param 查询参数
     * @return 题目信息表数据列表
     */
    List<SubjectInfo> selectList(@Param("param") SubjectInfo param);

    /**
     * 根据ID查询题目信息表详情
     *
     * @param id 题目信息表ID
     * @return 题目信息表详情信息
     */
    SubjectInfo selectById(@Param("id") Long id);

    /**
     * 新增题目信息表数据
     *
     * @param entity 题目信息表实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") SubjectInfo entity);

    /**
     * 批量新增题目信息表数据
     *
     * @param list 题目信息表实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<SubjectInfo> list);

    /**
     * 更新题目信息表数据
     *
     * @param entity 题目信息表实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") SubjectInfo entity);

    /**
     * 根据ID删除题目信息表数据
     *
     * @param id 题目信息表ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除题目信息表数据
     *
     * @param ids 题目信息表ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计题目信息表数据数量
     *
     * @param param 查询参数
     * @return 题目信息表数据总数
     */
    Long count(@Param("param") SubjectInfo param);

    /**
     * 根据ID判断题目信息表是否存在
     *
     * @param id 题目信息表ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);

    /**
     * 条件查询指定行题目数据
     * @param subjectInfo
     * @return
     */
    SubjectInfo queryByCondition(@Param("param") SubjectInfo subjectInfo);

    /**
     * 分页查询题目列表(面向用户)
     * @param subjectInfo
     * @param categoryId
     * @param labelId
     * @param offset
     * @param pageSize
     * @return
     */
    List<SubjectInfo> selectPage2(@Param("subjectInfo") SubjectInfo subjectInfo, @Param("categoryId") Integer categoryId, 
                                 @Param("labelId") Integer labelId, @Param("offset") int offset, @Param("pageSize") Integer pageSize);

    /**
     * 统计题目总数(面向用户)
     * @param subjectInfo
     * @param categoryId
     * @param labelId
     * @return
     */
    Long count2(@Param("subjectInfo") SubjectInfo subjectInfo, @Param("categoryId") Integer categoryId, @Param("labelId") Integer labelId);

    /**
     * 分页查询题目列表(面向用户)-支持多标签
     * @param subjectInfo
     * @param categoryId
     * @param labelIds
     * @param offset
     * @param pageSize
     * @return
     */
    List<SubjectInfo> selectPage3(@Param("subjectInfo") SubjectInfo subjectInfo, @Param("categoryId") Integer categoryId, 
                                 @Param("labelIds") List<Integer> labelIds, @Param("offset") int offset, @Param("pageSize") Integer pageSize);

    /**
     * 统计题目总数(面向用户)-支持多标签
     * @param subjectInfo
     * @param categoryId
     * @param labelIds
     * @return
     */
    Long count3(@Param("subjectInfo") SubjectInfo subjectInfo, @Param("categoryId") Integer categoryId, @Param("labelIds") List<Integer> labelIds);
}