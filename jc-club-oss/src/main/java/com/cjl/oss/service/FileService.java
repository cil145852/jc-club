package com.cjl.oss.service;

import com.cjl.oss.adapter.StorageAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-09-15:28
 * @Description
 */
@Service
public class FileService {
    private final StorageAdapter storageAdapter;

    public FileService(StorageAdapter storageAdapter) {
        this.storageAdapter = storageAdapter;
    }

    public List<String> listAllBucket() {
        return storageAdapter.listAllBucket();
    }

    public String getUrl(String bucketName, String fileName) {
        return storageAdapter.getUrl(bucketName, fileName);
    }

    public void uploadFile(MultipartFile multipartFile, String bucketName, String dirName) {
        storageAdapter.uploadFile(multipartFile, bucketName, dirName);
    }
}
