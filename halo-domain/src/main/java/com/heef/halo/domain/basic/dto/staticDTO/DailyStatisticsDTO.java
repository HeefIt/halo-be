package com.heef.halo.domain.basic.dto.staticDTO;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户每日刷题统计DTO
 */
@Data
public class DailyStatisticsDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 刷题数量
     */
    private Integer problemCount = 0;
    
    /**
     * 刷题时间（秒）
     */
    private Integer totalTime = 0;
    
    /**
     * 正确率（百分比）
     */
    private Double accuracy = 0.0;
    
    /**
     * 今日刷题得分
     */
    private Integer totalScore = 0;
}