package com.heda.video.dao;

import com.heda.video.entity.UserRVideo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserRVideoDao {
    int deleteByPrimaryKey(Integer userRVideoId);

    int insert(UserRVideo record);

    int insertSelective(UserRVideo record);

    UserRVideo selectByPrimaryKey(Integer userRVideoId);

    int updateByPrimaryKeySelective(UserRVideo record);

    int updateByPrimaryKey(UserRVideo record);

    List<UserRVideo> getRentRecord(Map map);

    int updateById(UserRVideo userRVideo);

    int getNowRentCount(@Param("userId") String userId);

    int getYearCount(@Param("userId") String userId,@Param("year") String year);
}