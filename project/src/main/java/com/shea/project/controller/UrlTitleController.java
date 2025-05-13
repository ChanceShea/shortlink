package com.shea.project.controller;

import com.shea.project.common.convention.result.Result;
import com.shea.project.common.convention.result.Results;
import com.shea.project.service.IUrlTitleService;
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

    private final IUrlTitleService urlTitleService;

    @GetMapping("/api/short-link/v1/title")
    public Result<String> getTitleByUrl(@RequestParam("url") String url) {
        return Results.success(urlTitleService.getUrlTitle(url));
    }
}
