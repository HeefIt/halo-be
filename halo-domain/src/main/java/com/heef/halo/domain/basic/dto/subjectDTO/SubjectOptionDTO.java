package com.heef.halo.domain.basic.dto.subjectDTO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 选择题题目选项表(SubjectInfo)DTO
 *
 * @author makejava
 * @since 2025-04-15 15:17:32
 */
@Data
public class SubjectOptionDTO  {


    /**
     * 正确答案对应的选项类型（如 "A"）正确
     */
    private String subjectAnswer;
    /**
     * 所有选项列表（如 A/B/C/D），每个选项包含内容和是否正确标记
     */
    private List<SubjectAnswerDTO> optionList;

    /**
     * 题目的分类
     */
    private Integer categoryId;

    /**
     * 题目的标签
     */
    private Integer labelId;

}

