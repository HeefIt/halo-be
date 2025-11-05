package com.heef.halo.domain.basic.mapper;

import com.heef.halo.domain.basic.entity.SensitiveWords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 敏感词表(SensitiveWords)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:48
 */
@Mapper
public interface SensitiveWordsMapper {

    /**
     * 分页查询敏感词表数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 敏感词表数据列表
     */
    List<SensitiveWords> selectPage(@Param("param") SensitiveWords param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询敏感词表列表
     *
     * @param param 查询参数
     * @return 敏感词表数据列表
     */
    List<SensitiveWords> selectList(@Param("param") SensitiveWords param);

    /**
     * 根据ID查询敏感词表详情
     *
     * @param id 敏感词表ID
     * @return 敏感词表详情信息
     */
    SensitiveWords selectById(@Param("id") Long id);

    /**
     * 新增敏感词表数据
     *
     * @param entity 敏感词表实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") SensitiveWords entity);

    /**
     * 批量新增敏感词表数据
     *
     * @param list 敏感词表实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<SensitiveWords> list);

    /**
     * 更新敏感词表数据
     *
     * @param entity 敏感词表实体对象
     * @return 更新结果（影响行数）
     */
    int update(@Param("entity") SensitiveWords entity);

    /**
     * 根据ID删除敏感词表数据
     *
     * @param id 敏感词表ID
     * @return 删除结果（影响行数）
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除敏感词表数据
     *
     * @param ids 敏感词表ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计敏感词表数据数量
     *
     * @param param 查询参数
     * @return 敏感词表数据总数
     */
    Long count(@Param("param") SensitiveWords param);

    /**
     * 根据ID判断敏感词表是否存在
     *
     * @param id 敏感词表ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);
}
