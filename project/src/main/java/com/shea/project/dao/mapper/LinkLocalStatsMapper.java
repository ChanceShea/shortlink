package com.shea.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shea.project.dao.entity.LinkLocalStatsDO;
import com.shea.project.dto.req.ShortLinkStatsReqDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Shea
 * @since 2025-05-14
 */
public interface LinkLocalStatsMapper extends BaseMapper<LinkLocalStatsDO> {

    @Insert("INSERT INTO t_link_local_stats (full_short_url, gid, date, cnt,country,province,city,adcode,create_time, update_time, del_flag)" +
            "VALUES (#{fullShortUrl}, #{gid}, #{date}, #{cnt}, #{country},#{province},#{city},#{adcode}, NOW(), NOW(), 0)" +
            "ON DUPLICATE KEY UPDATE cnt = cnt + #{cnt}")
    void insertShortLinkLocalStats(LinkLocalStatsDO linkLocalStats);

    /**
     * 根据短链接获取指定日期内基础监控数据
     */
    @Select("SELECT " +
            "    province, " +
            "    SUM(cnt) AS cnt " +
            "FROM " +
            "    t_link_local_stats " +
            "WHERE " +
            "    full_short_url = #{fullShortUrl} " +
            "    AND gid = #{gid} " +
            "    AND date BETWEEN #{startDate} and #{endDate} " +
            "GROUP BY " +
            "    full_short_url, gid, province;")
    List<LinkLocalStatsDO> listLocalByShortLink(ShortLinkStatsReqDTO requestParam);
}
