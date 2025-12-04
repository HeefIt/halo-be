package com.heef.halo.domain.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heef.halo.domain.basic.entity.SubjectRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 刷题记录Mapper接口
 *
 * @author heefM
 * @date 2025-11-05
 */
public interface SubjectRecordMapper extends BaseMapper<SubjectRecord> {

    /**
     * 分页查询
     *
     * @param offset 偏移量
     * @param pageSize 每页数量
     * @param subjectRecord 查询条件
     * @return 刷题记录列表
     */
    List<SubjectRecord> selectPage(@Param("offset") int offset, @Param("pageSize") int pageSize, @Param("subjectRecord") SubjectRecord subjectRecord);

    /**
     * 统计总数
     *
     * @param subjectRecord 查询条件
     * @return 总数
     */
    long count(@Param("subjectRecord") SubjectRecord subjectRecord);

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
     * 获取用户在指定时间范围内的刷题记录（用于排行榜）
     * 
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 答题记录列表
     */
    List<SubjectRecord> getRankRecords(@Param("userId") Long userId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    
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