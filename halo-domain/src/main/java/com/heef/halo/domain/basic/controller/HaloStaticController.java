package com.heef.halo.domain.basic.controller;

import com.heef.halo.domain.basic.dto.staticDTO.DailyStatisticsDTO;
import com.heef.halo.domain.basic.dto.staticDTO.StatisticsDTO;
import com.heef.halo.domain.basic.entity.AuthUser;
import com.heef.halo.domain.basic.entity.SubjectInfo;
import com.heef.halo.domain.basic.entity.SubjectRecord;
import com.heef.halo.domain.basic.mapper.AuthUserMapper;
import com.heef.halo.domain.basic.mapper.SubjectInfoMapper;
import com.heef.halo.domain.basic.mapper.SubjectRecordMapper;
import com.heef.halo.domain.basic.service.SubjectService;
import com.heef.halo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 网站统计信息控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/statistics")
public class HaloStaticController {
    
    @Autowired
    private AuthUserMapper authUserMapper;
    
    @Autowired
    private SubjectInfoMapper subjectInfoMapper;
    
    @Autowired
    private SubjectRecordMapper subjectRecordMapper;
    
    @Autowired
    private SubjectService subjectService;

    /**
     * 获取网站统计信息
     * @return
     */
    @GetMapping("/getStatistics")
    public Result<StatisticsDTO> getStatistics() {
        try {
            StatisticsDTO statistics = new StatisticsDTO();
            
            // 获取总用户数
            AuthUser userParam = new AuthUser();
            userParam.setIsDeleted(0); // 未删除的用户
            Long totalUsers = authUserMapper.count(userParam);
            statistics.setTotalUsers(totalUsers);
            
            // 获取总题目数
            SubjectInfo subjectParam = new SubjectInfo();
            subjectParam.setIsDeleted(0); // 未删除的题目
            Long totalProblems = subjectInfoMapper.count(subjectParam);
            statistics.setTotalProblems(totalProblems);
            
            // 获取总提交数（刷题记录数）
            SubjectRecord recordParam = new SubjectRecord();
            long totalSubmissions = subjectRecordMapper.count(recordParam);
            statistics.setTotalSubmissions(totalSubmissions);
            
            // 在线用户数（根据用户状态判断，status为0表示正常状态，即在线）
            AuthUser onlineUserParam = new AuthUser();
            onlineUserParam.setIsDeleted(0);
            onlineUserParam.setStatus(0); // 正常状态的用户视为在线
            Long onlineUsers = authUserMapper.countOnlineUsers(onlineUserParam);
            statistics.setOnlineUsers(onlineUsers);
            
            return Result.ok(statistics);
        } catch (Exception e) {
            log.error("获取网站统计信息失败: ", e);
            return Result.fail("获取网站统计信息失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取用户当日刷题统计
     * 
     * @param userId 用户ID
     * @param date 日期（可选，默认为当天）
     * @return 每日统计信息
     */
    @GetMapping("/getDailyStatistics")
    public Result<DailyStatisticsDTO> getDailyStatistics(@RequestParam Long userId, 
                                                         @RequestParam(required = false) Date date) {
        try {
            if (date == null) {
                date = new Date(); // 默认为当天
            }
            
            DailyStatisticsDTO dailyStats = subjectService.getDailyStatistics(userId, date);
            return Result.ok(dailyStats);
        } catch (Exception e) {
            log.error("获取用户当日刷题统计失败: ", e);
            return Result.fail("获取用户当日刷题统计失败: " + e.getMessage());
        }
    }
}