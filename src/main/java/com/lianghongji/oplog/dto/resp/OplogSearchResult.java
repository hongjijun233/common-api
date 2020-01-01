package com.lianghongji.oplog.dto.resp;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 操作日志搜索结果
 *
 * @author deng.huaiyu
 */
@ApiModel(value = "OplogSearchResult", description = "操作日志搜索结果")
@Data
public class OplogSearchResult {

    @ApiModelProperty(value = "操作日志id")
    private Long id;
    
    @ApiModelProperty(value = "操作用户")
    private String username;
    
    @ApiModelProperty(value = "操作时间")
    private String operationTime;

    @ApiModelProperty(value = "操作参数")
    private String operationParam;

    @ApiModelProperty(value = "操作路径")
    private String operationPath;
}
