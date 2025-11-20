package com.heef.halo.domain.basic.dto.staticDTO;

import lombok.Data;

import java.io.Serializable;

/**
 * 网站统计信息DTO
 */
@Data
public class StatisticsDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 总用户数
     */
    private Long totalUsers;
    
    /**
     * 总题目数
     */
    private Long totalProblems;
    
    /**
     * 在线用户数
     */
    private Long onlineUsers;
    
    /**
     * 总提交数
     */
    private Long totalSubmissions;
}