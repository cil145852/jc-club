package com.cjl.auth.application.context;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-28-14:34
 * @Description
 */

public class LoginContextHolder {
    private static final InheritableThreadLocal<Map<String, Object>> THREAD_LOCAL
            = new InheritableThreadLocal<>();

    public static void set(String key, Object value) {
        getThreadLocalMap().put(key, value);
    }

    public static Object get(String key) {
        return getThreadLocalMap().get(key);
    }

    public static String getLoginId() {
        return (String) get("loginId");
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    private static Map<String, Object> getThreadLocalMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (Objects.isNull(map)) {
            map = new ConcurrentHashMap<>();
            THREAD_LOCAL.set(map);
        }
        return map;
    }

}
