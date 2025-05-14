package com.shea.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shea.admin.common.biz.user.UserContext;
import com.shea.admin.common.convention.exception.ClientException;
import com.shea.admin.common.convention.result.Result;
import com.shea.admin.dao.entity.GroupDO;
import com.shea.admin.dao.mapper.GroupMapper;
import com.shea.admin.remote.ShortLinkRemoteService;
import com.shea.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.shea.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.shea.admin.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/13 22:14
 */
@Service
@RequiredArgsConstructor
public class RecycleBinServiceImpl implements RecycleBinService {

    private final GroupMapper groupMapper;
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    @Override
    public Result<IPage<ShortLinkPageRespDTO>> pageRecycleShortLink(
            ShortLinkRecycleBinPageReqDTO shortLinkRecycleBinPageReqDTO) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getDelFlag, 0);
        List<GroupDO> groupDOList = groupMapper.selectList(queryWrapper);
        if (CollUtil.isEmpty(groupDOList)) {
            throw new ClientException("当前分组为空，无信息");
        }
        shortLinkRecycleBinPageReqDTO.setGids(groupDOList.stream().map(GroupDO::getGid).toList());
        return shortLinkRemoteService.pageRecycleShortLink(shortLinkRecycleBinPageReqDTO);
    }
}
