package com.shea.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shea.project.dao.entity.ShortLinkDO;
import org.apache.ibatis.annotations.Update;

/**
 * 短链接持久层
 *
 * @author Shea
 * @since 2025-05-11
 */
public interface IShortLinkMapper extends BaseMapper<ShortLinkDO> {


    @Update("update t_link set total_uv = total_uv + #{totalUv}, total_pv = total_pv + #{totalPv}, total_uip = total_uip + #{totalUip} where gid = #{gid} and full_short_url = #{fullShortUrl}")
    void incrementShortLinkStats(String gid, String fullShortUrl, Integer totalUv, Integer totalPv, Integer totalUip);

    //ShortLinkGroupCountQueryDTO listCount(List<String> gids);
}
