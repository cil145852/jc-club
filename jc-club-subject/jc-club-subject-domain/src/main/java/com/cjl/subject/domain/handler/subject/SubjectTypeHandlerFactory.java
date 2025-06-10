package com.cjl.subject.domain.handler.subject;

import com.cjl.subject.common.enums.SubjectTypeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-04-10:13
 * @Description
 */
@Component
public class SubjectTypeHandlerFactory implements InitializingBean {
   @Resource
   private List<SubjectTypeHandler> subjectTypeHandlerList;

   private Map<SubjectTypeEnum, SubjectTypeHandler> subjectTypeHandlerMap;

   public SubjectTypeHandler getSubjectTypeHandler(Integer subjectType) {
      SubjectTypeEnum subjectTypeEnum = SubjectTypeEnum.getByCode(subjectType);
      return subjectTypeHandlerMap.get(subjectTypeEnum);
   }

   @Override
   public void afterPropertiesSet() throws Exception {
      subjectTypeHandlerMap = new HashMap<>();
      for (SubjectTypeHandler subjectTypeHandler : subjectTypeHandlerList) {
         subjectTypeHandlerMap.put(subjectTypeHandler.getSubjectType(), subjectTypeHandler);
      }
   }
}
