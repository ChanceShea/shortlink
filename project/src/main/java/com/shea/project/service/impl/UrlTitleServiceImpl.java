package com.shea.project.service.impl;

import com.shea.project.service.IUrlTitleService;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @description: 网站标题业务实现类
 * @Author: Shea.
 * @Date: 2025/5/13 17:48
 */
@Service
public class UrlTitleServiceImpl implements IUrlTitleService {
    @Override
    @SneakyThrows
    public String getUrlTitle(String url) {
        URL targetUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Document document = Jsoup.connect(url).get();
            return document.title();
        }
        return "Error while fetching title.";
    }
}
