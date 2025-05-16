package com.shea.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shea.project.dao.entity.*;
import com.shea.project.dao.mapper.*;
import com.shea.project.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.shea.project.dto.req.ShortLinkStatsReqDTO;
import com.shea.project.dto.resp.*;
import com.shea.project.service.IShortLInkStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: TODO
 * @Author: Shea.
 * @Date: 2025/5/15 15:21
 */
@Service
@RequiredArgsConstructor
public class ShortLinkStatsServiceImpl implements IShortLInkStatsService {

    private final LinkAccessStatsMapper linkAccessStatsMapper;
    private final LinkLocalStatsMapper linkLocalStatsMapper;
    private final LinkAccessLogsMapper linkAccessLogsMapper;
    private final LinkBrowserStatsMapper linkBrowserStatsMapper;
    private final LinkOsStatsMapper linkOsStatsMapper;
    private final LinkDeviceStatsMapper linkDeviceStatsMapper;
    private final LinkNetworkStatsMapper linkNetworkStatsMapper;

    @Override
    public ShortLinkStatsRespDTO oneShortLinkStats(ShortLinkStatsReqDTO shortLinkStatsReqDTO) {

        List<LinkAccessStatsDO> listStatsByShortLink = linkAccessStatsMapper.listStatsByShortLink(shortLinkStatsReqDTO);
        if (CollUtil.isEmpty(listStatsByShortLink)) {
            return null;
        }

        // 基础信息访问详情
        List<ShortLinkStatsAccessDailyRespDTO> daily = new ArrayList<>();
        List<String> rangeDates = DateUtil.rangeToList(DateUtil.parse(shortLinkStatsReqDTO.getStartDate()), DateUtil.parse(shortLinkStatsReqDTO.getEndDate()), DateField.DAY_OF_MONTH)
                .stream()
                .map(DateUtil::formatDate)
                .toList();
        rangeDates.forEach(each ->
                listStatsByShortLink.stream()
                        .filter(item -> Objects.equals(each, DateUtil.formatDate(item.getDate())))
                        .findFirst()
                        .ifPresentOrElse(item -> {
                            ShortLinkStatsAccessDailyRespDTO accessDailyRespDTO = ShortLinkStatsAccessDailyRespDTO.builder()
                                    .date(each)
                                    .pv(item.getPv())
                                    .uv(item.getUv())
                                    .uip(item.getUip())
                                    .build();
                            daily.add(accessDailyRespDTO);
                        }, () -> {
                            ShortLinkStatsAccessDailyRespDTO accessDailyRespDTO = ShortLinkStatsAccessDailyRespDTO.builder()
                                    .date(each)
                                    .pv(0)
                                    .uv(0)
                                    .uip(0)
                                    .build();
                            daily.add(accessDailyRespDTO);
                        }));

        // 地区访问详情（国内）
        List<ShortLinkStatsLocalCNRespDTO> localeCnStats = new ArrayList<>();
        List<LinkLocalStatsDO> linkLocalStatsByShortLink = linkLocalStatsMapper.listLocalByShortLink(shortLinkStatsReqDTO);
        int localSum = linkLocalStatsByShortLink.stream().mapToInt(LinkLocalStatsDO::getCnt).sum();
        linkLocalStatsByShortLink.forEach(each -> {
            double ratio = (double) each.getCnt() / localSum;
            double actualRatio = Math.round(ratio * 100.0) / 100.0;
            ShortLinkStatsLocalCNRespDTO localCNRespDTO = ShortLinkStatsLocalCNRespDTO.builder()
                    .cnt(each.getCnt())
                    .ratio(actualRatio)
                    .local(each.getProvince())
                    .build();
            localeCnStats.add(localCNRespDTO);
        });

        // 小时访问详情
        List<Integer> hourStats = new ArrayList<>();
        List<LinkAccessStatsDO> listHourStatsByShortLink = linkAccessStatsMapper.listHourStatsByShortLink(shortLinkStatsReqDTO);
        for (int i = 0; i < 24; i++) {
            AtomicInteger hour = new AtomicInteger(i);
            int hourCnt = listHourStatsByShortLink.stream()
                    .filter(item -> Objects.equals(hour.get(), item.getHour()))
                    .findFirst()
                    .map(LinkAccessStatsDO::getPv)
                    .orElse(0);
            hourStats.add(hourCnt);
        }

        // 高频访问IP详情
        List<ShortLinkStatsTopIpRespDTO> topIpStats = new ArrayList<>();
        List<HashMap<String, Object>> listTopIpByShortLink = linkAccessLogsMapper.listTopIpByShortLink(shortLinkStatsReqDTO);
        listTopIpByShortLink.forEach(each -> {
            ShortLinkStatsTopIpRespDTO statsTopIpRespDTO = ShortLinkStatsTopIpRespDTO.builder()
                    .ip(each.get("ip").toString())
                    .cnt(Integer.parseInt(each.get("count").toString()))
                    .build();
            topIpStats.add(statsTopIpRespDTO);
        });

        // 一周访问详情
        List<Integer> weekdayStats = new ArrayList<>();
        List<LinkAccessStatsDO> listWeekdayStatsByShortLink = linkAccessStatsMapper.listWeekdayStatsByShortLink(shortLinkStatsReqDTO);
        for (int i = 1; i < 8; i++) {
            AtomicInteger weekday = new AtomicInteger(i);
            int weekdayCnt = listWeekdayStatsByShortLink.stream()
                    .filter(item -> Objects.equals(weekday.get(), item.getWeekday()))
                    .findFirst()
                    .map(LinkAccessStatsDO::getPv)
                    .orElse(0);
            weekdayStats.add(weekdayCnt);
        }

        //浏览器访问详情
        List<ShortLinkStatsBrowserRespDTO> browserStats = new ArrayList<>();
        List<HashMap<String, Object>> listBrowserStatsByShortLink = linkBrowserStatsMapper.listBrowserStatsByShortLink(shortLinkStatsReqDTO);
        int browserSum = listBrowserStatsByShortLink.stream()
                .mapToInt(each -> Integer.parseInt(each.get("count").toString()))
                .sum();
        listBrowserStatsByShortLink.forEach(each -> {
            double ratio = (double) Integer.parseInt(each.get("count").toString()) / browserSum;
            double actualRatio = Math.round(ratio * 100.0) / 100.0;
            ShortLinkStatsBrowserRespDTO browserRespDTO = ShortLinkStatsBrowserRespDTO.builder()
                    .browser(each.get("browser").toString())
                    .ratio(actualRatio)
                    .cnt(Integer.parseInt(each.get("count").toString()))
                    .build();
            browserStats.add(browserRespDTO);
        });

        // 操作系统访问详情
        List<ShortLinkStatsOsRespDTO> osStats = new ArrayList<>();
        List<HashMap<String, Object>> listOsStatsByShortLink = linkOsStatsMapper.listOsStatsByShortLink(shortLinkStatsReqDTO);
        int osCount = listOsStatsByShortLink.stream().mapToInt(each -> Integer.parseInt(each.get("count").toString())).sum();
        listOsStatsByShortLink.forEach(each -> {
            double ratio = (double) Integer.parseInt(each.get("count").toString()) / osCount;
            double actualRatio = Math.round(ratio * 100.0) / 100.0;
            ShortLinkStatsOsRespDTO osStatsRespDTO = ShortLinkStatsOsRespDTO.builder()
                    .os(each.get("os").toString())
                    .ratio(actualRatio)
                    .cnt(Integer.parseInt(each.get("count").toString()))
                    .build();
            osStats.add(osStatsRespDTO);
        });

        // 访客访问类型详情
        List<ShortLinkStatsUvRespDTO> uvTypeStats = new ArrayList<>();
        HashMap<String, Object> findUvTypeByShortLink = linkAccessLogsMapper.findUvTypeCntByShortLink(shortLinkStatsReqDTO);
        int oldUserCnt = Integer.parseInt(
                Optional.ofNullable(findUvTypeByShortLink)
                        .map(each -> each.get("oldUserCnt"))
                        .map(Object::toString)
                        .orElse("0")
        );
        int newUserCnt = Integer.parseInt(
                Optional.ofNullable(findUvTypeByShortLink)
                        .map(each -> each.get("newUserCnt"))
                        .map(Object::toString)
                        .orElse("0")
        );
        int uvSum = oldUserCnt + newUserCnt;
        double oldRatio = (double) oldUserCnt / uvSum;
        double newRatio = (double) newUserCnt / uvSum;
        double actualOldRatio = Math.round(oldRatio * 100.0) / 100.0;
        double actualNewRatio = Math.round(newRatio * 100.0) / 100.0;
        ShortLinkStatsUvRespDTO newUvRespDTO = ShortLinkStatsUvRespDTO.builder()
                .uvType("newUser")
                .cnt(newUserCnt)
                .ratio(actualNewRatio)
                .build();
        uvTypeStats.add(newUvRespDTO);
        ShortLinkStatsUvRespDTO oldUvRespDTO = ShortLinkStatsUvRespDTO.builder()
                .uvType("oldUser")
                .cnt(oldUserCnt)
                .ratio(actualOldRatio)
                .build();
        uvTypeStats.add(oldUvRespDTO);

        // 访问设备详情
        List<ShortLinkStatsDeviceRespDTO> deviceStats = new ArrayList<>();
        List<LinkDeviceStatsDO> listDeviceStatsByShortLink = linkDeviceStatsMapper.listDeviceStatsByShortLink(shortLinkStatsReqDTO);
        int deviceSum = listDeviceStatsByShortLink.stream()
                .mapToInt(LinkDeviceStatsDO::getCnt)
                .sum();
        listDeviceStatsByShortLink.forEach(each -> {
            double ratio = (double) each.getCnt() / deviceSum;
            double actualRatio = Math.round(ratio * 100.0) / 100.0;
            ShortLinkStatsDeviceRespDTO deviceRespDTO = ShortLinkStatsDeviceRespDTO.builder()
                    .cnt(each.getCnt())
                    .device(each.getDevice())
                    .ratio(actualRatio)
                    .build();
            deviceStats.add(deviceRespDTO);
        });

        //访问网络详情
        List<ShortLinkStatsNetworkRespDTO> networkRespDTOS = new ArrayList<>();
        List<LinkNetworkStatsDO> listNetworkStatsByShortLink = linkNetworkStatsMapper.listNetworkStatsByShortLink(shortLinkStatsReqDTO);
        int networkSum = listNetworkStatsByShortLink.stream()
                .mapToInt(LinkNetworkStatsDO::getCnt)
                .sum();
        listNetworkStatsByShortLink.forEach(each -> {
            double ratio = (double) each.getCnt() / networkSum;
            double actualRatio = Math.round(ratio * 100.0) / 100.0;
            ShortLinkStatsNetworkRespDTO networkRespDTO = ShortLinkStatsNetworkRespDTO.builder()
                    .cnt(each.getCnt())
                    .network(each.getNetwork())
                    .ratio(actualRatio)
                    .build();
            networkRespDTOS.add(networkRespDTO);
        });
        return ShortLinkStatsRespDTO.builder()
                .browserStats(browserStats)
                .daily(daily)
                .deviceStats(deviceStats)
                .hourStats(hourStats)
                .localeCnStats(localeCnStats)
                .networkStats(networkRespDTOS)
                .osStats(osStats)
                .topIpStats(topIpStats)
                .weekdayStats(weekdayStats)
                .uvTypeStats(uvTypeStats)
                .build();
    }

