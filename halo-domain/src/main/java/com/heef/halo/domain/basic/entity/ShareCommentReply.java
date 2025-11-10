package com.heef.halo.domain.basic.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 评论及回复信息(ShareCommentReply)实体类
 *
 * @author heef
 * @since 2025-10-31 16:47:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ShareCommentReply implements Serializable {
    private static final long serialVersionUID = -79123326753894970L;
    /**
     * 评论ID
     */
    private Long id;
    /**
     * 原始动态ID
     */
    private Integer momentId;
    /**
     * 回复类型 1评论 2回复
     */
    private Integer replyType;
    /**
     * 评论目标id
     */
    private Long toId;
    /**
     * 评论人
     */
    private String toUser;
    /**
     * 评论人是否作者 1=是 0=否
     */
    private Integer toUserAuthor;
    /**
     * 回复目标id
     */
    private Long replyId;
    /**
     * 回复人
     */
    private String replyUser;
    /**
     * 回复人是否作者 1=是 0=否
     */
    private Integer replayAuthor;
    /**
     * 内容
     */
    private String content;
    /**
     * 图片内容
     */
    private String picUrls;

    private Integer parentId;

    private String leafNode;

    private String children;

    private String rootNode;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
    /**
     * 是否被删除 0为删除 1已删除
     */
    private Integer isDeleted;

}
