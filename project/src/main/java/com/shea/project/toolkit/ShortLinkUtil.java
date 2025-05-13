package com.shea.project.toolkit;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.Optional;

import static com.shea.project.common.constants.ShortLinkConstant.DEFAULT_CACHE_VALID_TIME;

/**
 * @description: 短链接工具类
 * @Author: Shea.
 * @Date: 2025/5/13 17:13
 */
public class ShortLinkUtil {

    /**
     * 获取短链接的缓存有效时间
     *
     * @param validDate 短链接有效期
     * @return 缓存有效时间戳(毫秒)
     */
    public static long getLinkCacheValidTime(Date validDate) {
        return Optional.ofNullable(validDate)
                .map(each -> DateUtil.between(each, new Date(), DateUnit.MS))
                .orElse(DEFAULT_CACHE_VALID_TIME);
    }
}
