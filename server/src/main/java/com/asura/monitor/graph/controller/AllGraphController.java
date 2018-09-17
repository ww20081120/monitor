package com.asura.monitor.graph.controller;

import com.asura.framework.base.paging.SearchMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.asura.common.response.ResponseVo;
import com.asura.monitor.configure.conf.MonitorCacheConfig;
import com.asura.monitor.grafana.controller.GrafanaController;
import com.asura.monitor.grafana.entity.DashboardEntity;
import com.asura.monitor.grafana.service.DashboardService;
import com.asura.monitor.graph.entity.BindImageEntity;
import com.asura.monitor.graph.entity.PushEntity;
import com.asura.monitor.graph.entity.StatusEntity;
import com.asura.monitor.graph.util.FileRender;
import com.asura.monitor.graph.util.FileWriter;
import com.asura.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

import static com.asura.monitor.graph.util.FileRender.getDirFiles;
import static com.asura.monitor.graph.util.FileRender.getSubDir;
import static com.asura.monitor.graph.util.FileRender.readHistory;
import static com.asura.monitor.graph.util.FileRender.separator;
import static com.asura.monitor.graph.util.FileWriter.dataDir;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 * 所有图像接口，包括数据上传接口，数据展示接口
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/08/11 07:49:11
 * @since 1.0
 */
@Controller
@RequestMapping("/monitor/graph/all/")
public class AllGraphController {

    Logger logger = LoggerFactory.getLogger(AllGraphController.class);

    private static Map<String, Map> hostIdMap;
    private static Map<String,String> indexMap;

    @Autowired
    private GrafanaController grafanaController;

    @Autowired
    private DashboardService dashboardService;

    /**
     * @param ip
     * @param select
     * @param width
     * @param model
     * 2017-02-05
     * @return
     */
    @RequestMapping("index")
    public String index(String ip, String select, String width, String type, Model model) {
        model.addAttribute("ip", ip);
        model.addAttribute("width", width);
        model.addAttribute("select", select);
        if (type != null && type.equals("bind")) {
            model.addAttribute("bind", "&type=bind");
        }

        return "/monitor/graph/all/index";
    }

    /**
     * 时间段图像显示
     * @param ip
     * @param groups
     * @param name
     * @param model
     * @return
     */
    @RequestMapping("listImg")
    public String sub(String ip, String groups, String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("n", groups);
        ArrayList arrayList = new ArrayList();
        arrayList.add(3);
        arrayList.add(7);
        arrayList.add(15);
        arrayList.add(30);
        arrayList.add(60);
        arrayList.add(90);
        arrayList.add(120);
        arrayList.add(180);
        arrayList.add(240);
        arrayList.add(360);
        model.addAttribute("days", arrayList);
        model.addAttribute("ip", ip);
        return "/monitor/graph/all/listImg";
    }


    /**
     * 获取图像目录
     * @param model
     * @param ip
     * @param select
     * @return
     */
    public Model getImagesDir(Model model, String ip, String select, boolean all){
        ArrayList dir ;
        dir = getSubDir(ip);

        // 获取所有的类型
        Map<String, ArrayList> map = FileRender.getGraphName(dir, ip);
        Map tempMap = new HashMap();
        if (CheckUtil.checkString(select)) {
            model.addAttribute("select", select);
            String[] selectList = select.split(",");
            String[] types ;
            for (String s : selectList) {
                ArrayList<String> tempArr = new ArrayList();
                types = s.split("\\|");
                ArrayList<String> t = (ArrayList) map.get(types[0]);
                if (t==null){continue;}
                for (String tname : t) {
                    if (tname.equals(types[1].trim())) {
                        tempArr.add(tname);
                    }
                }
                if (tempMap.get(types[0]) != null) {
                    ArrayList newTemp = (ArrayList) tempMap.get(types[0]);
                    for (String ns : tempArr) {
                        newTemp.add(ns);
                    }
                    tempMap.put(types[0], newTemp);
                } else {
                    tempMap.put(types[0], tempArr);
                }
            }
        }
        model.addAttribute("names", dir);
        if (tempMap.size() > 0) {
            model.addAttribute("types", tempMap);
        } else {
            model.addAttribute("types", map);
            // 取消空选择时选择所有数据,只选择前10个
            int count= 0;
            for (Map.Entry<String, ArrayList> entry : map.entrySet()) {
                ArrayList names = entry.getValue();
                tempMap.put(entry.getKey(), names);
                count += names.size();
                if (count > 10 && ! all){
                    break;
                }
            }
            model.addAttribute("types", tempMap);
        }
        return model;
    }

