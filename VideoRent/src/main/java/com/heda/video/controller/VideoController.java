package com.heda.video.controller;

import com.heda.video.entity.Video;
import com.heda.video.listener.JettyHttpServiceLoader;
import com.heda.video.service.VideoService;
import com.heda.video.util.Response;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
       List<Video> videoList = videoService.getHotVideoList();
       modelAndView.addObject("HotVideos", videoList);
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


   @ResponseBody
   @RequestMapping("/testUpload")
   public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
       request.setCharacterEncoding("utf-8");
       response.setCharacterEncoding("utf-8");
       //1、创建一个DiskFileItemFactory工厂
       DiskFileItemFactory factory = new DiskFileItemFactory();
       //2、创建一个文件上传解析器
       ServletFileUpload upload = new ServletFileUpload(factory);
       //解决上传文件名的中文乱码
       upload.setHeaderEncoding("UTF-8");
       factory.setSizeThreshold(1024 * 500);//设置内存的临界值为500K
       File linshi = new File("E:\\Users\\leimiaomiao\\Desktop\\img");//当超过500K的时候，存到一个临时文件夹中
       factory.setRepository(linshi);
       upload.setSizeMax(1024 * 1024 * 5);//设置上传的文件总的大小不能超过5M
       try {
           // 1. 得到 FileItem 的集合 items
           List<FileItem> /* FileItem */items = upload.parseRequest(request);

           // 2. 遍历 items:
           for (FileItem item : items) {
               // 若是一个一般的表单域, 打印信息
               if (item.isFormField()) {
                   String name = item.getFieldName();
                   String value = item.getString("utf-8");

                   System.out.println(name + ": " + value);


               }
               // 若是文件域则把文件保存到 e:\\files 目录下.
               else {
                   String fileName = item.getName();
                   long sizeInBytes = item.getSize();
                   System.out.println(fileName);
                   System.out.println(sizeInBytes);

                   InputStream in = item.getInputStream();
                   byte[] buffer = new byte[1024];
                   int len = 0;

                   fileName = "f:\\files\\" + fileName;//文件最终上传的位置
                   System.out.println(fileName);
                   OutputStream out = new FileOutputStream(fileName);

                   while ((len = in.read(buffer)) != -1) {
                       out.write(buffer, 0, len);
                   }

                   out.close();
                   in.close();
               }
           }

       } catch (FileUploadException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
       ModelAndView modelAndView = new ModelAndView("/index.jsp");
       modelAndView.addObject("result",true);
       return modelAndView;

}

    public static void main(String[] args) {
        JettyHttpServiceLoader.main(new String[]{});
    }

}
