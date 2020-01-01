package com.lianghongji.oplog.dto.req;

import com.lianghongji.dto.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 操作日志搜索
 *
 * @author liang.hongji
 */
@ApiModel(value = "OplogSearch", description = "操作日志搜索")
@Data
public class OplogSearch {

    @ApiModelProperty(value = "操作人")
    private String username;
    
    @ApiModelProperty(value = "操作开始时间")
    private String opTimeStart;
    
    @ApiModelProperty(value = "操作结束时间")
    private String opTimeEnd;
    
    @ApiModelProperty(value = "操作内容")
    private String opContent;
    
    @ApiModelProperty(value = "分页信息")
    private PageParam page = new PageParam();
}
