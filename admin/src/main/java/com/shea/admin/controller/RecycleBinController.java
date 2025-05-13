package com.shea.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shea.admin.common.convention.result.Result;
import com.shea.admin.common.convention.result.Results;
import com.shea.admin.remote.ShortLinkRemoteService;
import com.shea.admin.remote.dto.req.RecycleBinReqDTO;
import com.shea.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.shea.admin.remote.dto.resp.ShortLinkPageRespDTO;
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
public class RecycleBinController {

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
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        return shortLinkRemoteService.pageRecycleShortLink(shortLinkPageReqDTO);
    }
}
