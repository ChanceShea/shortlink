package com.shea.admin.toolkit;

import java.util.Random;


/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/11 10:04
 */
public final class RandomStringUtils {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    /**
     * 生成指定长度的随机字符串，包含数字和字母
     *
     * @param length 随机字符串的长度
     * @return 指定长度的随机字符串
     */
    public static String generateRandomString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 生成一个六位随机字符串，包含数字和字母
     *
     * @return 六位随机字符串
     */
    public static String generateRandomString() {
        return generateRandomString(6);
    }

}