package com.shea.project.dto.req;

import lombok.Data;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/12 21:53
 */
@Data
public class RecycleBinReqDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;
}
