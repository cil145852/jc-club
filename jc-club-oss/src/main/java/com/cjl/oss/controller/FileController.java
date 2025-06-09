package com.cjl.oss.controller;

import com.cjl.oss.util.MinioUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-09-11:32
 * @Description
 */
@RestController
public class FileController {
    @Resource
    private MinioUtil minioUtil;

    @GetMapping("/test")
    public String test() throws Exception {
        return minioUtil.listAllBucket().toString();
    }
}
