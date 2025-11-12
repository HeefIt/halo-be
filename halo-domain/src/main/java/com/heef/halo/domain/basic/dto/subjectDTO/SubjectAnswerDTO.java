package com.heef.halo.domain.basic.dto.subjectDTO;

import lombok.Data;

import java.io.Serializable;

/**
 * 题目答案(SubjectSubjectAnswerDTO)DTO
 *
 * @author makejava
 * @since 2025-04-15 15:17:32
 */
@Data
public class SubjectAnswerDTO implements Serializable {
    private static final long serialVersionUID = -98680207564099835L;
    /**
     * 主键
     */
    private Integer optionType;
    /**
     * 题目名称
     */
    private String optionContent;
    /**
     * 是否正确
     */
    private Integer isCorrect;

}

