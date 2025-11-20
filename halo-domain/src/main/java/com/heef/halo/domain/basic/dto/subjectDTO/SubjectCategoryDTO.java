package com.heef.halo.domain.basic.dto.subjectDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目分类(SubjectCategory)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:50
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SubjectCategoryDTO implements Serializable {
    private static final long serialVersionUID = -63247575387212916L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 分类类型
     */
    private Integer categoryType;
    /**
     * 图标连接
     */
    private String imageUrl;
    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除 0: 未删除 1: 已删除
     */
    private Integer isDeleted;

    /**
     * 分类下的题目数量
     */
    private  Integer subjectCount;

    /**
     * 标签信息
     */
    private List<SubjectLabelDTO> labelDTOList;

}
