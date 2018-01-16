package com.heda.video.dao;

import com.heda.video.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDao {

    User login(@Param("idCard") String idCard, @Param("userName") String userName, @Param("password") String password);

    int updateByPrimaryKey(User record);

    int getMonthCount(Map map);

    List<User> getUserList(@Param("startNum") int startNum,@Param("pageSize") int pageSize);


    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int editUserMsg(User record);

    List<User> searchUser(String condition);

}