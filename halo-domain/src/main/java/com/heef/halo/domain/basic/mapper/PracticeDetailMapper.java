package com.heef.halo.domain.basic.mapper;

import com.heef.halo.domain.basic.entity.PracticeDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 练习详情表(PracticeDetail)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:46
 */
@Mapper
public interface PracticeDetailMapper {

    /**
     * 分页查询练习详情表数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 练习详情表数据列表
     */
    List<PracticeDetail> selectPage(@Param("param") PracticeDetail param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询练习详情表列表
     *
     * @param param 查询参数
     * @return 练习详情表数据列表
     */
    List<PracticeDetail> selectList(@Param("param") PracticeDetail param);

    /**
     * 根据ID查询练习详情表详情
     *
     * @param id 练习详情表ID
     * @return 练习详情表详情信息
     */
    PracticeDetail selectById(@Param("id") Long id);

    /**
     * 新增练习详情表数据
     *
     * @param entity 练习详情表实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") PracticeDetail entity);

    /**
     * 批量新增练习详情表数据
     *
     * @param list 练习详情表实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<PracticeDetail> list);

    /**
     * 更新练习详情表数据
     *
     * @param entity 练习详情表实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") PracticeDetail entity);

    /**
     * 根据ID删除练习详情表数据
     *
     * @param id 练习详情表ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除练习详情表数据
     *
     * @param ids 练习详情表ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计练习详情表数据数量
     *
     * @param param 查询参数
     * @return 练习详情表数据总数
     */
    Long count(@Param("param") PracticeDetail param);

    /**
     * 根据ID判断练习详情表是否存在
     *
     * @param id 练习详情表ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);
}
