package com.heef.halo.domain.basic.service;

import com.heef.halo.domain.basic.dto.authDTO.AuthUserDTO;
import com.heef.halo.result.PageResult;

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
    Boolean login(AuthUserDTO authUserDTO);

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
    Boolean update(AuthUserDTO authUserDTO);
}
