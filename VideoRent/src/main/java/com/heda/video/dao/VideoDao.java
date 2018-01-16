package com.heda.video.dao;

import com.heda.video.entity.Video;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

public interface VideoDao {
    int deleteByPrimaryKey(Integer videoId);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Integer videoId);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    List<Video> getVideoList(@Param("startNum") int startNum,@Param("pageSize") int pageSize,@Param("condition")String condition);

    List<Video> selectVideo(@Param("condition") String condition);


    int deleteMoreVideo(List<Integer> list);

    List<Video> getHotVideoList(String type);

    List<String> getHotTypes();

    List<Video> getNewVideoList();

    String getTypeByUserId(int id);

    List<Video> getHotVideoByType(String type);

    int getCurrentCount(String videoId);

    int updateVideoById(String videoId);

    int updateByVideoNum(String videoNum);

}