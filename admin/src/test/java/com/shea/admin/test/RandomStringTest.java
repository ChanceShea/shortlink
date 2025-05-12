package com.shea.admin.test;

import com.shea.admin.toolkit.RandomStringUtils;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/11 10:05
 */
public class RandomStringTest {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomStringUtils.generateRandomString());
        }
    }
}
