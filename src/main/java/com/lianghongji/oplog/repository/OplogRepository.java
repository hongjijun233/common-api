package com.lianghongji.oplog.repository;

import com.lianghongji.oplog.entity.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 操作日志Repository
 *
 * @author  liang.hongji
 */
public interface OplogRepository extends JpaRepository<OperationLog, Long>, OplogExtRepository {
}
