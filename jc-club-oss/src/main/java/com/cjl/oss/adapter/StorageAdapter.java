package com.cjl.oss.adapter;

import com.cjl.oss.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-09-14:41
 * @Description 文件存储适配器
 */

public interface StorageAdapter {
    /**
     * 创建bucket桶
     */
    void createBucket(String bucketName);

    /**
     * 上传文件
     */
    void uploadFile(MultipartFile multipartFile, String bucketName, String dirName);

    /**
     * 获取所有bucket桶
     */
    List<String> listAllBucket();

    /**
     * 获取当前通的所有文件
     */
    List<FileInfo> listAllFile(String bucketName);

    /**
     * 下载文件
     */
    InputStream downloadFile(String bucketName, String fileName);

    /**
     * 删除桶
     */
    void deleteBucket(String bucketName);

    /**
     * 删除文件
     */
    void deleteFile(String bucketName, String fileName);
}
