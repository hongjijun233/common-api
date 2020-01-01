package com.lianghongji.manageuser.dto.req;

import com.lianghongji.dto.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源搜索dto
 *
 * @author  liang.hongji
 */
@ApiModel(value = "ResourceSearch", description = "资源搜索")
@Data
public class ResourceSearch {

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "资源对应的路径")
    private String resourceUrl;
    
    @ApiModelProperty(value = "分页参数")
    private PageParam page = new PageParam();
}
