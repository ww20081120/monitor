package com.asura.resource.configure.server.dao;
import com.asura.common.dao.BaseDao;
import com.asura.resource.configure.server.entity.CmdbResourceServerHistoryEntity;
import com.asura.resource.configure.server.dao.CmdbResourceServerHistoryDao;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.paging.SearchMap;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;



/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2017-05-08 12:34:43
 * @since 1.0
 */
@Repository("com.asura.resource.configure.server.dao.CmdbResourceServerHistoryDao")
public class CmdbResourceServerHistoryDao extends BaseDao<CmdbResourceServerHistoryEntity>{

    @Resource(name="monitor.MybatisDaoContext")
     private MybatisDaoContext mybatisDaoContext;
     /**
     *
     * @param searchMap
     * @param pageBounds
     * @return
     */
     public PagingResult<CmdbResourceServerHistoryEntity> findAll(SearchMap searchMap, PageBounds pageBounds, String sqlId){
        return mybatisDaoContext.findForPage(this.getClass().getName()+"."+sqlId,CmdbResourceServerHistoryEntity.class,searchMap,pageBounds);
     }
}