package com.shea.admin.dto.req;

import lombok.Data;

/**
 * @description: 分组更新请求参数
 * @Author: Shea.
 * @Date: 2025/5/11 14:32
 */

@Data
public class GroupUpdateReqDTO {

    /**
     * 分组名称
     */
    private String name;

    /**
     * 分组标识
     */
    private String gid;
}
