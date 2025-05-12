package com.shea.admin.common.convention.errorcode;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/10 15:56
 */

/**
 * 平台错误码
 */
public interface IErrorCode {

    /**
     * 错误码
     */
    String code();

    /**
     * 错误信息
     */
    String message();
}