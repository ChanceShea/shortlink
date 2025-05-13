package com.shea.admin.remote.dto.resp;

import lombok.Data;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/12 23:39
 */
@Data
public class ShortLinkGroupCountRespDTO {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 短链接数量
     */
    private Integer count;
}
