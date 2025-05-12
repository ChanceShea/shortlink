package com.shea.project.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 短链接分组统计数量查询参数
 * @Author: Shea.
 * @Date: 2025/5/12 17:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortLinkGroupCountQueryDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 统计数量
     */
    private Integer count;
}
