package com.heef.halo.domain.basic.dto.staticDTO;

import lombok.Data;

import java.io.Serializable;

/**
 * 排行榜数据DTO
 *
 * @author heefM
 * @date 2025-11-27
 */
@Data
public class RankDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String userName;
    
    /**
     * 用户头像URL
     */
    private String avatar;
    
    /**
     * 排行数值（根据排行类型不同而不同，如刷题数、得分、正确数等）
     */
    private Integer value;
    
    /**
     * 趋势变化
     * up: 上升
     * down: 下降
     * --: 无变化
     */
    private String trend;
}