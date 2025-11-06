package com.heef.halo.domain.basic.service.impl;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heef.halo.domain.basic.dto.authDTO.AuthUserDTO;
import com.heef.halo.domain.basic.entity.AuthRole;
import com.heef.halo.domain.basic.entity.AuthUser;
import com.heef.halo.domain.basic.entity.AuthUserRole;
import com.heef.halo.domain.basic.mapper.AuthRoleMapper;
import com.heef.halo.domain.basic.mapper.AuthUserMapper;
import com.heef.halo.domain.basic.mapper.AuthUserRoleMapper;
import com.heef.halo.domain.basic.service.AuthService;
import com.heef.halo.domain.convert.AuthConvert;
import com.heef.halo.result.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户鉴权-service层
 *
 * @author heefM
 * @date 2025-10-31
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthUserMapper authUserMapper;

    @Autowired
    private AuthRoleMapper authRoleMapper;

    @Autowired
    private AuthConvert authConvert;

    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;
//mapstruct检查出问题
//    // 添加调试日志转化前的dto
//        System.out.println("接收到的DTO: " + authUserDTO);
//    // 添加调试日志转化后的entity
//        System.out.println("转换后的Entity: " + authUserEntity);

    /**
     * 分页查询用户列表
     *
     * @param authUserDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<AuthUserDTO> selectPage(AuthUserDTO authUserDTO, Integer pageNum, Integer pageSize) {

        //1.计算偏移量-起始页
        int offset = (pageNum - 1) * pageSize;

        //2.dto转实体
        AuthUser authUser = authConvert.toEntity(authUserDTO);

        //3.查询当前页列表数据
        List<AuthUser> userList = authUserMapper.selectPage(authUser, offset, pageSize);

        //4.查询表中总数据
        Long total = authUserMapper.count(authUser);

        //5.实体集合转dto集合返回
        List<AuthUserDTO> authUserDTOList = authConvert.toDtoList(userList);

        //6.封装结果返回
        return PageResult.<AuthUserDTO>builder()
                .pageNo(pageNum)            //当前页码
                .pageSize(pageSize)                 //每页大小
                .total(Math.toIntExact(total))//总记录数
                .result(authUserDTOList)     //数据结果列表
                .build();                          //自动计算总页数,起始,结束位置
    }

    /**
     * 用户注册
     *
     * @param authUserDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insert(AuthUserDTO authUserDTO) {
        // 添加调试日志
        System.out.println("接收到的DTO: " + authUserDTO);

        //1.对象转换
        AuthUser authUserEntity = authConvert.toEntity(authUserDTO);

        // 添加调试日志
        System.out.println("转换后的Entity: " + authUserEntity);

        //密码为空就设置默认密码
        if (StringUtils.isBlank(authUserEntity.getPassword())) {
            authUserEntity.setPassword("123456");
        }
        authUserEntity.setIsDeleted(0);
        authUserEntity.setStatus(0);
        //2.插入数据库
        int uCount = authUserMapper.insert(authUserEntity);

        //3.获取插入数据的id
        Long userId = authUserEntity.getId();
        // 插入用户后，自动获取ID的几种方式：
        //------------------------------------------
        // MyBatis Plus
        //userMapper.insert(user);
        //Long userId = user.getId();  // 自动回填
        //------------------------------------------
        // MyBatis
        //userMapper.insertUser(user);
        //Long userId = user.getId();  // 需要配置 useGeneratedKeys="true"


        // users表        user_roles表        auth_roles表
        //┌─────┐       ┌─────────────┐      ┌───────────┐
        //│ id  │       │ id          │      │ id        │
        //│ name│←-----→│ user_id     │      │ role_name │
        //└─────┘       │ role_id     │←---->│ role_key  │
        //              └─────────────┘      └───────────┘
        //4. 为用户分配默认角色(插入用户角色表);
        AuthUserRole authUserRole = new AuthUserRole();
        authUserRole.setUserId(userId);//设置用户id
        authUserRole.setRoleId(2L);//设置角色id(普通角色)
        authUserRole.setIsDeleted(0);

        int urCount = authUserRoleMapper.insert(authUserRole);
        return urCount > 0 && uCount > 0;
    }

    /**
     * 用户登录
     *
     * @param authUserDTO
     * @return
     */
    @Override
    public SaTokenInfo login(AuthUserDTO authUserDTO) {
        //1.转换对象
        AuthUser authUser = authConvert.toEntity(authUserDTO);

        // 2. 从数据库查询用户
        AuthUser user = authUserMapper.selectByUserName(authUser.getUserName());
        if (user == null) {
            throw new RuntimeException("用户不存在，请先注册");
        }

        // 3. 检查用户状态
        if (user.getStatus() == 1) {
            throw new RuntimeException("用户已被禁用");
        }

        // 4. 验证密码（生产环境要加密验证）
        if (!user.getPassword().equals(authUserDTO.getPassword())) {
            // 加密验证示例：
            // if (!passwordEncoder.matches(authUserDTO.getPassword(), authUser.getPassword()))
            throw new RuntimeException("密码错误");
        }

        // 5. 执行登录
        StpUtil.login(user.getId());

        // 6. 获取 Token 信息
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        log.info("用户登录成功: {}", user.getUserName());

        return tokenInfo;
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long id) {

        //检查用户是否存在
        AuthUser authUser = authUserMapper.selectById(id);
        if (authUser == null) {
            throw new RuntimeException("用户不存在,无法删除!");
        }

        // 删除用户角色关联 --根据userId删除角色关联表数据(用户的不存在了,对应的角色关联肯定也不存在)
        //authUserRoleMapper.deleteByUserId(id);
        authUserRoleMapper.delete(
                new LambdaQueryWrapper<AuthUserRole>()
                        .eq(AuthUserRole::getUserId, id)
        );

        //再删除主表
        int deleted = authUserMapper.deleteById(id);
        return deleted != 0;
    }

    /**
     * 用户修改
     *
     * @param id,authUserDTO
     * @return
     */
    @Override
    public Boolean update(Long id, AuthUserDTO authUserDTO) {
        // 添加调试日志
        System.out.println("接收到的DTO: " + authUserDTO);

        AuthUser authUser = authConvert.toEntity(authUserDTO);

        // 添加调试日志
        System.out.println("转换后的Entity: " + authUser);

        // 根据id查询用户是否存在--用户的id不可能被修改
        AuthUser user = authUserMapper.selectById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在!");
        }

        // 如果不是管理员的话只能修改自己的用户信息
        if (!StpUtil.hasRole("admin_user")) {
            // 获取当前登录用户ID
            Long currentUserId = StpUtil.getLoginIdAsLong();
            // 如果当前登录用户不是要修改的用户，则抛出异常
            if (!currentUserId.equals(id)) {
                throw new RuntimeException("没有权限修改其他用户信息");
            }
        }
        // 修改用户信息
        int updated = authUserMapper.update(authUser);

        return updated != 0;
    }

    /**
     * 根据用户id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public AuthUserDTO queryById(Long id) {
        //根据传递的id查询数据库
        AuthUser authUser = authUserMapper.selectById(id);
        if (authUser == null) {
            throw new RuntimeException("用户不存在");
        }
        //对象转换返回
        AuthUserDTO authUserDTO = authConvert.toDto(authUser);

        return authUserDTO;
    }


    /**
     * 批量新增用户
     *
     * @param authUserDTOList
     * @return
     */
    @Override
    public Boolean insertBatch(List<AuthUserDTO> authUserDTOList) {

        // 1. 参数校验
        if (authUserDTOList == null || authUserDTOList.isEmpty()) {
            throw new IllegalArgumentException("用户列表不能为空");
        }

        // 2. 数据量控制（防止一次性插入过多数据）
        if (authUserDTOList.size() > 1000) {
            throw new IllegalArgumentException("单次批量插入不能超过1000条记录");
        }

        // 3. 业务数据校验
        for (AuthUserDTO dto : authUserDTOList) {
            if (dto.getUserName() == null || dto.getUserName().trim().isEmpty()) {
                throw new IllegalArgumentException("用户名不能为空");
            }
        }
        //集合对象转换
        List<AuthUser> authUserList = authConvert.toEntityList(authUserDTOList);

        //批量插入数据库
        int insertBatch = authUserMapper.insertBatch(authUserList);

        // 7. 返回结果（检查是否全部插入成功）
        return insertBatch == authUserDTOList.size();
    }

    /**
     * 用户退出登录
     *
     * @param authUserDTO
     * @return
     */
    @Override
    public Boolean logout(AuthUserDTO authUserDTO) {

        AuthUser authUser = authConvert.toEntity(authUserDTO);

        AuthUser user = authUserMapper.selectByCondition(authUser);

        if (user == null) {
            throw new RuntimeException("用户不存在,无法继续操作");
        }
        StpUtil.logout(user.getId());

        return true;
    }


}
