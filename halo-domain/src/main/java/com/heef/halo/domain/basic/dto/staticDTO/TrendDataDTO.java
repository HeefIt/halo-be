package com.heef.halo.domain.basic.dto.staticDTO;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * 趋势数据DTO（用于展示用户增长趋势和题目提交趋势）
 */
@Data
@AllArgsConstructor
public class TrendDataDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 日期（格式：yyyy-MM-dd）
     */
    private String date;
    
    /**
     * 数量（新增用户数或提交数）
     */
    private Long count;
    
    /**
     * 累计数量（用于展示累计曲线）
     */
    private Long total;
    
    public TrendDataDTO() {
    }
    
    public TrendDataDTO(String date, Long count) {
        this.date = date;
        this.count = count;
    }
}
