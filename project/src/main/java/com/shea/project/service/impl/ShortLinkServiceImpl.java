package com.shea.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shea.project.common.convention.exception.ClientException;
import com.shea.project.dao.entity.ShortLinkDO;
import com.shea.project.dao.mapper.IShortLinkMapper;
import com.shea.project.dto.req.ShortLinkCreateReqDTO;
import com.shea.project.dto.req.ShortLinkPageReqDTO;
import com.shea.project.dto.req.ShortLinkUpdateReqDTO;
import com.shea.project.dto.resp.ShortLinkCreateRespDTO;
import com.shea.project.dto.resp.ShortLinkGroupCountQueryDTO;
import com.shea.project.dto.resp.ShortLinkPageRespDTO;
import com.shea.project.service.IShortLinkService;
import com.shea.project.toolkit.HashUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.shea.project.common.enums.ValidDateTypeEnums.PERMANENT;

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

    @Override
    public List<ShortLinkGroupCountQueryDTO> groupShortLinkCount(List<String> gids) {
        QueryWrapper<ShortLinkDO> wrapper = Wrappers.query(new ShortLinkDO())
                .select("gid as gid,count(*) as count")
                .in("gid", gids)
                .eq("enable_status", 0)
                .groupBy("gid");
        List<Map<String, Object>> shortLinkCount = baseMapper.selectMaps(wrapper);
        return BeanUtil.copyToList(shortLinkCount, ShortLinkGroupCountQueryDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateShortLink(ShortLinkUpdateReqDTO shortLinkUpdateReqDTO) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, shortLinkUpdateReqDTO.getGid())
                .eq(ShortLinkDO::getFullShortUrl, shortLinkUpdateReqDTO.getFullShortUrl())
                .eq(ShortLinkDO::getEnableStatus, 0)
                .eq(ShortLinkDO::getDelFlag, 0);
        ShortLinkDO hasShortLinkDO = baseMapper.selectOne(queryWrapper);
        if (hasShortLinkDO == null) {
            throw new ClientException("短链接记录不存在");
        }
        ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                .domain(hasShortLinkDO.getDomain())
                .shortUri(hasShortLinkDO.getShortUri())
                .clickNum(hasShortLinkDO.getClickNum())
                .favicon(hasShortLinkDO.getFavicon())
                .gid(shortLinkUpdateReqDTO.getGid())
                .originUrl(shortLinkUpdateReqDTO.getOriginUrl())
                .description(shortLinkUpdateReqDTO.getDescription())
                .validDateType(shortLinkUpdateReqDTO.getValidDateType())
                .validDate(shortLinkUpdateReqDTO.getValidDate())
                .build();
        if (Objects.equals(hasShortLinkDO.getGid(), shortLinkUpdateReqDTO.getGid())) {
            LambdaUpdateWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, shortLinkUpdateReqDTO.getFullShortUrl())
                    .eq(ShortLinkDO::getGid, shortLinkUpdateReqDTO.getGid())
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .eq(ShortLinkDO::getEnableStatus, 0)
                    .set(Objects.equals(shortLinkUpdateReqDTO.getValidDateType(), PERMANENT.getType()), ShortLinkDO::getValidDate, null);
            baseMapper.update(shortLinkDO, updateWrapper);
        } else {
            LambdaUpdateWrapper<ShortLinkDO> wrapper = Wrappers.lambdaUpdate(ShortLinkDO.class)
                    .eq(ShortLinkDO::getFullShortUrl, shortLinkUpdateReqDTO.getFullShortUrl())
                    .eq(ShortLinkDO::getGid, hasShortLinkDO.getGid())
                    .eq(ShortLinkDO::getDelFlag, 0)
                    .eq(ShortLinkDO::getEnableStatus, 0);
            baseMapper.delete(wrapper);
            baseMapper.insert(shortLinkDO);
        }
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
