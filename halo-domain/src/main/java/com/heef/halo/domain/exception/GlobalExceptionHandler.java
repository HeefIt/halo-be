package com.heef.halo.domain.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.heef.halo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author heefM
 * @date 2025-11-05
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理Sa-Token未登录异常
     *
     * @param e 异常
     * @return 错误响应
     */
    @ExceptionHandler(NotLoginException.class)
    public Result<?> handleNotLoginException(NotLoginException e) {
        log.error("Sa-Token未登录异常: ", e);
        return Result.fail("用户未登录，请先登录");
    }

    /**
     * 处理Sa-Token角色权限异常
     *
     * @param e 异常
     * @return 错误响应
     */
    @ExceptionHandler(NotRoleException.class)
    public Result<?> handleNotRoleException(NotRoleException e) {
        log.error("Sa-Token角色权限异常: ", e);
        return Result.fail("用户角色权限不足");
    }

    /**
     * 处理Sa-Token权限码异常
     *
     * @param e 异常
     * @return 错误响应
     */
    @ExceptionHandler(NotPermissionException.class)
    public Result<?> handleNotPermissionException(NotPermissionException e) {
        log.error("Sa-Token权限码异常: ", e);
        return Result.fail("用户权限不足");
    }

    /**
     * 处理其他所有异常
     *
     * @param e 异常
     * @return 错误响应
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("系统异常: ", e);
        return Result.fail("系统异常，请联系管理员");
    }
}