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
import com.shea.project.dto.req.RecycleBinRecoverReqDTO;
import com.shea.project.dto.req.RecycleBinRemoveReqDTO;
import com.shea.project.dto.req.RecycleBinReqDTO;
import com.shea.project.dto.req.ShortLinkRecycleBinPageReqDTO;
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
    // TODO 此处请求参数为null，明天记得更正！！！
    public IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkRecycleBinPageReqDTO shortLinkRecycleBinPageReqDTO) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .in(ShortLinkDO::getGid, shortLinkRecycleBinPageReqDTO.getGids())
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 1)
                .orderByDesc(ShortLinkDO::getCreateTime);
        IPage<ShortLinkDO> resultPage = baseMapper.selectPage(shortLinkRecycleBinPageReqDTO, queryWrapper);
        return resultPage.convert(each -> {
            ShortLinkPageRespDTO result = BeanUtil.toBean(each, ShortLinkPageRespDTO.class);
            result.setDomain("http://" + result.getDomain());
            return result;
        });
    }

    @Override
    public void recoverRecycleBin(RecycleBinRecoverReqDTO recycleBinRecoverReqDTO) {
        LambdaUpdateWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, recycleBinRecoverReqDTO.getGid())
                .eq(ShortLinkDO::getFullShortUrl, recycleBinRecoverReqDTO.getFullShortUrl())
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 1);
        ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                .enableStatus(0)
                .build();
        baseMapper.update(shortLinkDO, updateWrapper);
        stringRedisTemplate.delete(String.format(RedisKeyConstant.GOTO_IS_NULL_SHORT_LINK_KEY,
                recycleBinRecoverReqDTO.getFullShortUrl()));
    }

    @Override
    public void removeRecycleBin(RecycleBinRemoveReqDTO recycleBinRemoveReqDTO) {
        LambdaUpdateWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, recycleBinRemoveReqDTO.getGid())
                .eq(ShortLinkDO::getFullShortUrl, recycleBinRemoveReqDTO.getFullShortUrl())
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 1);
        ShortLinkDO shortLinkDO = new ShortLinkDO();
        shortLinkDO.setDelFlag(1);
        baseMapper.update(shortLinkDO, updateWrapper);
    }
}
