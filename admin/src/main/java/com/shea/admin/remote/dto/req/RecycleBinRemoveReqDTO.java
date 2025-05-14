package com.shea.admin.remote.dto.req;

import lombok.Data;

/**
 * @description: 短链接回收站移除请求参数
 * @Author: Shea.
 * @Date: 2025/5/12 21:53
 */
@Data
public class RecycleBinRemoveReqDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;
}
