package com.heda.video.controller;

import com.heda.video.entity.Video;
import com.heda.video.listener.JettyHttpServiceLoader;
import com.heda.video.service.VideoService;
import com.heda.video.util.Response;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by leimiaomiao on 2018/1/5.
 */
@Controller
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 获取影碟列表---实现分页
     * @return
     */
        @RequestMapping("/getVideoList")
       public ModelAndView getVideoList(@RequestParam String currentPage,@RequestParam String pageSize){
           ModelAndView modelAndView = new ModelAndView();
           Map map = new HashMap() ;
           Map  paramMap = new HashMap() ;
           paramMap = getPageRows(currentPage,pageSize);
           map.put("startNum",paramMap.get("startNum"));
           map.put("pageSize",pageSize);
           List<Video> videoList = videoService.getVideoList(map);
           modelAndView.addObject("videoList",videoList);
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

    /**
     * 影碟入库
     * @param map
     * @return
     */
    @RequestMapping("/addVideo")
       public ModelAndView addVideo(@RequestParam Map map){
           ModelAndView modelAndView = new ModelAndView();
           int result = videoService.addVideo(map);
           if (result>0){
               modelAndView.addObject("result",true);
           }else {
               modelAndView.addObject("result",false);
           }
           return modelAndView;
       }

    /**
     * 删除影碟
     * @param id
     * @return
     */
       @ResponseBody
       @RequestMapping("/deleteVideo")
       public ModelAndView deleteVideo(int id){
           ModelAndView modelAndView = new ModelAndView();
           int result = videoService.deleteVideo(id);
           if (result>0){
               modelAndView.addObject("result",true);
           }else {
               modelAndView.addObject("result",false);
           }
           return modelAndView;
       }

    /**
     * 批量删除
     * @param idList
     * @return
     */
       @CrossOrigin
       @ResponseBody
       @RequestMapping(value = "/deleteMoreVideo",method = RequestMethod.POST,produces = "application/json")
       public ModelAndView deleteMoreVideo(@RequestBody List<Integer> idList){
           ModelAndView modelAndView = new ModelAndView();
           int result = videoService.deleteMoreVideo(idList);
           if (result>0){
               modelAndView.addObject("result",true);
           }else {
               modelAndView.addObject("result",false);
           }
           return modelAndView;
       }

    /**
     * 更改影碟信息
     * @param map
     * @return
     */
       @ResponseBody
       @RequestMapping("/editVideoMsg")
       public ModelAndView editVideoMsg(@RequestParam Map map){
           ModelAndView modelAndView = new ModelAndView();
           int result = videoService.updateVideo(map);
           if (result>0){
               modelAndView.addObject("result",true);
           }else {
               modelAndView.addObject("result",false);
           }
           return modelAndView;
       }



    /**
     * 根据编号、演员、名称搜索影碟
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchVideo")
   public ModelAndView searchVideo(@RequestParam Map map){
        ModelAndView modelAndView = new ModelAndView();
        Map paramMap = new HashMap();
        paramMap = getPageRows(map.get("currentPage").toString(),map.get("pageSize").toString());
        paramMap.put("pageSize",map.get("pageSize"));
        paramMap.put("condition",map.get("condition")) ;
        List<Video> list = videoService.selectVideo(paramMap);
        modelAndView.addObject("list",list);
        return modelAndView;
   }

    /**
     *获取影碟通过id--预览
     * @return
     */
    @ResponseBody
    @RequestMapping("/getById")
   public ModelAndView getById(String id){
       ModelAndView modelAndView = new ModelAndView();
       modelAndView.addObject("video",videoService.getById(id));
       return modelAndView;
   }

    /**
     * 获取最热的影碟,前5排名
     * @return
     */
    @ResponseBody
    @RequestMapping("/getVideoHot")
   public ModelAndView getVideoHot(){
       ModelAndView modelAndView = new ModelAndView();
        Map map = new HashMap();
        List<Map<String,List>> list = new ArrayList<>();
        List<String> hotType = videoService.getHotTypes();
        for (String s : hotType) {
            List<Video> videoList = videoService.getHotVideoList(s);
            map.put("type",s);
            map.put("videos",videoList);
            list.add(map);
        }
       modelAndView.addObject("HotVideos", list);
       return modelAndView;
   }

    /**
     * 获取最新的影碟  前5
     * @return
     */
    @ResponseBody
    @RequestMapping("/getVideoNew")
   public ModelAndView getVideoNew(){
       ModelAndView  modelAndView = new ModelAndView();
       List<Video> newVideos = videoService.getNewVideoList();
       modelAndView.addObject("newVideos",newVideos);
       return modelAndView;

   }

    /**
     * 根据用户喜好推荐
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchByPersonHobby")
   public ModelAndView searchByPersonHobby(String userId){
       ModelAndView modelAndView = new ModelAndView();
       List<Video> favouriteVideo = videoService.getFavouriteVideo(userId);
       modelAndView.addObject("favouriteVideo",favouriteVideo);
       return modelAndView;
   }


    /**
     * 上传文件
     * @param file--前端文件名
     * @param req
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value="/upload",method=RequestMethod.POST)
    public String add(@RequestParam("file")MultipartFile file,HttpServletRequest req) throws IOException {//一定要紧跟Validate之后写验证结果类
        //获取upload文件夹得真实路径
        String realpath = req.getSession().getServletContext().getRealPath("/resources/upload");//获取文件上传的路径（项目目录+项目目录下其他指定目录）
        System.out.println(realpath);
        System.out.println(file.getName());//获取attach的属性名称，也就是前台表单的名称
        System.out.println(file.getOriginalFilename());//获取上传文件的名称
        System.out.println(file.getContentType());//获取上传文件的类型
        File f = new File(realpath+"/"+file.getOriginalFilename());
        //Apache的上传文件的工具类
        FileUtils.copyInputStreamToFile(file.getInputStream(),f);
        return "redirect:/index.html";
    }

    public static void main(String[] args) {
        JettyHttpServiceLoader.main(new String[]{});
    }

}
