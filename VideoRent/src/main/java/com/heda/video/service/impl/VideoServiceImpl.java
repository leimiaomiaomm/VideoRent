package com.heda.video.service.impl;

import com.heda.video.dao.VideoDao;
import com.heda.video.entity.Video;
import com.heda.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by leimiaomiao on 2018/1/8.
 */
@Service("videoService")
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;

    /**
     * 获取影碟列表--分页
     * @param map
     * @return
     */
    @Override
    public List<Video> getVideoList(Map map) {
        int startNum = Integer.parseInt(map.get("startNum").toString());
        int pageSize = Integer.parseInt(map.get("pageSize").toString());
        return videoDao.getVideoList(startNum,pageSize,map.get("condition").toString());
    }

    /**
     * 添加影碟
     * @param map
     * @return
     */
    @Override
    public int addVideo(Map map) {
        Video video = new Video();
        video.setType(map.get("type").toString());
        video.setActor(map.get("actor").toString());
        //数量需要前端设置成数字选择形式，不允许输入,不允许为空
        video.setCount(Integer.parseInt(map.get("count").toString()));
        //默认为进货张数
        // video.setCurrentCount(map.get("currentCount")==null?Integer.parseInt(map.get("count").toString()):Integer.parseInt(map.get("currentCount").toString()));
        video.setCurrentCount(video.getCount());
        video.setDesc(map.get("desc")==null?"":map.get("desc").toString());
        video.setDirector(map.get("director")==null?"":map.get("director").toString());
        video.setPosition(map.get("position")==null?"":map.get("position").toString());
        video.setPrice(map.get("price").toString());
        //租借次数不允许用户填，默认未借出
//        video.setRentTimes(map.get("rentTimes")==null?"0":map.get("rentTimes").toString());
        video.setRentTimes(map.get("rentTimes").toString());
        //默认未借出状态
        video.setStatus("0");
        video.setVideoName(map.get("videoName").toString());
        video.setVideoNo(map.get("videoNo").toString());
        video.setYear(map.get("year")==null?"":map.get("year").toString());
        return videoDao.insert(video);
    }

    @Override
    public int deleteVideo(int id) {
        return videoDao.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteMoreVideo(List<Integer> idList) {
        return  videoDao.deleteMoreVideo(idList);
    }

    @Override
    public int updateVideo(Map map) {
        int videoId = Integer.parseInt(map.get("id").toString());
        Video video = new Video();
        video.setVideoId(videoId);
        video.setType(map.get("type").toString());
        video.setActor(map.get("actor").toString());
        //数量需要前端设置成数字选择形式，不允许输入,不允许为空
        video.setCount(Integer.parseInt(map.get("count").toString()));
        Video tempVideo = videoDao.selectByPrimaryKey(videoId);
        //保证当前库存和修改的总数量的改变一致----自动计算库存
        video.setCurrentCount(video.getCount()-(tempVideo.getCount()-tempVideo.getCurrentCount()));
        video.setDesc(map.get("desc")==null?"":map.get("desc").toString());
        video.setDirector(map.get("director")==null?"":map.get("director").toString());
        video.setPosition(map.get("position")==null?"":map.get("position").toString());
        video.setPrice(map.get("price").toString());
        //不可更新租借次数，因为租借需要有记录
       /* video.setRentTimes("0");*/
        /*video.setStatus("0");*/
        video.setVideoName(map.get("videoName").toString());
        /*video.setVideoNo(map.get("videoNo").toString());*/
        video.setYear(map.get("year")==null?"":map.get("year").toString());
        int result = videoDao.updateByPrimaryKey(video);
        return result;
    }

    @Override
    public List<Video> selectVideo(Map map) {
        String condition = map.get("condition")==null?null:map.get("condition").toString();
            //分页获取  ---未实现
        return videoDao.selectVideo(condition);
    }

    @Override
    public Video getById(String id) {
        Video video = videoDao.selectByPrimaryKey(Integer.parseInt(id));
        return video;
    }

    @Override
    public List<Video> getHotVideoList(String type) {
        List<Video> videoList = videoDao.getHotVideoList(type);
        return videoList;
    }

    @Override
    public List<String> getHotTypes() {
        List<String> hotTypes = videoDao.getHotTypes();
        return hotTypes;
    }

    @Override
    public List<Video> getNewVideoList() {
        List<Video> videoList = videoDao.getNewVideoList();
        return videoList;
    }

    @Override
    public List<Video> getFavouriteVideo(String userId) {
        int id = Integer.parseInt(userId);
        String type = videoDao.getTypeByUserId(id);
        List<Video> videoList = videoDao.getHotVideoByType(type);
        return videoList;
    }

    @Override
    public int getCurrentCount(String videoId) {

        return videoDao.getCurrentCount(videoId);
    }



   /* public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }*/

}
