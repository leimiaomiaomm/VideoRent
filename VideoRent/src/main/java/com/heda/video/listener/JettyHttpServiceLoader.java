package com.heda.video.listener;

import com.heda.video.util.VideoConfigUtil;
import org.apache.log4j.Logger;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * Created by leimiaomiao on 2017/12/27.
 */
public class JettyHttpServiceLoader {
    private static Logger logger = Logger.getLogger(JettyHttpServiceLoader.class);
    public JettyHttpServiceLoader() {
    }
    public void start(){
        String port = VideoConfigUtil.get("videoPort");
        String path = "src/main/webapp";
        Server server = new Server(Integer.parseInt(port));
        MBeanContainer mbContainer = new MBeanContainer(
                ManagementFactory.getPlatformMBeanServer());
        server.addBean(mbContainer);
        WebAppContext webapp = new WebAppContext(path,"/");
        try {
            webapp.setBaseResource(Resource.newResource(path));
        } catch (IOException e) {
            logger.error("初始化jetty获取原始资源文件失败！"+e);
        }
        webapp.setDescriptor(path+"/web.xml");
        server.setHandler(webapp);
        try {
            server.start();
            server.dumpStdErr();
            server.join();
        } catch (InterruptedException e) {
            logger.error("jetty的join方法执行失败！"+e);
        }catch (Exception e){
            logger.error("jetty的start方法执行失败！"+e);
        }
    }
    public static void main( String[] args ){
        new JettyHttpServiceLoader().start();
    }
}
