package com.heef.halo.domain.basic.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.heef.halo.domain.basic.dto.authDTO.AuthUserDTO;
import com.heef.halo.result.PageResult;

import java.util.List;

public interface AuthService {
    /**
     * 分页查询用户数据
     * @param authUserDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult<AuthUserDTO> selectPage(AuthUserDTO authUserDTO, Integer pageNum, Integer pageSize);

    /**
     * 用户注册
     * @param authUserDTO
     * @return
     */
    Boolean insert(AuthUserDTO authUserDTO);

    /**
     * 用户登录
     * @param authUserDTO
     * @return
     */
    SaTokenInfo login(AuthUserDTO authUserDTO);

    /**
     * 删除用户
     * @param id
     * @return
     */
    Boolean delete(Long id);

    /**
     * 用户修改
     * @param authUserDTO
     * @return
     */
    Boolean update(Long id,AuthUserDTO authUserDTO);

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    AuthUserDTO queryById(Long id);

    /**
     * 批量新增用户
     * @param authUserDTOList
     * @return
     */
    Boolean insertBatch(List<AuthUserDTO> authUserDTOList);

    /**
     * 用户退出登录
     * @param authUserDTO
     * @return
     */
    Boolean logout(AuthUserDTO authUserDTO);
}
