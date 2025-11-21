package com.heef.halo.domain.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heef.halo.domain.basic.entity.AuthUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户信息表(AuthUser)数据访问层
 *
 * @author heef
 * @since 2025-10-31 16:47:45
 */
@Mapper
public interface AuthUserMapper extends BaseMapper<AuthUser> {

    /**
     * 分页查询用户信息表数据
     *
     * @param param  查询参数
     * @param offset 起始位置
     * @param limit  每页记录数
     * @return 用户信息表数据列表
     */
    List<AuthUser> selectPage(@Param("param") AuthUser param, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 条件查询用户信息表列表
     *
     * @param param 查询参数
     * @return 用户信息表数据列表
     */
    List<AuthUser> selectList(@Param("param") AuthUser param);

    /**
     * 根据ID查询用户信息表详情
     *
     * @param id 用户信息表ID
     * @return 用户信息表详情信息
     */
    AuthUser selectById(@Param("id") Long id);

    /**
     * 新增用户信息表数据
     *
     * @param entity 用户信息表实体对象
     * @return 插入结果（影响行数）
     */
    int insert(@Param("entity") AuthUser entity);

    /**
     * 批量新增用户信息表数据
     *
     * @param list 用户信息表实体对象列表
     * @return 插入结果（影响行数）
     */
    int insertBatch(@Param("list") List<AuthUser> list);

    /**
     * 更新用户信息表数据
     *
     * @param entity 用户信息表实体对象
     * @return 更新结果（影响行数）1表示修改成功,0表示修改失败
     */
    int update(@Param("entity") AuthUser entity);

    /**
     * 根据ID删除用户信息表数据
     *
     * @param id 用户信息表ID
     * @return 删除结果（影响行数）1表示删除成功,0表示删除失败
     */
    int deleteById(@Param("id") Long id);

    /**
     * 批量删除用户信息表数据
     *
     * @param ids 用户信息表ID列表
     * @return 删除结果（影响行数）
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 统计用户信息表数据数量
     *
     * @param param 查询参数
     * @return 用户信息表数据总数
     */
    Long count(@Param("param") AuthUser param);

    /**
     * 根据ID判断用户信息表是否存在
     *
     * @param id 用户信息表ID
     * @return 是否存在（true:存在, false:不存在）
     */
    Boolean existsById(@Param("id") Long id);


    /**
     * 条件查询指定行数据--比如根据用户名查找用户数据
     * @param authUser
     * @return
     */
    AuthUser selectByCondition(@Param("param") AuthUser authUser);


    /**
     * 根据用户名查询用户--登录接口使用
     * @param userName
     * @return
     */
    AuthUser selectByUserName(String userName);

    /**
     * 设置用户状态
     * @param id
     * @param status
     * @return
     */
    Boolean setStatus(Long id, Integer status);

    /**
     * 查询在线用户(status状态为0的用户)
     * @param onlineUserParam
     * @return
     */
    Long countOnlineUsers(AuthUser onlineUserParam);
    
    /**
     * 获取用户增长趋势数据
     * @param days 天数（7或30）
     * @return 趋势数据列表
     */
    List<java.util.Map<String, Object>> getUserGrowthTrend(@Param("days") int days);
}
