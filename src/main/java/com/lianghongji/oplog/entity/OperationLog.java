package com.lianghongji.oplog.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 系统日志的实体类
 *
 * @author  lianghj.hongji
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class OperationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String operationPath;
    private String operationContent;
    private String operationParam;
    private Date operationTime;
}
