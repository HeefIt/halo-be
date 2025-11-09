package com.heef.halo.domain.basic.mapper;

import com.heef.halo.domain.basic.entity.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件信息Mapper接口
 *
 * @author heefM
 * @date 2025-11-07
 */
@Mapper
public interface FileInfoMapper {
    
    /**
     * 插入文件信息
     *
     * @param fileInfo 文件信息实体
     * @return 插入结果
     */
    int insert(@Param("entity") FileInfo fileInfo);
    
    /**
     * 根据ID查询文件信息
     *
     * @param id 文件ID
     * @return 文件信息实体
     */
    FileInfo selectById(@Param("id") Long id);
    
    /**
     * 更新文件信息
     *
     * @param fileInfo 文件信息实体
     * @return 更新结果
     */
    int update(@Param("entity") FileInfo fileInfo);
    
    /**
     * 根据ID删除文件信息（逻辑删除）
     *
     * @param id 文件ID
     * @return 删除结果
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 分页查询文件信息列表
     *
     * @param offset 偏移量
     * @param limit  每页数量
     * @return 文件信息列表
     */
    List<FileInfo> selectPage(@Param("offset") Integer offset, @Param("limit") Integer limit);
    
    /**
     * 查询文件信息总数
     *
     * @return 文件信息总数
     */
    int count();
}