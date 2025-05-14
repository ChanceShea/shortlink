package com.shea.admin.remote;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shea.admin.common.convention.result.Result;
import com.shea.admin.remote.dto.req.*;
import com.shea.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.shea.admin.remote.dto.resp.ShortLinkGroupCountRespDTO;
import com.shea.admin.remote.dto.resp.ShortLinkPageRespDTO;

import java.util.HashMap;
import java.util.List;
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

    /**
     * 修改短链接
     *
     * @param shortLinkUpdateReqDTO 短链接修改请求参数
     */
    default void updateShortLink(ShortLinkUpdateReqDTO shortLinkUpdateReqDTO) {
//        HttpUtil.post("http://127.0.0.1:8081/api/short-link/v1/update"
//                , JSON.toJSONString(shortLinkUpdateReqDTO));
        HttpRequest request = HttpUtil.createRequest(Method.PUT, "http://127.0.0.1:8081/api/short-link/v1/update");
        request.body(JSON.toJSONString(shortLinkUpdateReqDTO));
        request.execute();
    }

    /**
     * 查询短链接分组中的短链接数量
     *
     * @param gids 短链接分组标识集合
     * @return 短链接分组中的短链接数量集合
     */
    default Result<List<ShortLinkGroupCountRespDTO>> listGroupCount(List<String> gids) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gids", gids);

        String requestBody = HttpUtil.get("http://127.0.0.1:8081/api/short-link/v1/count"
                , requestMap);
        return JSON.parseObject(requestBody, new TypeReference<>() {
        });
    }

    /**
     * 根据网址获取标题
     *
     * @param url 目标网址
     * @return 目标网址标题
     */
    default String getUrlTitle(String url) {
        String resultStr = HttpUtil.get("http://127.0.0.1:8081/api/short-link/v1/title?url=" + url);
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }

    /**
     * 将短链接移至回收站
     *
     * @param recycleBinReqDTO 回收站请求参数
     */
    default void saveRecycleBin(RecycleBinReqDTO recycleBinReqDTO) {
        HttpUtil.post("http://127.0.0.1:8081/api/short-link/recycle-bin/v1/save",
                JSON.toJSONString(recycleBinReqDTO));
    }


    /**
     * 分页查询回收站短链接
     *
     * @param shortLinkPageReqDTO 分页查询回收站短链接请求参数
     * @return 分页查询回收站短链接响应参数
     */
    default Result<IPage<ShortLinkPageRespDTO>> pageRecycleShortLink(ShortLinkRecycleBinPageReqDTO shortLinkPageReqDTO) {
//        Map<String, Object> requestMap = new HashMap<>();
//        requestMap.put("gidList", shortLinkPageReqDTO.getGids());
//        requestMap.put("current", shortLinkPageReqDTO.getCurrent());
//        requestMap.put("size", shortLinkPageReqDTO.getSize());
//        String s = HttpRequest.get("http://127.0.0.1:8081/api/short-link/recycle-bin/v1/page")
//                .form(requestMap) // 更明确地控制 form 参数编码
//                .execute()
//                .body();

        String baseUrl = "http://127.0.0.1:8081/api/short-link/recycle-bin/v1/page";
        String ss = String.join(",", shortLinkPageReqDTO.getGids());
        System.out.println(ss);
        String query = "?gidList=" + String.join(",", shortLinkPageReqDTO.getGids())
                + "&current=" + shortLinkPageReqDTO.getCurrent()
                + "&size=" + shortLinkPageReqDTO.getSize();

        String s = HttpUtil.get(baseUrl + query);
        return JSON.parseObject(s, new TypeReference<>() {

        });
    }

    /**
     * 从回收站中恢复短链接
     *
     * @param recycleBinRecoverReqDTO 回收站恢复短链接请求参数
     */
    default void recoverRecycleBin(RecycleBinRecoverReqDTO recycleBinRecoverReqDTO) {
        HttpUtil.post("http://127.0.0.1:8081/api/short-link/recycle-bin/v1/recover",
                JSON.toJSONString(recycleBinRecoverReqDTO));
    }

    /**
     * 回收站移除短链接
     *
     * @param recycleBinRemoveReqDTO 回收站移除短链接请求参数
     */
    default void removeRecycleBin(RecycleBinRemoveReqDTO recycleBinRemoveReqDTO) {
        HttpUtil.post("http://127.0.0.1:8081/api/short-link/recycle-bin/v1/remove",
                JSON.toJSONString(recycleBinRemoveReqDTO));
    }
}
