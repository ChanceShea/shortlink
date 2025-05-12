package com.shea.admin.remote;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shea.admin.common.convention.result.Result;
import com.shea.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.shea.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.shea.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.shea.admin.remote.dto.resp.ShortLinkPageRespDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/12 22:43
 */
public interface ShortLinkRemoteService {


    /**
     * 分页查询短链接
     *
     * @param shortLinkPageReqDTO 分页查询短链接请求参数
     * @return 分页查询短链接响应参数
     */
    default Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gid", shortLinkPageReqDTO.getGid());
        requestMap.put("current", shortLinkPageReqDTO.getCurrent());
        requestMap.put("size", shortLinkPageReqDTO.getSize());
        String s = HttpRequest.get("http://127.0.0.1:8081/api/short-link/v1/page")
                .form(requestMap) // 自动拼接到 query string
                .execute()
                .body();
//        Map map = JSON.parseObject(s, Map.class);
//        Map<String, Object> data = (Map<String, Object>) map.get("data");
//
//        Long total = ((Number) data.get("total")).longValue();
//        Long size = ((Number) data.get("size")).longValue();
//        Long current = ((Number) data.get("current")).longValue();
//        List<ShortLinkPageRespDTO> records = JSON.parseArray(JSON.toJSONString(data.get("records")), ShortLinkPageRespDTO.class);
//
//        IPage<ShortLinkPageRespDTO> page = new Page<>(current, size, total);
//        page.setRecords(records);
//        return Results.success(page);
        return JSON.parseObject(s, new TypeReference<>() {

        });
    }

    /**
     * 创建短链接
     *
     * @param shortLinkCreateReqDTO 创建短链接请求参数
     * @return 创建短链接响应参数
     */
    default Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        String requestBody = HttpUtil.post("http://127.0.0.1:8081/api/short-link/v1/create"
                , JSON.toJSONString(shortLinkCreateReqDTO));
        return JSON.parseObject(requestBody, new TypeReference<>() {
        });

    }
}
