package com.shea.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shea.admin.common.convention.result.Result;
import com.shea.admin.remote.ShortLinkRemoteService;
import com.shea.admin.remote.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.shea.admin.remote.dto.resp.ShortLinkStatsAccessRecordRespDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/16 08:38
 */
@RestController
public class ShortLinkStatsController {

    // TODO 后续重构为Spring Cloud Feign调用
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };


    /**
     * 获取短链接监控用户访问记录
     */
    @GetMapping("/api/short-link/admin/v1/stats/access-record")
    public Result<IPage<ShortLinkStatsAccessRecordRespDTO>> shortLinkAccessRecordStats(ShortLinkStatsAccessRecordReqDTO shortLinkStatsAccessRecordReqDTO) {
        return shortLinkRemoteService.shortLinkAccessRecordStats(shortLinkStatsAccessRecordReqDTO);
    }
}
