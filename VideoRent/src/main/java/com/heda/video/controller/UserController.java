package com.heda.video.controller;

import com.heda.video.entity.User;
import com.heda.video.entity.UserRVideo;
import com.heda.video.listener.JettyHttpServiceLoader;
import com.heda.video.service.UserRVideoService;
import com.heda.video.service.UserService;
import com.heda.video.util.Response;
import com.heda.video.util.VideoStaticVariables;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.heda.video.util.VideoUtil;
import com.heda.video.util.VideoStaticVariables.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.*;

/**
 * Created by leimiaomiao on 2017/12/27.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRVideoService userRVideoService;

    @RequestMapping("/test")
    private void test(int id){
        User user = userService.getById(id);
        System.out.println(user.getUserId());
        System.out.println(user.getUserName());
    }

    /**
     * 管理员登记用户信息--防止身份证号填写不正确。
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/addUser")
    public ModelAndView addUser(Map map){
        ModelAndView modelAndView = new ModelAndView();
        int result = userService.addUser(map);
        if (result>0){
            modelAndView.addObject("result",true);
            return modelAndView;
        } else {
            modelAndView.addObject("result",false);
            return modelAndView;
        }
    }

    /**
     * 管理员更改用户信息
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/editUserMsg")
    public ModelAndView editUserMsg(Map map){
        ModelAndView modelAndView = new ModelAndView();
        int result = userService.editUserMsg(map);
        if (result>0){
            modelAndView.addObject("result",true);
            return modelAndView;
        } else {
            modelAndView.addObject("result",false);
            return modelAndView;
        }
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteUser")
    public ModelAndView deleteUser(String userId){
        ModelAndView modelAndView = new ModelAndView();
        int result = userService.deleteUser(userId);
        if (result>0){
            modelAndView.addObject("result",true);
            return modelAndView;
        } else {
            modelAndView.addObject("result",false);
            return modelAndView;
        }
    }

    /**
     * 搜索用户
     * @param condition
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchUser")
    public ModelAndView searchUser(String condition) {
        ModelAndView modelAndView = new ModelAndView();
        List<User> users = userService.searchUser(condition);
        modelAndView.addObject("users",users);
        return modelAndView;
    }


    /**
     * 用户登录
     * @param map
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    private ModelAndView login(@RequestParam Map<String,String> map){
        ModelAndView modelAndView = new ModelAndView();
        User result = userService.login(map);
        if (result!=null) {
            modelAndView.addObject("result",result);
        }else{
            modelAndView.addObject("result",false);
        }
        return modelAndView;
    }

    /**
     *  修改登录密码
     * @return
     */
    @ResponseBody
    @RequestMapping("/changePassword")
    public ModelAndView changePassword(@RequestParam Map<String,String> map){
        ModelAndView modelAndView = new ModelAndView();
        int result = userService.changePassword(map);
        if (result>0){
            modelAndView.addObject("result",true);
            return modelAndView;
        }else {
            modelAndView.addObject("result",false);
            return modelAndView;
        }
    }

    /**
     * 获取个人信息-用户查看个人基本信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/getPersonalMsg")
    public ModelAndView getPersonalMsg(String id) {
        ModelAndView modelAndView = new ModelAndView();
        int userId = Integer.parseInt(id);
        User user = userService.getById(userId);
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    /**
     * 获取租借年统计量
     * @return
     */
    @ResponseBody
    @RequestMapping("/getYearCount")
    public ModelAndView getYearCount(String id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result",userService.getYearCount(id));
        return modelAndView;
    }

    /**
     * 获取租借月统计量
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMonthCount")
    public ModelAndView getMonthCount(String id){
        ModelAndView modelAndView = new ModelAndView();
        int monthCount = userService.getMonthCount(id);
        modelAndView.addObject("result",monthCount);
        return modelAndView;
    }

    /**
     * 获取当前借阅量
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getNowRentCount")
    public ModelAndView getNowRentCount(String userId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("result",userRVideoService.getNowRentCount(userId));
        return modelAndView;
    }

    /**
     * 获取个人租借记录
     * @return
     */
    @RequestMapping("/getPersonalRentRecord")
    @ResponseBody
   public ModelAndView getPersonalRentRecord(Map map){
        ModelAndView modelAndView = new ModelAndView();
        Map map1 = new HashMap() ;
        Map  paramMap = new HashMap() ;
        paramMap = getPageRows(map.get("currentPage").toString(),map.get("pageSize").toString());
        map1.put("startNum",paramMap.get("startNum"));
        map1.put("pageSize",Integer.parseInt(map.get("pageSize").toString()));
        map1.put("userId",map.get("userId").toString());
        List<UserRVideo> userRVideoList =  userRVideoService.getRentRecord(map);
        modelAndView.addObject("userRVideoList",userRVideoList);
        return  modelAndView;
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
     * 分页获取用户列表 ,管理员管理用户用
     * @param currentPage
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserList")
  public ModelAndView getUserList(String currentPage,String pageSize){
     ModelAndView modelAndView = new ModelAndView() ;
     Map paramMap = new HashMap();
      paramMap = getPageRows(currentPage,pageSize);
      List<User> users = userService.getUserList((Integer) paramMap.get("startNum"),Integer.parseInt(pageSize));
      modelAndView.addObject("users",users);
      return modelAndView;
  }

    /**
     * 获取ftp连接信息
     * @return
     */
  /*public Map getFtpMsg(){
      Properties properties = new Properties();
      Map map = new HashMap();
      try {
          InputStream inputStream = UserController.class.getResourceAsStream("/ftp.properties");
          if(inputStream == null) {
              return map;
          }

          properties.load(inputStream);
          Iterator keys = properties.keySet().iterator();

          while(keys.hasNext()) {
              String key = keys.next().toString();
              String value = properties.getProperty(key);
              map.put(key.trim(), value.trim());
          }
      } catch (Exception var7) {
          ;
      }

      return map;
      }*/
    /**
     * Description: 向FTP服务器上传文件
     * @param "host FTP服务器hostname
     * @param "port FTP服务器端口
     * @param "username FTP登录账号
     * @param "password FTP登录密码
     * @param "basePath FTP服务器基础目录
     * @param "filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     * @param "filename 上传到FTP服务器上的文件名
     * @param "input 输入流
     * @return 成功返回true，否则返回false
     */
   /*  public ModelAndView uploadVideo(String fileName,InputStream inputStream){
         Map map = new HashMap();
         ModelAndView modelAndView = new ModelAndView();
         boolean result = false;
         map = getFtpMsg();
         FTPClient ftp = new FTPClient();
         try {
             int reply;
             ftp.connect(map.get("FTP_ADDRESS").toString(), Integer.parseInt(map.get("FTP_PORT").toString()));// 连接FTP服务器
             // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
             ftp.login(map.get("FTP_USERNAME").toString(), map.get("FTP_PASSWORD").toString());// 登录
             reply = ftp.getReplyCode();
             if (!FTPReply.isPositiveCompletion(reply)) {
                 ftp.disconnect();
                 modelAndView.addObject("result",result);
                 return modelAndView;
             }
             //切换到上传目录
             if (!ftp.changeWorkingDirectory(map.get("FTP_BASEPATH").toString()+map.get("IMAGE_BASE_URL"))) {
                 //如果目录不存在创建目录
                 String[] dirs = map.get("IMAGE_BASE_URL").toString().split("/");
                 String tempPath = map.get("FTP_BASEPATH").toString();
                 for (String dir : dirs) {
                     if (null == dir || "".equals(dir))
                         continue;//跳出本次循环
                     tempPath += "/" + dir;
                     if (!ftp.changeWorkingDirectory(tempPath)) {
                         if (!ftp.makeDirectory(tempPath)) {
                             modelAndView.addObject("result",result);
                             return modelAndView;
                         } else {
                             ftp.changeWorkingDirectory(tempPath);
                         }
                     }
                 }
             }
             //设置上传文件的类型为二进制类型
             ftp.setFileType(FTP.BINARY_FILE_TYPE);
             //上传文件
             if (!ftp.storeFile(fileName, inputStream)) {
                 modelAndView.addObject("result",result);
                 return modelAndView;
             }
             inputStream.close();
             ftp.logout();
             result = true;
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
             if (ftp.isConnected()) {
                 try {
                     ftp.disconnect();
                 } catch (IOException ioe) {
                 }
             }
         }
         modelAndView.addObject("result",result);
         return modelAndView;
     }*/


   public static void main(String[] args) {
        JettyHttpServiceLoader.main(new String[]{});
    }

}
