package com.heda.video.service;

import com.heda.video.entity.UserRVideo;

import java.util.List;
import java.util.Map;

/**
 * Created by leimiaomiao on 2018/1/9.
 */
public interface UserRVideoService {

    List<UserRVideo> getRentRecord (Map map);

    int rentVideo(Map map);

    int returnVideo(Map map);

    int getNowRentCount(String userId);

}
