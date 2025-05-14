package com.shea.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shea.project.dao.entity.LinkOsStatsDO;
import org.apache.ibatis.annotations.Insert;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Shea
 * @since 2025-05-14
 */
public interface LinkOsStatsMapper extends BaseMapper<LinkOsStatsDO> {

    @Insert("INSERT INTO t_link_os_stats (full_short_url, gid, date, cnt, os, create_time, update_time, del_flag)" +
            "VALUES (#{fullShortUrl}, #{gid}, #{date}, #{cnt}, #{os}, NOW(), NOW(), 0)" +
            "ON DUPLICATE KEY UPDATE cnt = cnt + #{cnt}")
    void insertShortLinkOsStats(LinkOsStatsDO linkOsStats);
}
