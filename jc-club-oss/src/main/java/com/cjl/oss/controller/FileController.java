package com.cjl.oss.controller;

import com.cjl.oss.service.FileService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    private FileService fileService;

    @RequestMapping("/getUrl")
    public String getUrl(String bucketName, String fileName) {
        return fileService.getUrl(bucketName, fileName);
    }

    @PostMapping("/upload")
    public String upload(
            @RequestParam("multipartFile") MultipartFile multipartFile,
            @RequestParam("bucket") String bucketName,
            @RequestParam("objectName") String dirName) {
        fileService.uploadFile(multipartFile, bucketName, dirName);
        String fileName;
        if (StringUtils.hasText(dirName)) {
            fileName = dirName + "/" + multipartFile.getOriginalFilename();
        } else {
            fileName = multipartFile.getOriginalFilename();
        }
        return fileService.getUrl(bucketName, fileName);
    }
}
