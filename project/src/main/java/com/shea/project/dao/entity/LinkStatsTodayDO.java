package com.shea.project.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shea.project.common.database.BaseDO;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 短链接今日统计实体
 *
 * @author Shea
 * @since 2025-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_link_stats_today")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkStatsTodayDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分组标识
     */
    @TableField("gid")
    private String gid;

    /**
     * 短链接
     */
    @TableField("full_short_url")
    private String fullShortUrl;

    /**
     * 日期
     */
    @TableField("date")
    private Date date;

    /**
     * 今日PV
     */
    @TableField("today_pv")
    private Integer todayPv;

    /**
     * 今日UV
     */
    @TableField("today_uv")
    private Integer todayUv;

    /**
     * 今日IP数
     */
    @TableField("today_uip")
    private Integer todayUip;

}