    /**
     * 2017-07-09 添加grafana支持
     * @param model
     * @param name
     * @param type
     */
    void getGrafanaImage(Model model, String name, String type, String startT, String endT){
        if (CheckUtil.checkString(startT) && startT.length() > 10){
            logger.info("获取到图像启动时间 " + startT);
            logger.info("获取到图像完成时间 " + endT);
            model.addAttribute("start", DateUtil.dateToStamp(startT.split(",")[0]+" 00:00:00")+"000");
            model.addAttribute("to", DateUtil.dateToStamp(endT.split(",")[0] + " 00:00:00")+"000");
        }
        SearchMap searchMap = new SearchMap();
        searchMap.put("slug", Md5Util.MD5(name+type));
        List<DashboardEntity> dashboardEntities = dashboardService.getListData(searchMap, "selectImageExists");
        for (DashboardEntity entity:dashboardEntities){
            model.addAttribute("slug", entity.getSlug());
            model.addAttribute("orgId", entity.getOrgId());
        }
        Resource resource ;
        Properties props ;
        resource = new ClassPathResource("/system.properties");
        try {
            props = PropertiesLoaderUtils.loadProperties(resource);
            String url = (String) props.get("grafanaServer");
            logger.info("获取到grafana url " + url);
            model.addAttribute("grafanaServer", url.trim());
        }catch (Exception e){
            try {
                // 使用环境变量
                logger.info("获取到grafana url " + System.getenv("grafanaServer").trim());
                model.addAttribute("grafanaServer", System.getenv("grafanaServer").trim());
            }catch (Exception e1){
                logger.error("获取到grafana url 失败" ,e1);
            }
        }
    }

    /**
     * 所有图像入口
     *
     * @param ip
     * @param select
     * @param startT
     * @param endT
     * @param type
     * @param width
     * @param dayNumber
     * @param model
     * @param ips
     * @param isAll
     * @param mline
     * @return
     */
    @RequestMapping("sub")
    public String sub(String ip, String grafana, String select, String startT, String endT, String type, String width, Model model, String dayNumber, String isAll, String ips, String mline, HttpServletRequest request) {
        // 获取默认数据
        if (ip == null || ip.length() < 1) {
            return "/monitor/graph/all/sub";
        }
        if (CheckUtil.checkString(dayNumber)){
            model.addAttribute("dayNumber", dayNumber);
        }
        getImagesDir(model, ip, select, false);
        if (CheckUtil.checkString(width)) {
            model.addAttribute("width", width);
        }
        model.addAttribute("startT", startT);
        model.addAttribute("endT", endT);
        model.addAttribute("ip", ip);

        // 生产grafana图表数据
        if (null != ips) {
            model.addAttribute("ips", ips.split(","));
        }else{
            model.addAttribute("ips", ip.split(","));
        }

        if(null != mline  && mline.equals("1")) {
            System.out.println("mline 1 ");
            // 生产grafana多线图
            if (CheckUtil.checkString(grafana)) {
                grafanaController.getGrafanaTemplate(request, select, "mgroup");
                getGrafanaImage(model, select, "mgroup", startT, endT);
                return "/monitor/graph/all/grafana";
            }
            return "/monitor/graph/all/mline";
        }

        if(isAll != null && isAll.equals("1")) {
            // 生产grafana 多个指标成为一个图
            if (CheckUtil.checkString(grafana)) {
                grafanaController.getGrafanaTemplate(request, select, "group");
                getGrafanaImage(model, select, "group",startT, endT);
                return "/monitor/graph/all/grafana";
            }
            return "/monitor/graph/all/merger";
        }

        if (null == type) {
            // 生产单线线图
            if (CheckUtil.checkString(grafana)) {
                grafanaController.getGrafanaTemplate(request, select, "msign");
                getGrafanaImage(model, select, "msign", startT, endT);
                return "/monitor/graph/all/grafana";
            }
            return "/monitor/graph/all/sub";
        } else {
            return "/monitor/graph/all/selectSub";
        }
    }

    /**
     * 获取图像名称
     *
     * @param ip
     * @param model
     *
     * @return
     */
    @RequestMapping("selectImg")
    public String selectImg(String ip, Model model) {
        ArrayList dir = getSubDir(ip);
        // 获取所有的类型
        Map map = FileRender.getGraphName(dir, ip);
        model.addAttribute("names", dir);
        model.addAttribute("types", map);
        return "/monitor/graph/all/selectImg";
    }


