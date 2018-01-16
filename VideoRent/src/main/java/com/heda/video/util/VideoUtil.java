package com.heda.video.util;

import com.heda.video.entity.Video;
import org.apache.log4j.Logger;

import java.util.Map;

import static com.heda.video.util.VideoStaticVariables.CMD_OP;


/**
 * Created by leimiaomiao on 2017/12/27.
 */
public class VideoUtil {
    private static Logger logger = Logger.getLogger(Video.class);
    private VideoUtil() {}


    /**
     * 检查map中参数存在或是否为空
     * @param map
     * @param paraName
     * @return
     */
    public static boolean checkParam(Map map, String... paraName) {
        for (String s : paraName) {
            if (!map.containsKey(s) || "".equals(map.get(s)) || null == map.get(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 拦截请求参数，判断合法性
     * @param map
     * @param response
     * @param key
     * @return
     */
    public static boolean interceptRequests(Map map, Response response, String... key) {
        for (String s : key) {
            if (!map.containsKey(s) || "".equals(map.get(s)) || null == map.get(s)) {
                response.setCode(Response.PARAMETER);
                return false;
            }
            //设置返回type
            if (CMD_OP.equals(s)) {
                String cmd_op = String.valueOf(map.get(CMD_OP));
                response.setType(cmd_op);
            }
        }
        return true;
    }

}
