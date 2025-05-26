package com.onenet.common;

/**
 * AppConfig
 *
 * @author HaiYinLong
 * @version 2025/05/26 16:08
 **/
public class AppConfig {
    private String baseUrl;

    public AppConfig(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
