package com.heda.video.service;

import com.heda.video.entity.Video;

import java.util.List;
import java.util.Map;

/**
 * Created by leimiaomiao on 2018/1/8.
 */
public interface VideoService {

    List<Video> getVideoList(Map map);

    int addVideo(Map map);

    int deleteVideo(int id);

    int deleteMoreVideo(List<Integer> idList);

    int updateVideo(Map map);

    List<Video> selectVideo(Map map);

    Video getById(String id);

    List<Video> getHotVideoList(String type);

    List<String> getHotTypes();

    List<Video> getNewVideoList();

    List<Video> getFavouriteVideo(String userId);

    int getCurrentCount(String videoId);



}
