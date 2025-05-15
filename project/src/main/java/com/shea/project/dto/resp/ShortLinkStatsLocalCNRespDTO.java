package com.shea.project.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 短链接地区监控响应参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortLinkStatsLocalCNRespDTO {

    /**
     * 统计
     */
    private Integer cnt;

    /**
     * 地区
     */
    private String local;

    /**
     * 占比
     */
    private Double ratio;
}