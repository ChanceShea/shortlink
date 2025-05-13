package com.shea.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shea.project.common.constants.RedisKeyConstant;
import com.shea.project.dao.entity.ShortLinkDO;
import com.shea.project.dao.mapper.IShortLinkMapper;
import com.shea.project.dto.req.RecycleBinReqDTO;
import com.shea.project.dto.req.ShortLinkPageReqDTO;
import com.shea.project.dto.resp.ShortLinkPageRespDTO;
import com.shea.project.service.IRecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/12 21:58
 */
@Service
@RequiredArgsConstructor
public class RecycleBinServiceImpl extends ServiceImpl<IShortLinkMapper, ShortLinkDO> implements IRecycleBinService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void saveRecycleBin(RecycleBinReqDTO recycleBinReqDTO) {
        LambdaUpdateWrapper<ShortLinkDO> wrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, recycleBinReqDTO.getGid())
                .eq(ShortLinkDO::getFullShortUrl, recycleBinReqDTO.getFullShortUrl())
                .eq(ShortLinkDO::getEnableStatus, 0)
                .eq(ShortLinkDO::getDelFlag, 0);
        ShortLinkDO dto = ShortLinkDO.builder()
                .enableStatus(1)
                .build();
        baseMapper.update(dto, wrapper);

        stringRedisTemplate.delete(String
                .format(RedisKeyConstant.GOTO_SHORT_LINK_KEY, recycleBinReqDTO.getFullShortUrl()));
    }

    @Override
    public IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, shortLinkPageReqDTO.getGid())
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 1)
                .orderByDesc(ShortLinkDO::getCreateTime);
        IPage<ShortLinkDO> resultPage = baseMapper.selectPage(shortLinkPageReqDTO, queryWrapper);
        return resultPage.convert(one -> BeanUtil.toBean(one, ShortLinkPageRespDTO.class));
    }
}
