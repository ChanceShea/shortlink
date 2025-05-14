package com.shea.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shea.project.dao.entity.ShortLinkDO;
import lombok.Data;

import java.util.List;

/**
 * @description: 回收站短链接分页请求参数
 * @Author: Shea.
 * @Date: 2025/5/12 14:15
 */
@Data
public class ShortLinkRecycleBinPageReqDTO extends Page<ShortLinkDO> {

    /**
     * 分组标识
     */
    private List<String> gids;
}
