package com.shea.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shea.admin.common.convention.result.Result;
import com.shea.admin.common.convention.result.Results;
import com.shea.admin.remote.ShortLinkRemoteService;
import com.shea.admin.remote.dto.req.RecycleBinRecoverReqDTO;
import com.shea.admin.remote.dto.req.RecycleBinRemoveReqDTO;
import com.shea.admin.remote.dto.req.RecycleBinReqDTO;
import com.shea.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.shea.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.shea.admin.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/13 21:48
 */
@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    /**
     * 将短链接移至回收站
     */
    @PostMapping("/api/short-link/recycle-bin/admin/v1/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinReqDTO recycleBinReqDTO) {
        shortLinkRemoteService.saveRecycleBin(recycleBinReqDTO);
        return Results.success();
    }

    /**
     * 回收站短链接分页查询
     */
    @GetMapping("/api/short-link/recycle-bin/admin/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkRecycleBinPageReqDTO shortLinkPageReqDTO) {
        return recycleBinService.pageRecycleShortLink(shortLinkPageReqDTO);
    }

    /**
     * 回收站恢复短链接
     */
    @PostMapping("/api/short-link/recycle-bin/admin/v1/recover")
    public Result<Void> recoverRecycleBin(@RequestBody RecycleBinRecoverReqDTO recycleBinRecoverReqDTO) {
        shortLinkRemoteService.recoverRecycleBin(recycleBinRecoverReqDTO);
        return Results.success();
    }

    @PostMapping("/api/short-link/recycle-bin/admin/v1/remove")
    public Result<Void> removeRecycleBin(@RequestBody RecycleBinRemoveReqDTO recycleBinRemoveReqDTO) {
        shortLinkRemoteService.removeRecycleBin(recycleBinRemoveReqDTO);
        return Results.success();
    }
}
