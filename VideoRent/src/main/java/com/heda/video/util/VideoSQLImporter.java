package com.heda.video.util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by leimiaomiao on 2017/12/26.
 */
public class VideoSQLImporter extends VideoExecuteSQLUtil {
    private Class _class;

    public VideoSQLImporter(Class _class) {
        this._class = _class;
    }


    /**
     * 执行建库sql文件
     *
     * @param sqlContent
     * @param dbName
     * @param sqlDelimiter
     */
    public void importFile( String sqlContent, String dbName, String sqlDelimiter) {
        String importClassName = this._class.getSimpleName();
        LinkedHashMap importClassNameMap = this.get();
        //判断db文件是否为空，空则不执行建库sql
        if(importClassNameMap.isEmpty() || !importClassNameMap.containsKey(importClassName)) {
            super.executeUserSQLFile(sqlContent, dbName, sqlDelimiter);
            importClassNameMap.put(importClassName, "true");
            this.save(importClassNameMap);
        }else{
            logger.debug("非首次启动，数据库建库略过......");
        }
    }

    /**
     * 获取配置文件中的信息z
     * 校验是否进行过数据库初始化
     *
     * @return
     */
    private LinkedHashMap<String, String> get() {
        LinkedHashMap map = new LinkedHashMap();
        Properties properties = new Properties();

        try {
            InputStream e = VideoSQLImporter.class.getResourceAsStream("/dbImport.properties");  //通过配置文件来判定是否是首次建库，如果已经创建了，就不再执行建库
            if(e == null) {
                return map;
            }

            properties.load(e);
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
    }

    /**
     * 保存配置文件dbImport.properties，用于检查是否进行过数据库初始化
     *
     * @param info
     * @return
     */
    private boolean save(Map<String, String> info) {
        Properties properties = new Properties();
        properties.putAll(info);
        FileOutputStream fileOutputStream = null;

        try {
            String e = VideoSQLImporter.class.getClassLoader().getResource("").getPath();
            fileOutputStream = new FileOutputStream(e + "dbImport.properties");
            properties.store(fileOutputStream, "");
            boolean var5 = true;
            return var5;
        } catch (Exception var15) {
            ;
        } finally {
            try {
                fileOutputStream.close();
            } catch (Exception var14) {
                ;
            }

        }

        return false;
    }

    public static final synchronized void exeucteOneUserSQLFile(Class _class, String sqlContent, String dbName, String sqlDelimiter) {
        (new VideoSQLImporter(_class)).importFile( sqlContent, dbName, sqlDelimiter);
    }


}
