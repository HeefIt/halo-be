package com.heef.halo.domain.basic.dto.subjectDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目信息表(SubjectInfo)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SubjectInfoDTO implements Serializable {
    private static final long serialVersionUID = -71401209593239253L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 题目名称
     */
    private String subjectName;
    /**
     * 题目难度
     */
    private Integer subjectDifficult;
    /**
     * 出题人名
     */
    private String settleName;
    /**
     * 题目类型 1单选 2多选 3判断 4简答
     */
    private Integer subjectType;
    /**
     * 题目分数
     */
    private Integer subjectScore;
    /**
     * 题目解析
     */
    private String subjectParse;
    /**
     * 题目答案
     */
    private String subjectAnswer;

    /**
     * 分类id (新增的时候使用)--可能一个题目对应多个分类和标签
     */
    private List<Integer> categoryIds;

    /**
     * 标签id (新增的时候使用)--可能一个题目对应多个分类和标签
     */
    private List<Integer> labelIds;

    /**
     * 选择题的答案选项 如a,b,c,d
     */
    private List<SubjectAnswerDTO> optionList;

    /**
     * 分类id  (查询的时候使用)
     */
    private Integer categoryId;

    /**
     * 标签id (查询的时候使用)
     */
    private Integer labelId;

    /**
     * 标签id列表 (查询多个标签的时候使用)
     */
    private List<Integer> labelIds_query;

    /**
     * 标签名字
     */
    private List<String> labelName;
}
