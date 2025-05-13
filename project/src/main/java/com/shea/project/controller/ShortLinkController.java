package com.shea.project.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shea.project.common.convention.result.Result;
import com.shea.project.common.convention.result.Results;
import com.shea.project.dto.req.ShortLinkCreateReqDTO;
import com.shea.project.dto.req.ShortLinkPageReqDTO;
import com.shea.project.dto.req.ShortLinkUpdateReqDTO;
import com.shea.project.dto.resp.ShortLinkCreateRespDTO;
import com.shea.project.dto.resp.ShortLinkGroupCountQueryDTO;
import com.shea.project.dto.resp.ShortLinkPageRespDTO;
import com.shea.project.service.IShortLinkService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 短链接控制器
 *
 * @author Shea
 * @since 2025-05-11
 */
@RestController
@RequiredArgsConstructor
public class ShortLinkController {

    private final IShortLinkService shortLinkService;

    /**
     * 短链接跳转原始链接
     */
    @GetMapping("/{short-uri}")
    public void restoreUrl(@PathVariable("short-uri") String shortUri,
                           HttpServletRequest request,
                           HttpServletResponse response) throws IOException {
        shortLinkService.restoreUrl(shortUri, request, response);

    }

    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        return Results.success(shortLinkService.createShortLink(shortLinkCreateReqDTO));
    }

    /**
     * 修改短链接
     */
    @PutMapping("/api/short-link/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO shortLinkUpdateReqDTO) {
        shortLinkService.updateShortLink(shortLinkUpdateReqDTO);
        return Results.success();
    }

    /**
     * 短链接分页查询
     */
    @GetMapping("/api/short-link/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        return Results.success(shortLinkService.pageShortLink(shortLinkPageReqDTO));
    }

    /**
     * 短链接分组统计数量
     */
    @GetMapping("/api/short-link/v1/count")
    public Result<List<ShortLinkGroupCountQueryDTO>> groupShortLinkCount(@RequestParam("gids") List<String> gids) {
        return Results.success(shortLinkService.groupShortLinkCount(gids));
    }

}
