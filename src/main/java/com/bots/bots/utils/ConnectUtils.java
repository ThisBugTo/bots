package com.bots.bots.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * 连接实用程序
 *
 * @author ZhangJunHu
 * {@code @date} 2024/08/01
 */
@Slf4j
public class ConnectUtils {
    /**
     * url地址
     */
    private final static String urlAddress = "https://www.telegram.org";

    /**
     * 连接
     *
     * @return {@link Boolean }
     */
    public static Boolean connect() {
        log.info("开始检测连接：{}", urlAddress);
        try {
            URL url = new URL(urlAddress);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(10000);
            int responseCode = httpURLConnection.getResponseCode();
            log.info("检测连接返回数据:{}", responseCode);
        } catch (SocketTimeoutException socketTimeoutException) {
            log.error("连接超时：{}", socketTimeoutException.getMessage());
            return false;
        } catch (Exception e) {
            log.error("检测失败：{}", e.getMessage());
            return false;
        }
        return true;
    }
}
