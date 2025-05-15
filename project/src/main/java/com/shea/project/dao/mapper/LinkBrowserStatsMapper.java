package com.shea.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shea.project.dao.entity.LinkBrowserStatsDO;
import com.shea.project.dto.req.ShortLinkStatsReqDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * 短链接浏览器统计表 Mapper 接口
 *
 * @author Shea
 * @since 2025-05-14
 */
public interface LinkBrowserStatsMapper extends BaseMapper<LinkBrowserStatsDO> {

    /**
     * 记录浏览器访问监控数据
     */
    @Insert("INSERT INTO t_link_browser_stats (full_short_url, gid, date, cnt, browser, create_time, update_time, del_flag) " +
            "VALUES( #{fullShortUrl}, #{gid}, #{date}, #{cnt}, #{browser}, NOW(), NOW(), 0) " +
            "ON DUPLICATE KEY UPDATE cnt = cnt + #{cnt};")
    void insertShortLinkBrowserState(LinkBrowserStatsDO linkBrowserStatsDO);

    /**
     * 根据短链接获取指定日期内浏览器监控数据
     */
    @Select("SELECT " +
            "    browser, " +
            "    SUM(cnt) AS count " +
            "FROM " +
            "    t_link_browser_stats " +
            "WHERE " +
            "    full_short_url = #{fullShortUrl} " +
            "    AND gid = #{gid} " +
            "    AND date BETWEEN #{startDate} and #{endDate} " +
            "GROUP BY " +
            "    full_short_url, gid, browser;")
    List<HashMap<String, Object>> listBrowserStatsByShortLink(ShortLinkStatsReqDTO requestParam);
}
