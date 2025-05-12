package com.shea.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shea.project.dao.entity.ShortLinkDO;
import com.shea.project.dto.req.ShortLinkCreateReqDTO;
import com.shea.project.dto.req.ShortLinkPageReqDTO;
import com.shea.project.dto.resp.ShortLinkCreateRespDTO;
import com.shea.project.dto.resp.ShortLinkPageRespDTO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Shea
 * @since 2025-05-11
 */
public interface IShortLinkService extends IService<ShortLinkDO> {

    /**
     * 创建短链接
     *
     * @param shortLinkCreateReqDTO 短链接创建参数
     * @return 短链接返回参数
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO);

    /**
     * 短链接分页查询
     *
     * @param shortLinkPageReqDTO 短链接分页查询参数
     * @return 短链接分页查询结果
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO);
}
