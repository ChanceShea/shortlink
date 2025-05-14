package com.shea.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shea.project.dao.entity.ShortLinkDO;
import com.shea.project.dto.req.RecycleBinRecoverReqDTO;
import com.shea.project.dto.req.RecycleBinRemoveReqDTO;
import com.shea.project.dto.req.RecycleBinReqDTO;
import com.shea.project.dto.req.ShortLinkRecycleBinPageReqDTO;
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
     * @param shortLinkRecycleBinPageReqDTO 分页查询短链接参数
     * @return 分页查询短链接返回参数
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkRecycleBinPageReqDTO shortLinkRecycleBinPageReqDTO);

    /**
     * 短链接回收站恢复功能
     *
     * @param recycleBinRecoverReqDTO 恢复参数
     */
    void recoverRecycleBin(RecycleBinRecoverReqDTO recycleBinRecoverReqDTO);

    /**
     * 回收站移除短链接
     *
     * @param recycleBinRemoveReqDTO 移除参数
     */
    void removeRecycleBin(RecycleBinRemoveReqDTO recycleBinRemoveReqDTO);
}
