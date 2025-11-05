package com.heef.halo.domain.util;


import com.heef.halo.domain.context.LoginContextHolder;

/**
 * 登录应用上下文工具类
 *
 * @author heefM
 * @date 2025-07-21
 */
public class LoginUtil {

    /**
     * 获取登录用户ID
     *
     * @return
     */
    public static String getLoginId(){
        return LoginContextHolder.getLoginId();
    }

}
