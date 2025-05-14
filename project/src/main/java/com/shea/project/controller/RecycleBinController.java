package com.shea.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shea.project.common.convention.result.Result;
import com.shea.project.common.convention.result.Results;
import com.shea.project.dto.req.RecycleBinRecoverReqDTO;
import com.shea.project.dto.req.RecycleBinRemoveReqDTO;
import com.shea.project.dto.req.RecycleBinReqDTO;
import com.shea.project.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.shea.project.dto.resp.ShortLinkPageRespDTO;
import com.shea.project.service.IRecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/12 21:52
 */
@RestController
@RequiredArgsConstructor
public class RecycleBinController {

    private final IRecycleBinService recycleBinService;

    /**
     * 将短链接移至回收站
     */
    @PostMapping("/api/short-link/recycle-bin/v1/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinReqDTO recycleBinReqDTO) {
        recycleBinService.saveRecycleBin(recycleBinReqDTO);
        return Results.success();
    }

    /**
     * 回收站短链接分页查询
     */
    @GetMapping("/api/short-link/recycle-bin/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(@RequestParam ShortLinkRecycleBinPageReqDTO shortLinkRecycleBinPageReqDTO) {
        return Results.success(recycleBinService.pageShortLink(shortLinkRecycleBinPageReqDTO));
    }

    /**
     * 回收站恢复短链接
     */
    @PostMapping("/api/short-link/recycle-bin/v1/recover")
    public Result<Void> recoverRecycleBin(@RequestBody RecycleBinRecoverReqDTO recycleBinRecoverReqDTO) {
        recycleBinService.recoverRecycleBin(recycleBinRecoverReqDTO);
        return Results.success();
    }

    /**
     * 回收站移除短链接
     */
    @PostMapping("/api/short-link/recycle-bin/v1/remove")
    public Result<Void> removeRecycleBin(@RequestBody RecycleBinRemoveReqDTO recycleBinRemoveReqDTO) {
        recycleBinService.removeRecycleBin(recycleBinRemoveReqDTO);
        return Results.success();
    }
}
