package com.heda.video.listener;

import com.heda.video.util.VideoExecuteSQLUtil;
import com.heda.video.util.VideoSQLImporter;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Created by leimiaomiao on 2017/12/26.
 */
// TODO: 2017/12/26 注解作用，ApplicationContextAware接口作用
@Component
public class InitListener implements ApplicationContextAware {
    Logger logger = Logger.getLogger(InitListener.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //初始化数据库
        try{
            String connect = IOUtils.toString(InitListener.class.getResourceAsStream("/db.sql"),"UTF-8");
            VideoExecuteSQLUtil.geInstance();//初始化对象实例  不用new实例了
            VideoSQLImporter.exeucteOneUserSQLFile(InitListener.class,connect,"video_rent","##");
            logger.debug("数据库初始化完成......");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("数据库初始化失败！",e);
        }
    }
}
