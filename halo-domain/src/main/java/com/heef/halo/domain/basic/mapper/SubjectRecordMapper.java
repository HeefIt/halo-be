package com.heef.halo.domain.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heef.halo.domain.basic.entity.SubjectRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * (SubjectRecord)表数据库访问层
 *
 * @author heef
 * @since 2025-11-20 11:32:33
 */
@Mapper
public interface SubjectRecordMapper extends BaseMapper<SubjectRecord> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectRecord selectById(Long id);

    /**
     * 查询指定行数据
     *
     * @param subjectRecord 查询条件
     * @param offset        offset
     * @param pageSize      pageSize
     * @return 对象列表
     */
    List<SubjectRecord> selectPage(@Param("subjectRecord") SubjectRecord subjectRecord, @Param("offset") int offset, @Param("pageSize") int pageSize);

    /**
     * 统计总行数
     *
     * @param subjectRecord 查询条件
     * @return 总行数
     */
    long count(SubjectRecord subjectRecord);

    /**
     * 新增数据
     *
     * @param subjectRecord 实例对象
     * @return 影响行数
     */
    int insert(SubjectRecord subjectRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SubjectRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SubjectRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SubjectRecord> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SubjectRecord> entities);

    /**
     * 修改数据
     *
     * @param subjectRecord 实例对象
     * @return 影响行数
     */
    int update(SubjectRecord subjectRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据用户ID获取答题记录
     *
     * @param userId 用户ID
     * @return 答题记录列表
     */
    List<SubjectRecord> getRecordByUser(Long userId);

    /**
     * 根据题目ID获取答题记录
     *
     * @param subjectId 题目ID
     * @return 答题记录列表
     */
    List<SubjectRecord> getRecordBySubject(Long subjectId);
    
    /**
     * 获取用户当日刷题统计
     * 
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 答题记录列表
     */
    List<SubjectRecord> getDailyRecords(@Param("userId") Long userId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    
    /**
     * 统计用户已解决的题目数量（回答正确的题目）
     * 
     * @param userId 用户ID
     * @return 已解决的题目数量
     */
    int countSolvedProblems(@Param("userId") Long userId);
    
    /**
     * 统计用户尝试的题目数量（所有答题记录）
     * 
     * @param userId 用户ID
     * @return 尝试的题目数量
     */
    int countAttemptedProblems(@Param("userId") Long userId);
    
    /**
     * 获取题目提交趋势数据
     * @param days 天数（7或30）
     * @return 趋势数据列表
     */
    List<java.util.Map<String, Object>> getSubmissionTrend(@Param("days") int days);
}