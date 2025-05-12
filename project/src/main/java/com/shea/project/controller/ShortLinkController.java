package com.shea.project.controller;


import com.shea.project.common.convention.result.Result;
import com.shea.project.common.convention.result.Results;
import com.shea.project.dto.req.ShortLinkCreateReqDTO;
import com.shea.project.dto.resp.ShortLinkCreateRespDTO;
import com.shea.project.service.IShortLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
     * 创建短链接
     */
    @PostMapping("/api/short-link/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO shortLinkCreateReqDTO) {

        return Results.success(shortLinkService.createShortLink(shortLinkCreateReqDTO));
    }
}
