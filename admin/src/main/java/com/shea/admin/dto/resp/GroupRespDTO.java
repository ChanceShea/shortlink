package com.shea.admin.dto.resp;

import lombok.Data;

/**
 * @description: 查询分组信息返回对象
 * @Author: Shea.
 * @Date: 2025/5/11 10:38
 */
@Data
public class GroupRespDTO {
    private Long id;

    private String gid;

    private String name;

    private Integer sortOrder;
}
