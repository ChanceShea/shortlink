package com.shea.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shea.project.dao.entity.ShortLinkDO;
import com.shea.project.dto.resp.ShortLinkGroupCountQueryDTO;

import java.util.List;

/**
 * 短链接持久层
 *
 * @author Shea
 * @since 2025-05-11
 */
public interface IShortLinkMapper extends BaseMapper<ShortLinkDO> {

    ShortLinkGroupCountQueryDTO listCount(List<String> gids);
}
