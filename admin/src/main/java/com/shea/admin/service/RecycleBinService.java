package com.shea.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shea.admin.common.convention.result.Result;
import com.shea.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.shea.admin.remote.dto.resp.ShortLinkPageRespDTO;

/**
 * @description: 短链接回收站业务层
 * @Author: Shea.
 * @Date: 2025/5/13 22:13
 */
public interface RecycleBinService {

    /**
     * 分页查询回收站短链接
     *
     * @param shortLinkPageReqDTO 请求参数
     * @return 响应参数
     */
    Result<IPage<ShortLinkPageRespDTO>> pageRecycleShortLink(ShortLinkRecycleBinPageReqDTO shortLinkPageReqDTO);
}
