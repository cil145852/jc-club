package com.cjl.subject.infra.config;

import com.cjl.subject.common.enums.IsDeletedFlagEnum;
import com.cjl.subject.common.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-07-17-9:54
 * @Description mybatis拦截器用于填充公共字段
 */
@Component
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        if (Objects.isNull(parameter)) {
            return invocation.proceed();
        }
        String loginId = LoginUtil.getLoginId();
        if (StringUtils.isBlank(loginId)) {
            return invocation.proceed();
        }
        if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
            replace(parameter, sqlCommandType, loginId);
        }
        return invocation.proceed();
    }

    private void replace(Object parameter, SqlCommandType sqlCommandType, String loginId) {
        if (parameter instanceof Map) {
            replaceMap((Map) parameter, sqlCommandType, loginId);
        } else {
            replaceEntity(parameter, sqlCommandType, loginId);
        }
    }

    private void replaceEntity(Object parameter, SqlCommandType sqlCommandType, String loginId) {
        if (sqlCommandType.equals(SqlCommandType.INSERT)) {
            dealInsert(parameter, loginId);
        } else {
            dealUpdate(parameter, loginId);
        }
    }

    private void dealUpdate(Object parameter, String loginId) {
        Field[] fields = getAllField(parameter);
        for (Field field : fields) {
            try {
                Object o = field.get(parameter);
                if (Objects.isNull(o)) {
                    continue;
                }
                if ("updateBy".equals(field.getName())) {
                    setFieldValue(parameter, field, loginId);
                } else if ("updateTime".equals(field.getName())) {
                    setFieldValue(parameter, field, new Date());
                }
            } catch (Exception e) {
                log.error("dealUpdate.exception:{}", e.getMessage(), e);
            }
        }
    }

    private void dealInsert(Object parameter, String loginId) {
        Field[] fields = getAllField(parameter);
        for (Field field : fields) {
            try {
                if ("createdBy".equals(field.getName())) {
                    setFieldValue(parameter, field, loginId);
                } else if ("createdTime".equals(field.getName())) {
                    setFieldValue(parameter, field, new Date());
                } else if ("isDeleted".equals(field.getName())) {
                    setFieldValue(parameter, field, IsDeletedFlagEnum.UN_DELETED.getCode());
                }
            } catch (Exception e) {
                log.error("dealUpdate.exception:{}", e.getMessage(), e);
            }
        }
    }

    private void replaceMap(Map parameter, SqlCommandType sqlCommandType, String loginId) {
        Collection values = parameter.values();
        for (Object value : values) {
            replaceEntity(value, sqlCommandType, loginId);
        }
    }

    private Field[] getAllField(Object obj) {
        Class<?> clazz = obj.getClass();
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields.toArray(new Field[0]);
    }

    private void setFieldValue(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(obj);
            if (Objects.nonNull(fieldValue)) {
                field.setAccessible(false);
                return;
            }
            field.set(obj, value);
            field.setAccessible(false);
        } catch (Exception e) {
            log.error("setFieldValue.exception:{}", e.getMessage(), e);
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
