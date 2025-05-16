package com.shea.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shea.project.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.shea.project.dto.req.ShortLinkStatsReqDTO;
import com.shea.project.dto.resp.ShortLinkStatsAccessRecordRespDTO;
import com.shea.project.dto.resp.ShortLinkStatsRespDTO;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/15 15:21
 */
public interface IShortLInkStatsService {

    /**
     * 获取访问单个短链接指定时间内统计数据
     *
     * @param shortLinkStatsReqDTO 请求参数
     * @return 指定时间内统计数据
     */
    ShortLinkStatsRespDTO oneShortLinkStats(ShortLinkStatsReqDTO shortLinkStatsReqDTO);

    /**
     * 获取短链接监控用户访问记录
     *
     * @param shortLinkStatsAccessRecordReqDTO 用户访问记录请求参数
     * @return 用户访问记录
     */
    IPage<ShortLinkStatsAccessRecordRespDTO> shortLinkAccessRecordStats(ShortLinkStatsAccessRecordReqDTO shortLinkStatsAccessRecordReqDTO);
}
