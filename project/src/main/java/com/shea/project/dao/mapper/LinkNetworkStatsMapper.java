package com.shea.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shea.project.dao.entity.LinkNetworkStatsDO;
import org.apache.ibatis.annotations.Insert;

/**
 * 短链接访问网络统计mapper接口
 *
 * @author Shea
 * @since 2025-05-15
 */
public interface LinkNetworkStatsMapper extends BaseMapper<LinkNetworkStatsDO> {

    /**
     * 记录访问设备监控数据
     */
    @Insert("INSERT INTO t_link_network_stats (full_short_url, gid, date, cnt, network, create_time, update_time, del_flag) " +
            "VALUES( #{fullShortUrl}, #{gid}, #{date}, #{cnt}, #{network}, NOW(), NOW(), 0) " +
            "ON DUPLICATE KEY UPDATE cnt = cnt +  #{cnt};")
    void insertShortLinkNetworkState(LinkNetworkStatsDO linkNetworkStatsDO);
}
