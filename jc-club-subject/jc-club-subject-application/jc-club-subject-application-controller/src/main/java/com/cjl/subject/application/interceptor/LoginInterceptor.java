package com.cjl.subject.application.interceptor;

import com.cjl.subject.common.context.LoginContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-28-14:28
 * @Description
 */

public class LoginInterceptor implements HandlerInterceptor {
    public static final String LOGIN_HEADER = "loginId";
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginContextHolder.remove();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String loginId = request.getHeader(LOGIN_HEADER);
        if (StringUtils.isNotBlank(loginId)) {
            LoginContextHolder.set(LOGIN_HEADER, loginId);
        }
        return true;
    }
}
