package com.cjl.oss.adapter;

import com.cjl.oss.entity.FileInfo;
import com.cjl.oss.util.MinioUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-09-14:46
 * @Description
 */
public class MinioStorageAdapter implements StorageAdapter {
    @Resource
    private MinioUtil minioUtil;

    /**
     * minioUrl
     */
    @Value("${minio.url}")
    private String url;

    @Override
    public void createBucket(String bucketName) {
        try {
            minioUtil.createBucket(bucketName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void uploadFile(MultipartFile multipartFile, String bucketName, String dirName) {
        try {
            minioUtil.createBucket(bucketName);
            if (StringUtils.hasText(dirName)) {
                minioUtil.uploadFile(multipartFile.getInputStream(), bucketName, dirName + "/" + multipartFile.getName());
            } else {
                minioUtil.uploadFile(multipartFile.getInputStream(), bucketName, multipartFile.getName());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> listAllBucket() {
        try {
            return minioUtil.listAllBucket();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FileInfo> listAllFile(String bucketName) {
        try {
            return minioUtil.listAllFile(bucketName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream downloadFile(String bucketName, String fileName) {
        try {
            return minioUtil.downloadFile(bucketName, fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteBucket(String bucketName) {
        try {
            minioUtil.deleteBucket(bucketName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(String bucketName, String fileName) {
        try {
            minioUtil.deleteFile(bucketName, fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUrl(String bucketName, String fileName) {
        return url + "/" + bucketName + "/" + fileName;
    }
}
