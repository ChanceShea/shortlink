package com.shea.admin.dto.req;

import lombok.Data;

/**
 * @description: 短链接排序请求参数
 * @Author: Shea.
 * @Date: 2025/5/11 14:58
 */
@Data
public class GroupSortReqDTO {
    /**
     * 短链接id
     */
    private String gid;
    /**
     * 排序
     */
    private Integer sortOrder;
}
