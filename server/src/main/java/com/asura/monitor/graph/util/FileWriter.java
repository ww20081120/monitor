package com.asura.monitor.graph.util;

import com.asura.monitor.graph.entity.PushEntity;
import com.asura.monitor.util.ToElasticsearchUtil;
import com.asura.util.DateUtil;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 * 文件写入类
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/08/07 09:19
 * @since 1.0
 */
public class FileWriter {

    private final static Logger logger = Logger.getLogger(FileRender.class);

    private final static DateUtil dateUtil = new DateUtil();

    // 默认用这个，如果需要更新，改掉就行
    public final static String dataDir = System.getProperty("user.home");

    // 获取文件分割符号，window \ linux /
    public final static String separator = System.getProperty("file.separator");


    /**
     * @param dir
     */
    static void makeDir(String dir) {
        File folder = new File(dir);
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdirs();
        }
    }

    /**
     * @param file
     */
    public static void makeDirs(String file) {
        String dir = file.substring(0, file.lastIndexOf(separator));
        makeDir(dir);
    }

    /**
     * @param file
     *         文件名
     * @param content
     *         文件内容
     */
    public static void writeFile(String file, String content, boolean append) {

        // 防止等待磁盘
        FileThread thread = new FileThread(file, content, append);
        thread.start();
        try {
            for (int i=0 ; i< 1000 ; i++) {
                if(thread.isAlive()) {
                    Thread.sleep(2);
                }
            }
            if (thread.isAlive()) {
                thread.interrupt();
                logger.error("退出文件写入线程...");
            }
        }catch (Exception e){
            thread.interrupt();
            e.printStackTrace();
        }
    }

    /**
     *
     * @param type
     * @param ip
     * @param name
     * @return
     */
    public static String getGraphFile(String type, String ip, String name){
        StringBuilder dir = new StringBuilder();
        // 拼接文件目录
        dir.append(dataDir)
                .append(separator)
                .append("graph")
                .append(separator)
                .append(ip)
                .append(separator)
                .append(type)
                .append(separator)
                .append( DateUtil.getDate("yyyy"))
                .append(separator)
                .append(DateUtil.getDate("MM"))
                .append(separator)
                .append(DateUtil.getDate("dd") )
                .append(separator)
                .append(name);
        return dir.toString();
    }

    /**
     * 历史数据放到文件
     *
     * @param type
     * @param ip
     * @param name
     * @param value
     */
    public static void writeHistory(String type, String ip, String name, String value) {
        // 防止非数字的写入到文件
        try {
            Double.valueOf(value);
        }catch (Exception e){
            return;
        }
        // 拼接文件目录
        String dir = getGraphFile(type, ip, name);
        // 将值组装成固定的时间和数据
        String content = DateUtil.getDateStampInteter() + "000 " + value.trim();
        writeFile(dir, content, true);
        try {
            ToElasticsearchUtil.pushQueue(type, ip, name, value);
        }catch (Exception e){

            logger.error("写入ES失败", e);
        }
    }


    /**
     * @param ip
     * @param name
     * @return
     */
    public  static  String getMonitorFile(String ip, String name){
        StringBuilder file = new StringBuilder();
        // 拼接文件目录
        file.append(dataDir)
                .append(separator)
                .append("monitor")
                .append(separator)
                .append(ip)
                .append(separator)
                .append(name);

        return file.toString();
    }

    /**
     * 将监控数据写入到文件
     *
     * @param ip
     * @param name
     * @param entity
     */
    public static void writeMonitorHistory(String ip, String name, PushEntity entity) {

        // 拼接文件目录
        String file = getMonitorFile(ip, name);
        if (entity.getTime() == null || entity.getTime().length() < 10 ) {
            entity.setTime(DateUtil.getDate("yyyy-MM-dd HH:mm:ss"));
        }

        String content = DateUtil.dateToStamp(entity.getTime()) + " ";
        if(entity.getMessages() != null) {
            content += "[" + entity.getMessages().trim() + "]";
        }
        content = content.trim();
        writeFile(file, content, false);
    }

    /**
     * 获取系统信息的希尔目录
     * @return
     */
    public static String getSysInfoDir(){
        StringBuilder file = new StringBuilder();
        // 拼接文件目录
        file.append(dataDir)
                .append(separator)
                .append("graph")
                .append(separator)
                .append("sysinfo")
                .append(separator)
                .append( DateUtil.getDate("yyyy"))
                .append(separator)
                .append(DateUtil.getDate("MM"))
                .append(separator)
                .append(DateUtil.getDate("dd") )
                .append(separator);
        return file.toString();
    }

    /**
     * 获取指定指标数据
     * @return
     */
    public static String getIndexData(String ip, String type, String name, int dataLen){
        String data  ="";
        String file = getGraphFile(type, ip, name);
        try {
           String lastData =  FileRender.readLastLine(file);
            if (lastData!= null){
                String[] datas = lastData.split(" ");
                data = datas[dataLen];
            }
        }catch (Exception e){
             e.printStackTrace();
        }
        return data;
    }

    /**
     *
     * @param datas
     * @param ip
     * @param start
     * @return
     */
    public  static String getItemData(String datas, String ip, int start){
        try {
            String[] items = datas.split("\\|");
            String data = FileWriter.getIndexData(ip, items[0], items[1], start);
            return data.trim();
        }catch (Exception e){
            return "";
        }
    }

    /**
     * 删除文件执行的行数
     * @param file
     * @param delNumber
     */
    public static void deleteFileLine(String file, int delNumber){
        try {
            int lineDel = delNumber;
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuffer sb = new StringBuffer(4096);
            String temp = null;
            int line = 0;
            while ((temp = br.readLine()) != null) {
                line++;
                if (line <= lineDel) {
                    continue;
                }
                sb.append(temp).append("\n");
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(file));
            bw.write(sb.toString());
            bw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}