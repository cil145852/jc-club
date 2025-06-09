package com.cjl.oss.entity;

import lombok.Data;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-09-11:14
 * @Description 从minio获取的文件信息
 */
@Data
public class FileInfo {
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 是否是文件夹
     */
    private Boolean isDir;
    /**
     * 文件大小
     */
    private String etag;

}
