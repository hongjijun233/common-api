package com.lianghongji.oplog.repository;

import com.lianghongji.oplog.dto.req.OplogSearch;
import com.lianghongji.oplog.entity.OperationLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作日志Repository扩展
 *
 * @author liang.hongji
 */
@Repository
public interface OplogExtRepository {
    List<OperationLog> search(OplogSearch oplogSearch);

    Long countSearch(OplogSearch oplogSearch);
}
