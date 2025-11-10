package com.heef.halo.domain.basic.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
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

import java.util.Collections;
import java.util.List;

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
     * 用户注册(新增)
     *
     * @param authUserDTO
     * @return
     */
    @SaIgnore
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
    @SaIgnore
    @PostMapping("/user/login")
    public Result<SaTokenInfo> login(@RequestBody AuthUserDTO authUserDTO) {
        try {
            Preconditions.checkArgument(!StringUtils.isBlank(authUserDTO.getUserName()), "用户名不能为空!");
            Preconditions.checkArgument(!StringUtils.isBlank(authUserDTO.getPassword()), "密码不能为空!");
            SaTokenInfo tokenInfo = authService.login(authUserDTO);  // 修改返回类型
            return Result.ok(tokenInfo);
        } catch (Exception e) {
            log.error("用户登录接口: ", e);
            return Result.fail("登录接口调用错误: " + e.getMessage());
        }
    }

    /**
     * 用户退出登录接口
     *
     * @return
     */
    @SaCheckLogin
    @PostMapping("/user/logout")
    public Result<Boolean> logout() {
        try {
            // Sa-Token 会自动从当前会话获取用户信息
            StpUtil.logout();
            return Result.ok(true);
        } catch (Exception e) {
            log.error("用户退出登录失败: ", e);
            return Result.fail("退出登录失败: " + e.getMessage());
        }
    }

    /**
     * 分页查询用户列表数据
     *
     * @param authUserDTO
     * @param pageNum
     * @param pageSize
     * @return
     */

    @SaCheckLogin
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
            if (log.isInfoEnabled()) {
                log.info("用户查询结果: {}"
                        , JSON.toJSONString(pageResult));
            }
            return Result.ok(pageResult);
        } catch (Exception e) {
            log.error("用户分页查询列表数据接口: ", e);
            return Result.fail("分页查询用户列表数据失败: " + e.getMessage());
        }
    }

    /**
     * 根据用户id查询用户详情信息
     *
     * @param id
     * @return
     */
    @SaCheckLogin
    @GetMapping("/user/queryById/{id}")
    public Result<AuthUserDTO> queryById(@PathVariable Long id) {
        try {
            AuthUserDTO result = authService.queryById(id);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("根据用户id查询用户信息接口: ", e);
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 用户删除
     *
     * @param id
     * @return
     */
    @SaCheckLogin
    @SaCheckRole("admin_user")
    @SaCheckPermission("user:delete")
    @DeleteMapping("/user/delete/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        try {
            Boolean result = authService.delete(id);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("用户删除接口: ", e);
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 用户修改
     *
     * @param id,authUserDTO
     * @return
     */
    @SaCheckLogin
    @PutMapping("/user/update/{id}")
    public Result<Boolean> update(@PathVariable Long id,
                                  @RequestBody AuthUserDTO authUserDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("AuthController.update.dto: {}"
                        , JSON.toJSONString(authUserDTO));
            }
            Boolean result = authService.update(id, authUserDTO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("用户修改接口: ", e);
            return Result.fail(e.getMessage());
        }
    }


    /**
     * 批量新增用户接口
     *
     * @param authUserDTOList
     * @return
     */
    @SaCheckLogin
    @SaCheckRole("admin_user")
    @PostMapping("/user/insertBatch")
    public Result<Boolean> insertBatch(@RequestBody List<AuthUserDTO> authUserDTOList) {
        try {
            Boolean result = authService.insertBatch(authUserDTOList);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("批量新增用户接口: ", e);
            return Result.fail("批量新增用户接口: " + e.getMessage());
        }
    }

    /**
     * 用户状态设置
     *
     * @param id
     * @param status
     * @return
     */
//    @SaCheckLogin
//    @SaCheckRole("admin_user")
    @PostMapping("/user/status")
    public Result<Boolean> setStatus(@RequestParam Long id, @RequestParam Integer status) {
        try {
            Boolean result = authService.setStatus(id, status);
            return Result.ok(result);
        } catch (Exception e) {
            return Result.fail("用户状态设置失败: " + e.getMessage());
        }
    }

    /**
     * 踢出用户下线
     *
     * @param id
     * @return
     */
    @SaCheckLogin
    @SaCheckRole("admin_user")
    @PostMapping("/user/kickout")
    public Result<Boolean> kickOut(@RequestParam Long id) {
        try {
            StpUtil.kickout(id);
            log.info("踢出用户下线:{}", id);
            return Result.ok("踢出用户下线成功");
        } catch (Exception e) {
            return Result.fail("踢出用户下线失败: " + e.getMessage());
        }
    }

    /**
     * 检查用户是否登录
     *
     * @param id
     * @return
     */
    @PostMapping("/user/isLogin")
    public Result<Boolean> isLogin(@RequestParam Long id) {
        try {
            boolean result = StpUtil.isLogin(id);
            log.info("检查用户是否登录:{}", id);
            return Result.ok("检查用户是否登录-结果是:" + result);
        } catch (Exception e) {
            return Result.fail("检查用户是否登录: " + e.getMessage());
        }
    }


    /**
     * 分配用户-角色信息
     *
     * @param userId
     * @param roleId
     * @return
     */
    @SaCheckLogin
    @SaCheckRole("admin_user")
    @PostMapping("/user/setRole")
    public Result<Boolean> assignRole(@RequestParam Long userId, @RequestParam Long roleId) {
        try {
            Boolean result = authService.assignRole(userId, roleId);
            return Result.ok("分配用户-角色信息-结果是:" + result);
        } catch (Exception e) {
            log.error("分配用户-角色信息失败", e);
            return Result.fail("分配用户-角色信息失败: " + e.getMessage());
        }
    }

}
