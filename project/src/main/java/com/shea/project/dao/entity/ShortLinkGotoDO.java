package com.shea.project.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 短链接跳转实体
 * @Author: Shea.
 * @Date: 2025/5/13 15:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_link_goto")
public class ShortLinkGotoDO {

    /**
     * id
     */
    private Long id;

    /**
     * 短链接分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;
}
