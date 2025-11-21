package com.heef.halo.domain.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heef.halo.domain.basic.entity.PracticeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 练习表(PracticeInfo)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:47
 */
@Mapper
public interface PracticeInfoMapper extends BaseMapper<PracticeInfo> {

    /**
     * 分页查询练习表数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 练习表数据列表
     */
    List<PracticeInfo> selectPage(@Param("param") PracticeInfo param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询练习表列表
     *
     * @param param 查询参数
     * @return 练习表数据列表
     */
    List<PracticeInfo> selectList(@Param("param") PracticeInfo param);

    /**
     * 根据ID查询练习表详情
     *
     * @param id 练习表ID
     * @return 练习表详情信息
     */
    PracticeInfo selectById(@Param("id") Long id);

    /**
     * 新增练习表数据
     *
     * @param entity 练习表实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") PracticeInfo entity);

    /**
     * 批量新增练习表数据
     *
     * @param list 练习表实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<PracticeInfo> list);

    /**
     * 更新练习表数据
     *
     * @param entity 练习表实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") PracticeInfo entity);

    /**
     * 根据ID删除练习表数据
     *
     * @param id 练习表ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除练习表数据
     *
     * @param ids 练习表ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计练习表数据数量
     *
     * @param param 查询参数
     * @return 练习表数据总数
     */
    Long count(@Param("param") PracticeInfo param);

    /**
     * 根据ID判断练习表是否存在
     *
     * @param id 练习表ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);
}