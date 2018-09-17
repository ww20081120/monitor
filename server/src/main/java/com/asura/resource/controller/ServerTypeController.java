package com.asura.resource.controller;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.google.gson.Gson;
import com.asura.common.controller.IndexController;
import com.asura.common.response.PageResponse;
import com.asura.common.response.ResponseVo;
import com.asura.resource.entity.CmdbResourceServerEntity;
import com.asura.resource.entity.CmdbResourceServerTypeEntity;
import com.asura.resource.service.CmdbResourceServerService;
import com.asura.resource.service.CmdbResourceServerTypeService;
import com.asura.util.DateUtil;
import com.asura.util.PermissionsCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *  资源配置操作系统类型配置
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2016/7/28 17:59
 * @since 1.0
 */
@Controller
@RequestMapping("/resource/configure/serverType/")
public class ServerTypeController {

    @Autowired
    private CmdbResourceServerTypeService service ;

    @Autowired
    private PermissionsCheck permissionsCheck;

    private SearchMap searchMapNull = new SearchMap();

    private PageBounds pageBoundsNull = PageResponse.getPageBounds(100000,1);

    @Autowired
    private IndexController indexController;

    @Autowired
    private CmdbResourceServerService serverService;

    /**
     * 列表
     * @return
     */
    @RequestMapping("list")
    public String list(){
        return "/resource/configure/serverType/list";
    }

    /**
     * 列表数据
     * @return
     */
    @RequestMapping(value = "listData", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listData(int draw, int start, int length) {
        PageBounds pageBounds = PageResponse.getPageBounds(length, start);
        SearchMap searchMap = new SearchMap();
        PagingResult<CmdbResourceServerTypeEntity> result = service.findAll(searchMap,pageBounds);
        return PageResponse.getMap(result, draw);
    }


    /**
     * 获取数据
     * @param model
     * @return
     */
    public Model getData(Model model){
        PagingResult<CmdbResourceServerTypeEntity> result = service.findAll(searchMapNull,pageBoundsNull);
        model.addAttribute("types",result.getRows());
        return model;
    }

    /**
     * 添加页面
     * @return
     */
    @RequestMapping("add")
    public String add(Model model){
        getData(model);
        return "/resource/configure/serverType/add";
    }


    /**
     * 保存
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public ResponseVo save(CmdbResourceServerTypeEntity entity, HttpServletRequest request){
        String user = permissionsCheck.getLoginUser(request.getSession());
        entity.setLastModifyUser(user);
        entity.setCreateUser(user);
        if (entity.getDepend().equals(0)){
            entity.setDepend(null);
        }
        if(entity.getTypeId() != null){
            service.update(entity);
        }else {
            entity.setCreateTime(DateUtil.getDateStampInteter());
            service.save(entity);
        }
        Gson gson = new Gson();
        indexController.logSave(request, "添加操作系统类型" + gson.toJson(entity));
        return ResponseVo.responseOk(null);
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("detail")
    public String detail(int id, Model model){
        CmdbResourceServerTypeEntity result = service.findById(id,CmdbResourceServerTypeEntity.class);
        model.addAttribute("configs",result);
        getData(model);
        return "/resource/configure/serverType/add";
    }

    /**
     * 删除信息
     * @return
     */
    @RequestMapping("deleteSave")
    public ResponseVo delete(int id, HttpServletRequest request){
        Gson gson = new Gson();
        CmdbResourceServerTypeEntity result = service.findById(id, CmdbResourceServerTypeEntity.class);
        SearchMap searchMap = new SearchMap();
        searchMap.put("typeName", result.getTypeName());
        List<CmdbResourceServerEntity> data = serverService.getDataList(searchMap, "selectByAll");
        if (data.size() < 1) {
            service.delete(result);
            indexController.logSave(request, "删除设备类型:" + gson.toJson(result));
        }else{
            return ResponseVo.responseError("请先删除引用的设备记录");
        }
        return ResponseVo.responseOk(null);
    }
}
