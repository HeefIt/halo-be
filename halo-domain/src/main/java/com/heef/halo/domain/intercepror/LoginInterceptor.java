package com.heef.halo.domain.intercepror;


import com.heef.halo.domain.context.LoginContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器  (注册(自定义拦截器))
 *
 * @author heefM
 * @date 2025-07-21
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {


    /**
     * 拦截器    (重点)流程：将上游获取的loginId set到应用上下文里,  然后应用上下文在方法threadLocal里;
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头中获取用户id
        String loginId = request.getHeader("loginId");
        log.info("loginId:{}", loginId);
        //减用户id 设置(set)到应用上下文里
        if (!StringUtils.isBlank(loginId)){
            LoginContextHolder.set("loginId", loginId);
        }
        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     * 每一次获取上下文之后,都进行一个删除操作;
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @org.springframework.lang.Nullable Exception ex) throws Exception { /* compiled code */
        LoginContextHolder.remove();
    }

}
