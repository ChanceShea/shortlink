package com.shea.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shea.project.dao.entity.LinkAccessStatsDO;
import org.apache.ibatis.annotations.Insert;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Shea
 * @since 2025-05-14
 */
public interface LinkAccessStatsMapper extends BaseMapper<LinkAccessStatsDO> {

    @Insert("INSERT INTO t_link_access_stats (full_short_url, gid, date, pv, uv, uip, HOUR, weekday, create_time, update_time, del_flag)" +
            "VALUES" +
            "(#{fullShortUrl}, #{gid}, #{date}, #{pv}, #{uv}, #{uip}, #{hour}, #{weekday}, NOW(), NOW(), 0 )" +
            "ON DUPLICATE KEY UPDATE pv = pv + #{pv}," +
            "uv = uv + #{uv}," +
            "uip = uip + #{uip};")
    void insertShortLinkStats(LinkAccessStatsDO linkAccessStatsDO);
}
