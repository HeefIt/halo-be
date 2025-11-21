package com.heef.halo.domain.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heef.halo.domain.basic.entity.PracticeSetDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 套题内容表(PracticeSetDetail)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:47
 */
@Mapper
public interface PracticeSetDetailMapper extends BaseMapper<PracticeSetDetail> {

    /**
     * 分页查询套题内容表数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 套题内容表数据列表
     */
    List<PracticeSetDetail> selectPage(@Param("param") PracticeSetDetail param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询套题内容表列表
     *
     * @param param 查询参数
     * @return 套题内容表数据列表
     */
    List<PracticeSetDetail> selectList(@Param("param") PracticeSetDetail param);

    /**
     * 根据ID查询套题内容表详情
     *
     * @param id 套题内容表ID
     * @return 套题内容表详情信息
     */
    PracticeSetDetail selectById(@Param("id") Long id);

    /**
     * 新增套题内容表数据
     *
     * @param entity 套题内容表实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") PracticeSetDetail entity);

    /**
     * 批量新增套题内容表数据
     *
     * @param list 套题内容表实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<PracticeSetDetail> list);

    /**
     * 更新套题内容表数据
     *
     * @param entity 套题内容表实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") PracticeSetDetail entity);

    /**
     * 根据ID删除套题内容表数据
     *
     * @param id 套题内容表ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除套题内容表数据
     *
     * @param ids 套题内容表ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计套题内容表数据数量
     *
     * @param param 查询参数
     * @return 套题内容表数据总数
     */
    Long count(@Param("param") PracticeSetDetail param);

    /**
     * 根据ID判断套题内容表是否存在
     *
     * @param id 套题内容表ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);
}