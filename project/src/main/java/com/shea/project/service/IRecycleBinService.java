package com.shea.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shea.project.dao.entity.ShortLinkDO;
import com.shea.project.dto.req.RecycleBinReqDTO;
import com.shea.project.dto.req.ShortLinkPageReqDTO;
import com.shea.project.dto.resp.ShortLinkPageRespDTO;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/12 21:58
 */
public interface IRecycleBinService extends IService<ShortLinkDO> {
    /**
     * 将短链接移入回收站
     *
     * @param recycleBinReqDTO 短链接移入回收站参数
     */
    void saveRecycleBin(RecycleBinReqDTO recycleBinReqDTO);

    /**
     * 分页查询回收站短链接
     *
     * @param shortLinkPageReqDTO 分页查询短链接参数
     * @return 分页查询短链接返回参数
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO);
}
