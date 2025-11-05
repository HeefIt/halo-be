package com.heef.halo.domain.basic.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.heef.halo.domain.basic.dto.authDTO.AuthUserDTO;
import com.heef.halo.domain.basic.service.AuthService;
import com.heef.halo.result.PageResult;
import com.heef.halo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户-鉴权模块
 *
 * @author heefM
 * @date 2025-10-31
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    /**
     * 分页查询用户列表数据
     *
     * @param authUserDTO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/user/selectPage")
    public Result<PageResult<AuthUserDTO>> selectPage(AuthUserDTO authUserDTO,
                                                      @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        try {
            if (log.isInfoEnabled()) {
                log.info("AuthController.selectPage.dto: {}"
                        , JSON.toJSONString(authUserDTO));
            }
            PageResult<AuthUserDTO> pageResult = authService.selectPage(authUserDTO, pageNum, pageSize);
            return Result.ok(pageResult);
        } catch (Exception e) {
            log.error("用户分页查询列表数据接口: ", e);
            return Result.fail("分页查询用户列表数据失败: " + e.getMessage());
        }
    }

    /**
     * 用户注册
     *
     * @param authUserDTO
     * @return
     */
    @PostMapping("/user/register")
    public Result<AuthUserDTO> register(@RequestBody AuthUserDTO authUserDTO) {
        try {
            Preconditions.checkNotNull(authUserDTO, "用户注册对象不能为空!");//checkNotNull 检查的是对象
            Preconditions.checkArgument(!StringUtils.isBlank(authUserDTO.getUserName()), "用户名不能为空!");
            Boolean result = authService.insert(authUserDTO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("用户注册接口: ", e);
            return Result.fail("用户注册接口错误: " + e.getMessage());
        }
    }

    /**
     * 用户登录接口
     *
     * @param authUserDTO
     * @return
     */
    @PostMapping("/user/login")
    private Result<Boolean> login(AuthUserDTO authUserDTO) {
        try {
            Preconditions.checkArgument(!StringUtils.isBlank(authUserDTO.getUserName()), "用户名不能为空!");
            Preconditions.checkArgument(!StringUtils.isBlank(authUserDTO.getPassword()), "密码不能为空!");
            Boolean result = authService.login(authUserDTO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("用户登录接口: ", e);
            return Result.fail("登录接口调用错误: " + e.getMessage());
        }
    }

    /**
     * 用户删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/user/delete/{id}")
    private Result<Boolean> delete(@PathVariable Long id) {
        try {
            Boolean result = authService.delete(id);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("用户删除接口: ", e);
            return Result.fail( e.getMessage());
        }
    }

    /**
     * 用户修改
     * @param authUserDTO
     * @return
     */
    @PostMapping("/user/update")
    private Result<Boolean> update(@RequestBody AuthUserDTO authUserDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("AuthController.update.dto: {}"
                        , JSON.toJSONString(authUserDTO));
            }
            Boolean result = authService.update(authUserDTO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("用户修改接口: ", e);
            return Result.fail(e.getMessage());
        }
    }



}
