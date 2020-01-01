package com.lianghongji.oplog.service;


import com.lianghongji.controller.PageCommResult;
import com.lianghongji.oplog.dto.req.OplogSearch;
import com.lianghongji.oplog.dto.resp.OplogSearchResult;
import com.lianghongji.oplog.entity.OperationLog;
import com.lianghongji.oplog.repository.OplogRepository;
import com.lianghongji.service.AbstractService;
import com.lianghongji.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作日志服务类
 *
 * @author  liang.hongji
 */
@Service
public class OplogService extends AbstractService<OplogRepository, OperationLog, Long> {

    private static final Logger LOG = LoggerFactory.getLogger(OplogService.class);

    /**
     * 搜索操作日志
     * @param oplogSearch
     * @return
     */
    public PageCommResult<OplogSearchResult> search(OplogSearch oplogSearch){
        List<OperationLog> oplogs = this.repository.search(oplogSearch);
        List<OplogSearchResult> results = new ArrayList<>();
        BeanUtils.copyListProperties(oplogs, results, OplogSearchResult.class);
        Long total = this.repository.countSearch(oplogSearch);
        return PageCommResult.successPageResult(results, oplogSearch.getPage().getStart(),
                oplogSearch.getPage().getCount(), total);
    }
}
