package com.shea.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shea.project.common.convention.exception.ClientException;
import com.shea.project.dao.entity.ShortLinkDO;
import com.shea.project.dao.mapper.IShortLinkMapper;
import com.shea.project.dto.req.ShortLinkCreateReqDTO;
import com.shea.project.dto.req.ShortLinkPageReqDTO;
import com.shea.project.dto.resp.ShortLinkCreateRespDTO;
import com.shea.project.dto.resp.ShortLinkPageRespDTO;
import com.shea.project.service.IShortLinkService;
import com.shea.project.toolkit.HashUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Shea
 * @since 2025-05-11
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ShortLinkServiceImpl extends ServiceImpl<IShortLinkMapper, ShortLinkDO> implements IShortLinkService {

    private final RBloomFilter<String> rBloomFilter;

    @Override
    public ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO) {

        String suffix = generateSuffix(shortLinkCreateReqDTO);
        ShortLinkDO shortLinkDO = BeanUtil.toBean(shortLinkCreateReqDTO, ShortLinkDO.class);
        String fullShortUrl = shortLinkCreateReqDTO.getDomain() + "/" + suffix;
        shortLinkDO.setFullShortUrl(fullShortUrl);
        shortLinkDO.setShortUri(suffix);
        shortLinkDO.setEnableStatus(0);

        try {
            baseMapper.insert(shortLinkDO);
        } catch (DuplicateKeyException ex) {

            LambdaQueryWrapper<ShortLinkDO> wrapper = Wrappers
                    .lambdaQuery(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, fullShortUrl);
            ShortLinkDO one = baseMapper.selectOne(wrapper);

            if (one != null) {
                log.warn("短链接:{}已存在", fullShortUrl);
                throw new ClientException("短链接已存在");
            }

        }

        rBloomFilter.add(fullShortUrl);

        return ShortLinkCreateRespDTO.builder()
                .fullShortUrl(shortLinkDO.getFullShortUrl())
                .originUrl(shortLinkDO.getOriginUrl())
                .gid(shortLinkDO.getGid())
                .build();
    }

    @Override
    public IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        LambdaQueryWrapper<ShortLinkDO> wrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, shortLinkPageReqDTO.getGid())
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 0)
                .orderByDesc(ShortLinkDO::getCreateTime);
        IPage<ShortLinkDO> resultPage = baseMapper.selectPage(shortLinkPageReqDTO, wrapper);
        return resultPage.convert(one -> BeanUtil.toBean(one, ShortLinkPageRespDTO.class));
    }

    public String generateSuffix(ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        String originUrl = shortLinkCreateReqDTO.getOriginUrl();
        String shortUri;
        int customGenerateCount = 0;
        while (true) {
            if (customGenerateCount > 10) {
                throw new ClientException("短链接创建次数过多，请稍后再试");
            }
            originUrl += System.currentTimeMillis();
            shortUri = HashUtil.hashToBase62(originUrl);
            if (!rBloomFilter.contains(shortLinkCreateReqDTO.getDomain() + "/" + shortUri)) {
                break;
            }
            customGenerateCount++;
        }
        return shortUri;
    }
}
