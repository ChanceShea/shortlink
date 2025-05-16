package com.shea.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shea.project.dao.entity.LinkAccessLogsDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 短链接监控访问记录请求参数
 * @Author: Shea.
 * @Date: 2025/5/15 15:12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortLinkStatsAccessRecordReqDTO extends Page<LinkAccessLogsDO> {

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;
}