    @Override
    public IPage<ShortLinkStatsAccessRecordRespDTO> shortLinkAccessRecordStats(ShortLinkStatsAccessRecordReqDTO shortLinkStatsAccessRecordReqDTO) {
        LambdaQueryWrapper<LinkAccessLogsDO> queryWrapper = Wrappers.lambdaQuery(LinkAccessLogsDO.class)
                .eq(LinkAccessLogsDO::getFullShortUrl, shortLinkStatsAccessRecordReqDTO.getFullShortUrl())
                .eq(LinkAccessLogsDO::getGid, shortLinkStatsAccessRecordReqDTO.getGid())
                .between(LinkAccessLogsDO::getCreateTime,
                        shortLinkStatsAccessRecordReqDTO.getStartDate(),
                        shortLinkStatsAccessRecordReqDTO.getEndDate())
                .eq(LinkAccessLogsDO::getDelFlag, 0)
                .orderByDesc(LinkAccessLogsDO::getCreateTime);
        IPage<LinkAccessLogsDO> linkAccessLogsDOIPage = linkAccessLogsMapper.selectPage(shortLinkStatsAccessRecordReqDTO, queryWrapper);
        IPage<ShortLinkStatsAccessRecordRespDTO> actualResult = linkAccessLogsDOIPage.convert(each -> BeanUtil.toBean(each, ShortLinkStatsAccessRecordRespDTO.class));
        List<String> userAccessRecordList = actualResult.getRecords().stream()
                .map(ShortLinkStatsAccessRecordRespDTO::getUser)
                .toList();
        List<Map<String, Object>> uvTypeList = linkAccessLogsMapper.selectUvTypeByUsers(
                shortLinkStatsAccessRecordReqDTO.getGid(),
                shortLinkStatsAccessRecordReqDTO.getFullShortUrl(),
                shortLinkStatsAccessRecordReqDTO.getStartDate(),
                shortLinkStatsAccessRecordReqDTO.getEndDate()
                , userAccessRecordList);
        actualResult.getRecords().forEach(each -> {
            String uvType = uvTypeList.stream()
                    .filter(item -> Objects.equals(each.getUser(), item.get("user")))
                    .findFirst()
                    .map(item -> (String) item.get("uvType")) // 使用 uvType 字段
                    .map(type -> type.equals("newUser") ? "新访客" : "旧访客")
                    .orElse("旧访客");
            each.setUvType(uvType);
        });
        return actualResult;
    }
}
