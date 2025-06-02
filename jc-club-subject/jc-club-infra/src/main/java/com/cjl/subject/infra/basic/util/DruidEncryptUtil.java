package com.cjl.subject.infra.basic.util;

import com.alibaba.druid.filter.config.ConfigTools;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * @author liang
 * @version 1.0
 * @CreateDate 2025-06-01-19:43
 * @Description
 */

public class DruidEncryptUtil {
    private static String privateKey;
    private static String publicKey;

    static {
        try {
            String[] keyPair = ConfigTools.genKeyPair(512);
            privateKey = keyPair[0];
            System.out.println("privateKey: " + privateKey);
            publicKey = keyPair[1];
            System.out.println("publicKey: " + publicKey);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String plainText) {
        try {
            return ConfigTools.encrypt(privateKey, plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encryptText) {
        try {
            return ConfigTools.decrypt(publicKey, encryptText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
