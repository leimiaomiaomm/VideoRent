package com.heda.video.controller;

import com.heda.video.dao.VideoDao;
import com.heda.video.entity.UserRVideo;
import com.heda.video.entity.Video;
import com.heda.video.service.UserRVideoService;
import com.heda.video.service.UserService;
import com.heda.video.service.VideoService;
import com.heda.video.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leimiaomiao on 2018/1/5.
 */
@Controller
@RequestMapping("/userRVideo")
public class UserRVideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRVideoService userRVideoService;

    @RequestMapping("/getRentRecord")
    @ResponseBody
    public ModelAndView getRentRecord(String currentPage, String pageSize){
        ModelAndView modelAndView = new ModelAndView();
        Map map = new HashMap() ;
        Map  paramMap = new HashMap() ;
        paramMap = getPageRows(currentPage,pageSize);
        map.put("startNum",paramMap.get("startNum"));
        map.put("pageSize",Integer.parseInt(pageSize));
        map.put("userId",null);
        List<UserRVideo> list = userRVideoService.getRentRecord(map);
        modelAndView.addObject("list",list);
        return modelAndView;

    }

    /**
     * 获取分页的开始位置
     * @param page
     * @param pageSize
     * @return
     */
    public static Map getPageRows(String page, String pageSize){
        Map map = new HashMap();
        int page_int = Integer.parseInt(page);
        int pageSize_int = Integer.parseInt(pageSize);
        int startNum = (page_int - 1) * pageSize_int;
        int endNum = page_int * pageSize_int;
        map.put("startNum",startNum);
        map.put("endNum",endNum);
        return map;
    }

    @RequestMapping("/rentVideo")
    @ResponseBody
    public ModelAndView rentVideo(@RequestParam Map map){
        ModelAndView modelAndView = new ModelAndView();
        int result = userRVideoService.rentVideo(map);
        if (result>0){
            modelAndView.addObject("result",true);
            return modelAndView;
        }else {
            modelAndView.addObject("result",false);
            return modelAndView;
        }
    }
    @RequestMapping("/returnVideo")
    @ResponseBody
    public ModelAndView returnVideo(@RequestParam Map map){
         ModelAndView modelAndView = new ModelAndView();
         int result = userRVideoService.returnVideo(map);
        if (result>0){
            modelAndView.addObject("result",true);
            return modelAndView;
        }else {
            modelAndView.addObject("result",false);
            return modelAndView;
        }

    }



   /* boolean isStock(String videoId){
        int count =videoService.getCurrentCount(videoId);
    }*/

}
