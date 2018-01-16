package com.heda.video.service.impl;

import com.heda.video.dao.UserDao;
import com.heda.video.dao.UserRVideoDao;
import com.heda.video.entity.User;
import com.heda.video.service.UserService;
import com.heda.video.util.Response;
import com.heda.video.util.VideoStaticVariables;
import org.eclipse.jetty.server.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by leimiaomiao on 2017/12/27.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRVideoDao userRVideoDao;

    /**
     * 预览用户个人信息
     * @param userId
     * @return
     */
    @Override
    public User getById(int userId) {
        User user = userDao.selectByPrimaryKey(userId);
        return user;
    }

    /**
     * 用户登录，如果验证成功，返回用户身份；如果失败，返回-1
     * username
     * @param map
     * @return
     */
    @Override
    public User login(Map<String, String> map) {
        String idCard = map.get(VideoStaticVariables.ID_CARD);
        String userName = map.get(VideoStaticVariables.USER_NAME);
        String password = map.get(VideoStaticVariables.PASSWORD);
        User user = userDao.login(idCard,userName,password);
        return user;
    }

    /**
     * 修改密码
     * @param map
     * @return
     */
    @Override
    public int changePassword(Map<String, String> map) {
        Integer userId = Integer.parseInt(map.get("userId"));
        String newPassword = map.get("password");
        User user = new User();
        user.setPassword(newPassword);
        user.setUserId(userId);
        int result = userDao.updateByPrimaryKey(user);
        if (result>0){
            return 1;
        }
        return 0;
    }

    @Override
    public int getYearCount(String userId) {
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        int yearCount = userRVideoDao.getYearCount(userId,year);
        return yearCount;
    }

    @Override
    public int getMonthCount(String userId) {
   /*     Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH)+1;
        int year = now.get(Calendar.YEAR);
        int date = now.get(Calendar.DATE);
        //获取当前日期
        String nowDate = year+"-"+month+"-"+date;
        String firstDate = year+"-"+month+"-"+"1";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String iii=dateFormat.format(nowDate);*/
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String temp = now.substring(0,8) ;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(temp);
        stringBuilder.append("01");
        String firstDate = stringBuilder.toString();
        String nowDate = now.substring(0,10);
        Map map = new HashMap();
        map.put("userId",userId);
        map.put("nowDate",nowDate);
        map.put("firstDate",firstDate);
        int monthCount = userDao.getMonthCount(map);
        return monthCount;

    }

    @Override
    public List<User> getUserList(int startNum, int pageSize) {
        List<User> userList = userDao.getUserList(startNum,pageSize);
        return userList;
    }

    @Override
    public int addUser(Map map) {
        User user = new User();
        String userName = map.get("userName").toString();
        String password = map.get("password").toString();
        String idCard = map.get("idCard").toString();
        String phone = map.get("phone").toString();
        String email = map.get("email").toString();
        user.setUserName(userName);
        user.setPassword(password);
        user.setIdCard(idCard);
        user.setEmail(email);
        user.setPhone(phone);
        user.setIsAdmin("0");
        return  userDao.insert(user);
    }

    @Override
    public int editUserMsg(Map map) {
        String userName = map.get("userName").toString();
        String phone = map.get("phone")==null?"":map.get("phone").toString();
        String email = map.get("email")==null?"":map.get("email").toString();
        User user = new User();
        user.setPhone(phone);
        user.setEmail(email);
        user.setUserName(userName);
        return userDao.editUserMsg(user);
    }

    @Override
    public int deleteUser(String id) {
        int userId = Integer.parseInt(id);
        return userDao.deleteByPrimaryKey(userId);
    }

    @Override
    public List<User> searchUser(String condition) {
        List<User> userList = userDao.searchUser(condition);
        return userList;
    }


}
