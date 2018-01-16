package com.heda.video.util;

import org.apache.log4j.Logger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by leimiaomiao on 2017/12/26.
 */
public class VideoExecuteSQLUtil {
    Logger logger = Logger.getLogger(VideoExecuteSQLUtil.class);
    private static String driver;
    private static String url;
    private static String user;
    private static String pass;

    /**
     * 通过配置文件获取数据库连接信息
     * @author leimiaomiao
     * @throws Exception
     */
    public static void getMysqlInfo(){
        Properties props = new Properties();
        try{
            props.load(VideoExecuteSQLUtil.class.getResourceAsStream("/jdbc.properties"));
            driver = props.getProperty("driver");
            url = props.getProperty("url");
            user = props.getProperty("username");
            pass = props.getProperty("password");
        }catch (IOException e){
            e.printStackTrace();

        }
    }

    /**
     * 初始化
     * 获取sql执行工具
     * 通过配置文件读取数据库url，用户名，密码信息
     *构造函数
     * @return
     */
    // TODO: 2017/12/26 查询getInstance和new实例区别 
    public static VideoExecuteSQLUtil geInstance() {
        getMysqlInfo();
        return new VideoExecuteSQLUtil();
    }


    /**
     * 执行sql
     * 使用ant工具包执行sql文件
     * @param sqlContent
     * @param dbName
     * @param sqlDelimiter
     */
    public void executeUserSQLFile(String sqlContent, String dbName, String sqlDelimiter) {

        //去除数据库的连接地址上的库名，避免第一次连接因为没有库导致失败
        url = url.split("\\?")[0].replaceAll(dbName,"")+"?"+"&allowMultiQueries=true";

        SQLExec sqlExec = new SQLExec();
        sqlExec.setDriver(driver);
        sqlExec.setDelimiter(sqlDelimiter);
        sqlExec.setUrl(this.url);
        sqlExec.setUserid(this.user);
        sqlExec.setPassword(this.pass);
        sqlExec.addText(sqlContent);
        //出错时候的处理
        sqlExec.setOnerror((SQLExec.OnError) EnumeratedAttribute.getInstance(SQLExec.OnError.class, "abort"));
        sqlExec.setPrint(true);
        sqlExec.setProject(new Project());

        try {
            sqlExec.execute();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("执行sql文件失败！",e);

        }
    }
}
