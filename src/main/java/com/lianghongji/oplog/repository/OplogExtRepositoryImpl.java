package com.lianghongji.oplog.repository;

import com.lianghongji.oplog.dto.req.OplogSearch;
import com.lianghongji.oplog.entity.OperationLog;
import com.lianghongji.util.DateUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * 操作日志扩展实现
 *
 * @author  liang.hongji
 */
public class OplogExtRepositoryImpl implements OplogExtRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<OperationLog> search(OplogSearch oplogSearch) {
        String sql = " ";
        Query query = searchCommon(sql, oplogSearch, OperationLog.class);
        query.setFirstResult(oplogSearch.getPage().getStart() * oplogSearch.getPage().getCount());
        query.setMaxResults(oplogSearch.getPage().getCount());
        return query.getResultList();
    }

    public <T> Query searchCommon(String querySQL, OplogSearch oplogSearch, Class<T> resultClass){
        querySQL += " from OperationLog o where 1 = 1 ";
        if(StringUtils.isNotBlank(oplogSearch.getOpContent())){
            querySQL += " and o.operationContent like :opContent ";
        }
        if(StringUtils.isNotBlank(oplogSearch.getUsername())){
            querySQL += " and o.username = :username  ";
        }
        if (StringUtils.isNotBlank(oplogSearch.getOpTimeStart())){
            querySQL += " and o.operationTime > :opTimeStart ";
        }
        if (StringUtils.isNotBlank(oplogSearch.getOpTimeEnd())){
            querySQL += " and o.operationTime < :opTimeEnd ";
        }
        Query query = em.createQuery(querySQL, resultClass);
        if(StringUtils.isNotBlank(oplogSearch.getOpContent())){
            query.setParameter("opContent", oplogSearch.getOpContent() + "%");
        }
        if(StringUtils.isNotBlank(oplogSearch.getUsername())){
            query.setParameter("username", oplogSearch.getUsername());
        }
        if (StringUtils.isNotBlank(oplogSearch.getOpTimeStart())){
            query.setParameter("opTimeStart", DateUtils.parseYYYMMDDHHmmss(oplogSearch.getOpTimeStart() + " 00:00:00"));
        }
        if (StringUtils.isNotBlank(oplogSearch.getOpTimeEnd())){
            query.setParameter("opTimeEnd", DateUtils.parseYYYMMDDHHmmss(oplogSearch.getOpTimeEnd() + " 23:59:59"));
        }
        return query;
    }

    public Long countSearch(OplogSearch oplogSearch){
        String querySQL = "select count(*)  ";
        Query query = searchCommon(querySQL, oplogSearch, Long.class);
        return (Long) query.getSingleResult();
    }
}
