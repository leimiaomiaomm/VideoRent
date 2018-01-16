package com.heda.video.service;

import com.heda.video.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by leimiaomiao on 2017/12/27.
 */
public interface UserService {
      User getById(int userId);

      User login(Map<String,String> map);

      int changePassword(Map<String,String> map);

      int getYearCount(String userId);

      int getMonthCount(String userId);

      List<User> getUserList(int startNum,int pageSize);

      int addUser(Map map);

      int editUserMsg(Map map);

      int deleteUser(String userId);
}
