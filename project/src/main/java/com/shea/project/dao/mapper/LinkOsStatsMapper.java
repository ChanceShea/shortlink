package com.shea.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shea.project.dao.entity.LinkOsStatsDO;
import com.shea.project.dto.req.ShortLinkStatsReqDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

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

    /**
     * 根据短链接获取指定日期内操作系统监控数据
     */
    @Select("SELECT " +
            "    os, " +
            "    SUM(cnt) AS count " +
            "FROM " +
            "    t_link_os_stats " +
            "WHERE " +
            "    full_short_url = #{fullShortUrl} " +
            "    AND gid = #{gid} " +
            "    AND date BETWEEN #{startDate} and #{endDate} " +
            "GROUP BY " +
            "    full_short_url, gid, os;")
    List<HashMap<String, Object>> listOsStatsByShortLink(ShortLinkStatsReqDTO requestParam);
}
