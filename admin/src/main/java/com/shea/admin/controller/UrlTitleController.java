package com.shea.admin.controller;


import com.shea.admin.common.convention.result.Result;
import com.shea.admin.common.convention.result.Results;
import com.shea.admin.remote.ShortLinkRemoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 链接标题控制器
 * @Author: Shea.
 * @Date: 2025/5/13 17:47
 */
@RestController
@RequiredArgsConstructor
public class UrlTitleController {

    // TODO 后续重构为Spring Cloud Feign调用
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    @GetMapping("/api/short-link/admin/v1/title")
    public Result<String> getTitleByUrl(@RequestParam("url") String url) {
        return Results.success(shortLinkRemoteService.getUrlTitle(url));
    }
}
