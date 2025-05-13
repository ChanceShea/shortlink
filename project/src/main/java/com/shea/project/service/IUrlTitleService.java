package com.shea.project.service;

/**
 * @description: 网站标题业务层接口
 * @Author: Shea.
 * @Date: 2025/5/13 17:48
 */
public interface IUrlTitleService {

    /**
     * 根据url获取标题
     *
     * @param url 目标网址
     * @return 目标网址标题
     */
    String getUrlTitle(String url);
}
