package com.heda.video.util;

import java.util.ResourceBundle;

/**
 * Created by leimiaomiao on 2017/12/27.
 * 读取被指文件中的内容
 */
public class VideoConfigUtil {
    //读取公共配置文件
    private static final ResourceBundle bundle = ResourceBundle.getBundle("videoConfig");

    /**
     * 根据key读取配置文件中的value
     *
     * @param key key值
     * @return value值
     */
    public static final String get(String key) {
        return bundle.getString(key);
    }
}
