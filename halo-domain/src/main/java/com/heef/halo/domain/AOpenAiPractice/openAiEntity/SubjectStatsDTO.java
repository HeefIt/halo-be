package com.heef.halo.domain.AOpenAiPractice.openAiEntity;

import com.heef.halo.domain.basic.entity.SubjectInfo;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Map;

/**
 * 题目统计响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectStatsDTO {
    private Long totalCount;                    // 总题数
    private Map<String, Long> categoryCount;    // 各分类数量
    private Map<String, Long> levelCount;       // 各难度数量
    private List<SubjectInfo> recentSubjects;    // 最近题目
}