package com.shea.admin.dto.resp;

import lombok.Data;

/**
 * @description: 查询分组信息返回对象
 * @Author: Shea.
 * @Date: 2025/5/11 10:38
 */
@Data
public class GroupRespDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 分组排序
     */
    private Integer sortOrder;

    /**
     * 分组下短链接数量
     */
    private Integer shortLinkCount;
}

