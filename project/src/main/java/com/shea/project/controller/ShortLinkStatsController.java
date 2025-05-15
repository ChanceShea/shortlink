package com.shea.project.controller;

import com.shea.project.common.convention.result.Result;
import com.shea.project.common.convention.result.Results;
import com.shea.project.dto.req.ShortLinkStatsReqDTO;
import com.shea.project.dto.resp.ShortLinkStatsRespDTO;
import com.shea.project.service.IShortLInkStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 短链接监控统计控制器
 * @Author: Shea.
 * @Date: 2025/5/15 15:09
 */
@RestController
@RequiredArgsConstructor
public class ShortLinkStatsController {

    private final IShortLInkStatsService shortLinkStatsService;

    /**
     * 获取访问单个短链接指定时间内统计数据
     */
    @GetMapping("/api/short-link/v1/stats")
    public Result<ShortLinkStatsRespDTO> getShortLinkStats(@RequestBody ShortLinkStatsReqDTO shortLinkStatsReqDTO) {
        return Results.success(shortLinkStatsService.oneShortLinkStats(shortLinkStatsReqDTO));
    }
}
