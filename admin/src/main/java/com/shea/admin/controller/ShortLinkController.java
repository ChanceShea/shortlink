package com.shea.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shea.admin.common.convention.result.Result;
import com.shea.admin.common.convention.result.Results;
import com.shea.admin.remote.ShortLinkRemoteService;
import com.shea.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.shea.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.shea.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import com.shea.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.shea.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 短链接控制层
 * @Author: Shea.
 * @Date: 2025/5/12 15:58
 */
@RestController
public class ShortLinkController {

    // TODO 后续重构为Spring Cloud Feign调用
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    /**
     * 短链接分页查询
     */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {

        return shortLinkRemoteService.pageShortLink(shortLinkPageReqDTO);
    }

    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        return shortLinkRemoteService.createShortLink(shortLinkCreateReqDTO);
    }

    /**
     * 修改短链接
     */
    @PutMapping("/api/short-link/admin/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO shortLinkUpdateReqDTO) {
        shortLinkRemoteService.updateShortLink(shortLinkUpdateReqDTO);
        return Results.success();
    }
}

