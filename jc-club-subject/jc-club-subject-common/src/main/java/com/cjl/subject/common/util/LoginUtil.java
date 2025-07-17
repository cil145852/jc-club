package com.cjl.subject.common.util;

import com.cjl.subject.common.context.LoginContextHolder;

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
