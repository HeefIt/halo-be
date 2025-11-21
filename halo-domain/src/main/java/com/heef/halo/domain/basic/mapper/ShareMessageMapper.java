package com.heef.halo.domain.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heef.halo.domain.basic.entity.ShareMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息表(ShareMessage)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:49
 */
@Mapper
public interface ShareMessageMapper extends BaseMapper<ShareMessage> {

    /**
     * 分页查询消息表数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 消息表数据列表
     */
    List<ShareMessage> selectPage(@Param("param") ShareMessage param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询消息表列表
     *
     * @param param 查询参数
     * @return 消息表数据列表
     */
    List<ShareMessage> selectList(@Param("param") ShareMessage param);

    /**
     * 根据ID查询消息表详情
     *
     * @param id 消息表ID
     * @return 消息表详情信息
     */
    ShareMessage selectById(@Param("id") Integer id);

    /**
     * 新增消息表数据
     *
     * @param entity 消息表实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") ShareMessage entity);

    /**
     * 批量新增消息表数据
     *
     * @param list 消息表实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<ShareMessage> list);

    /**
     * 更新消息表数据
     *
     * @param entity 消息表实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") ShareMessage entity);

    /**
     * 根据ID删除消息表数据
     *
     * @param id 消息表ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 批量删除消息表数据
     *
     * @param ids 消息表ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Integer> ids);

    /**
     * 统计消息表数据数量
     *
     * @param param 查询参数
     * @return 消息表数据总数
     */
    Long count(@Param("param") ShareMessage param);

    /**
     * 根据ID判断消息表是否存在
     *
     * @param id 消息表ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Integer id);
}