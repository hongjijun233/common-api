package com.lianghongji.manageuser.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源搜索结果
 *
 * @author  liang.hongji
 */
@ApiModel(value = "ResourceSearchResult", description = "资源搜索结果")
@Data
public class ResourceSearchResult {

    @ApiModelProperty(value = "资源id")
    private long id;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "资源对应的路径")
    private String resourceUrl;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "备注")
    private String remark;
}