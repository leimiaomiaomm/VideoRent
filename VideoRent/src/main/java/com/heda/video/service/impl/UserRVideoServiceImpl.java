package com.heda.video.service.impl;

import com.heda.video.dao.UserRVideoDao;
import com.heda.video.dao.VideoDao;
import com.heda.video.entity.UserRVideo;
import com.heda.video.entity.Video;
import com.heda.video.service.UserRVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by leimiaomiao on 2018/1/9.
 */
@Service("userRVideoService")
public class UserRVideoServiceImpl implements UserRVideoService {

    @Autowired
    private UserRVideoDao userRVideoDao;

    @Autowired
    private VideoDao videoDao;


    @Override
    public List<UserRVideo> getRentRecord(Map map) {
        return userRVideoDao.getRentRecord(map);
    }

    @Override
    public int rentVideo(Map map) {
        UserRVideo userRVideo = new UserRVideo();
        userRVideo.setVideoId(map.get("videoId").toString());
        userRVideo.setUserId(map.get("userId").toString());
        userRVideo.setDeposit(map.get("deposit").toString());
        userRVideo.setInvoiceNumber(map.get("invoiceNumber").toString());
        userRVideo.setRent(map.get("rent").toString());
        String currentDate =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString();
        userRVideo.setRentDate(map.get("rentDate")==null?currentDate:map.get("rentDate").toString());
        //前端限制默认为借阅时间的下一个月
        userRVideo.setReturnDate(map.get("returnDate").toString());
        int result =  userRVideoDao.insert(userRVideo);
        if (result>0){
            videoDao.updateVideoById(userRVideo.getVideoId()) ;
        }
        return result;
    }

    @Override
    public int returnVideo(Map map) {
        UserRVideo userRVideo = new UserRVideo();
        userRVideo.setUserRVideoId(Integer.parseInt(map.get("recordId").toString()));
        userRVideo.setDeposit("0");
        userRVideo.setActualReturnDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString());
        int result = userRVideoDao.updateById(userRVideo);
        if (result>0){
            videoDao.updateByVideoNum(map.get("videoNum").toString());
        }
        return result;
    }

    @Override
    public int getNowRentCount(String userId) {
       /* String now = new SimpleDateFormat("YYYY-MM-NN hh:mm:ss").format(new Date());
        String nowDate = now.substring(0,10);*/
        int nowRentCount = userRVideoDao.getNowRentCount(userId);
        return nowRentCount;
    }
}
