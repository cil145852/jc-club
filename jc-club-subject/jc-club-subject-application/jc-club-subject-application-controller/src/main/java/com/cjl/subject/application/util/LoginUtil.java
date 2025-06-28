package com.cjl.subject.application.util;

import com.cjl.subject.application.context.LoginContextHolder;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-28-14:52
 * @Description
 */

public class LoginUtil {
    public static String getLoginId() {
        return LoginContextHolder.getLoginId();
    }
}
