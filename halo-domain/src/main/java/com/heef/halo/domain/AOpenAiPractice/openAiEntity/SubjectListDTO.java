package com.heef.halo.domain.AOpenAiPractice.openAiEntity;

import com.heef.halo.domain.basic.entity.SubjectInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 题目列表响应DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectListDTO {
    private List<SubjectInfo> list;      // 题目列表
    private Long total;                   // 总记录数
    private Integer pageNum;               // 当前页码
    private Integer pageSize;              // 每页数量
}