package com.cjl.subject.application.controller;

import com.cjl.subject.infra.basic.entity.SubjectCategory;
import com.cjl.subject.infra.basic.service.SubjectCategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-01-16:36
 * @Description
 */
@RestController
public class SubjectController {
    @Resource
    private SubjectCategoryService subjectCategoryService;

    @RequestMapping("/test")
    public String hello() {
        SubjectCategory subjectCategory = subjectCategoryService.queryById(1L);
        return subjectCategory.getCategoryName();
    }
}