    /**
     * 数据写入
     *
     * @param entity
     * @param request
     */
    public void pushData(PushEntity entity, HttpServletRequest request) {
        String ipAddr;
        String name = FileRender.replace(entity.getName());
        String groups = FileRender.replace(entity.getGroups());
        String value = entity.getValue();
        // 获取客户端IP
        if (entity.getIp() == null || entity.getIp().length() < 10) {
            ipAddr = RequestClientIpUtil.getIpAddr(request);
        } else {
            ipAddr = FileRender.replace(entity.getIp());
        }

        // 只将数据写入到文件
        if (name != null && value != null && value.length() > 0) {
            FileWriter.writeHistory(groups, ipAddr, name, value);
        }

        entity.setTime(DateUtil.getTimeStamp() + "");
        entity.setServer(request.getLocalAddr());
    }

    /**
     * 监控数据上报接口
     * 单个上传,打包上传
     * 方法:post,get
     *
     * @return
     */
    @RequestMapping("push")
    @ResponseBody
    public ResponseVo push(PushEntity entity, String lentity, HttpServletRequest request) {

        if (lentity != null) {
            Type type = new TypeToken<ArrayList<PushEntity>>() {
            }.getType();
            List<PushEntity> list = new Gson().fromJson(lentity, type);
            for (PushEntity entity1 : list) {
                if (entity1 == null) {
                    continue;
                }
                pushData(entity1, request);
            }

        } else {
            pushData(entity, request);
        }
        entity.setServer(request.getLocalAddr());
        return ResponseVo.responseOk(entity);
    }

    /**
     * 服务器状态展示
     *
     * @return
     */
    @RequestMapping("status")
    public String status(String ip, Model model) {
        model.addAttribute("ip", ip);
        return "/monitor/graph/all/status";
    }

    /**
     * 获取服务器状态展示的数据
     *
     * @param ip
     *
     * @return
     */
    @RequestMapping("statusData")
    @ResponseBody
    public String statusData(String ip) throws IOException {
        ArrayList<String> dirs = FileRender.getSubDir(ip);
        ArrayList<StatusEntity> statusEntities = new ArrayList<>();

        //  大目录设置
        StatusEntity ipStatus = new StatusEntity();
        ipStatus.setName(ip);
        ipStatus.setKey(0);
        statusEntities.add(ipStatus);
        int key = 1;
        for (String dir : dirs) {
            StatusEntity statusEntity = new StatusEntity();
            statusEntity.setName(dir);
            statusEntity.setKey(key);
            ArrayList<String> dirList = new ArrayList<>();
            statusEntity.setDirs(dirList);
            key += 1;
            statusEntities.add(statusEntity);
        }

        ArrayList arrayList = new ArrayList();
        for (StatusEntity entity : statusEntities) {
            arrayList.add(entity);
            key = 1000;
            // 获取所有的类型
            String statusDir = FileRender.getStatusDir(ip, entity.getName());
            File[] fileList = getDirFiles(statusDir);
            if (fileList == null) {
                continue;
            }
            for (File f : fileList) {
                StatusEntity statusEntity = new StatusEntity();
                statusEntity.setKey(Integer.valueOf(entity.getKey() + "" + key));
                String last = "0";
                if (f.isDirectory()) {
                    File[] subFile = getDirFiles(statusDir + f.getName());
                    for (File subF : subFile) {
                        statusEntity.setName(f.getName().replace("SLASH", "/") + " " + subF.getName());
                        last = FileRender.readLastLine(statusDir + separator + f.getName() + separator + subF.getName());
                        String[] result = last.split(" ");
                        statusEntity.setTitle(result[1]);
                        statusEntity.setBoss(entity.getKey());
                        arrayList.add(statusEntity);
                        continue;
                    }
                    break;
                } else {
                    statusEntity.setName((f.getName().replace("SLASH", "/")));
                    last = FileRender.readLastLine(statusDir + separator + f.getName());
                }
                if (last == null) {
                    continue;
                }
                String[] result = last.split(" ");
                statusEntity.setTitle(result[1]);
                statusEntity.setBoss(entity.getKey());
                key += 1;
                arrayList.add(statusEntity);

            }

        }
        return new Gson().toJson(arrayList);
    }

