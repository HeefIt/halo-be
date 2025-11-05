package com.heef.halo.domain.basic.mapper;

import com.heef.halo.domain.basic.entity.ShareCommentReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论及回复信息(ShareCommentReply)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:48
 */
@Mapper
public interface ShareCommentReplyMapper {

    /**
     * 分页查询评论及回复信息数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 评论及回复信息数据列表
     */
    List<ShareCommentReply> selectPage(@Param("param") ShareCommentReply param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询评论及回复信息列表
     *
     * @param param 查询参数
     * @return 评论及回复信息数据列表
     */
    List<ShareCommentReply> selectList(@Param("param") ShareCommentReply param);

    /**
     * 根据ID查询评论及回复信息详情
     *
     * @param id 评论及回复信息ID
     * @return 评论及回复信息详情信息
     */
    ShareCommentReply selectById(@Param("id") Long id);

    /**
     * 新增评论及回复信息数据
     *
     * @param entity 评论及回复信息实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") ShareCommentReply entity);

    /**
     * 批量新增评论及回复信息数据
     *
     * @param list 评论及回复信息实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<ShareCommentReply> list);

    /**
     * 更新评论及回复信息数据
     *
     * @param entity 评论及回复信息实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") ShareCommentReply entity);

    /**
     * 根据ID删除评论及回复信息数据
     *
     * @param id 评论及回复信息ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除评论及回复信息数据
     *
     * @param ids 评论及回复信息ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计评论及回复信息数据数量
     *
     * @param param 查询参数
     * @return 评论及回复信息数据总数
     */
    Long count(@Param("param") ShareCommentReply param);

    /**
     * 根据ID判断评论及回复信息是否存在
     *
     * @param id 评论及回复信息ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);
}
