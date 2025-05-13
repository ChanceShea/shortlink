package com.shea.project.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 短链有效期枚举
 *
 * @author shea
 * @date 2023/1/17
 */
@RequiredArgsConstructor
public enum ValidDateTypeEnums {

    /**
     * 永久有效
     */
    PERMANENT(0),

    /**
     * 用户自定义有效期1
     */
    CUSTOM(1);

    @Getter
    private final Integer type;
}