    /**
     *
     * @param key
     * @param result
     * @param name
     * @param groups
     * @return
     */
    String getRealData(String key, String result, String name, String groups, Gson gson){
        Map map = new HashMap();
        List datas = new ArrayList();
        if (result.length()> key.length()) {
            Type type = new TypeToken<ArrayList<PushEntity>>() {
            }.getType();
            List<PushEntity> list = new Gson().fromJson(result, type);
            for (PushEntity entity : list) {
                if (entity.getGroups().equals(groups) && entity.getName().equals(name)) {
                    map.put("name", name);
                    map.put("groups", groups);
                    map.put("value", entity.getValue());
                }
            }
            datas.add(map);
        }
        return gson.toJson(datas);
    }

    /**
     * 获取agent服务器信息
     * @param server
     * @param redisUtil
     * @param gson
     * @return
     */
    public static Map<String,String> getHostInfo(String server, RedisUtil redisUtil, Gson gson){
        String serverId = redisUtil.get(MonitorCacheConfig.hostsIdKey + server);
        if (serverId == null ){
            return new HashMap<>();
        }
        String portData = redisUtil.get(MonitorCacheConfig.cacheAgentServerInfo + serverId);
        if (CheckUtil.checkString(portData)) {
            Map serverMap = gson.fromJson(portData, HashMap.class);
            Map map = new HashMap();
            map.put("server", server);
            map.put("serverId", serverId);
            map.put("port", serverMap.get("port"));
            return  map;
        }
        return new HashMap<>();
    }

    /**
     * 实时获取数据
     */
    @RequestMapping(value = "realtime", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String getRealtimeData(String server, String groups, String  name) throws Exception {
        String serverId = "";
        String port = "";
        RedisUtil redisUtil = new RedisUtil();
        Gson gson = new Gson();
        if (hostIdMap == null) {
            hostIdMap = new HashMap();
        }
        if (indexMap==null){
            indexMap = new HashMap<>();
        }

        if (hostIdMap.containsKey(server)) {
            port = (String) hostIdMap.get(server).get("port");
        } else {
            Map map =getHostInfo(server, redisUtil,  gson);
            if (map != null && map.size() > 0) {
                hostIdMap.put(server, map);
            }
        }

        String  key = groups + "." + name;
        String scriptId;
        if (!indexMap.containsKey(key)) {
            // 拼接文件目录
            String dir = dataDir + separator + "graph" + separator +"index" +separator;
            dir = dir + key + separator + "id";
            dir = FileRender.replace(dir);
            scriptId = FileRender.readLastLine(dir);
            indexMap.put(key, scriptId);
        }else{
            scriptId = indexMap.get(key);
        }
        String url = "http://" + server + ":" + port + "/api/realtime?scriptId=" + scriptId;
        String result = HttpUtil.sendGet(url);
        if (result.length()> key.length()){
            return getRealData(key, result, name, groups, gson);
        }else{
            return "[]";
        }
    }

    /**
     * @param ip
     *
     * @return
     */
    @RequestMapping(value = "bindData", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String bindData(String ip) {
        Gson gson = new Gson();
        ArrayList dir = getSubDir(ip);
        String startT = DateUtil.getDay();
        String endT = DateUtil.getDay();
        Map<String, ArrayList> maps = new HashMap<>();
        ArrayList timeList = new ArrayList();
        // 获取所有的类型
        boolean isTime = false;
        Map<String, ArrayList> map = FileRender.getGraphName(dir, ip);
        ArrayList<BindImageEntity> list = new ArrayList<>();
        for (Map.Entry<String, ArrayList> entry : map.entrySet()) {
            ArrayList<String> values = entry.getValue();
            for (String name : values) {
                BindImageEntity bindImageEntity = new BindImageEntity();
                bindImageEntity.setName(name.replace("---", " ").replace("SLASH", "/"));
                bindImageEntity.setType("spline");
                bindImageEntity.setUnit("");
                ArrayList<ArrayList> data = readHistory(ip, entry.getKey(), name, startT, endT, null, false, null);
                ArrayList<Double> bindData = new ArrayList<>();
                for (ArrayList<Double> d : data) {
                    bindData.add(d.get(1));
                    if (!isTime) {
                        timeList.add(d.get(0));
                    }
                }
                isTime = true;
                bindImageEntity.setData(bindData);
                bindImageEntity.setValueDecimals(2);
                list.add(bindImageEntity);
            }
        }
        maps.put("data", list);
        maps.put("xdata", timeList);
        return new Gson().toJson(maps);
    }
}