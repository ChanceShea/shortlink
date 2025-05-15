package com.shea.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shea.project.dao.entity.LinkDeviceStatsDO;
import org.apache.ibatis.annotations.Insert;

/**
 * 短链设备统计 Mapper 接口
 *
 * @author Shea
 * @since 2025-05-15
 */
public interface LinkDeviceStatsMapper extends BaseMapper<LinkDeviceStatsDO> {

    /**
     * 记录访问设备监控数据
     */
    @Insert("INSERT INTO t_link_device_stats (full_short_url, gid, date, cnt, device, create_time, update_time, del_flag) " +
            "VALUES( #{fullShortUrl}, #{gid}, #{date}, #{cnt}, #{device}, NOW(), NOW(), 0) " +
            "ON DUPLICATE KEY UPDATE cnt = cnt +  #{cnt};")
    void insertShortLinkDeviceStats(LinkDeviceStatsDO linkDeviceStatsDO);

}
