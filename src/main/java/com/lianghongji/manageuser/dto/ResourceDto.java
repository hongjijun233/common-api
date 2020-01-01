package com.lianghongji.manageuser.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 资源dto
 *
 * @author liang.hongji
 */

@ApiModel(value = "ResourceDto", description = "资源信息")
@Data
public class ResourceDto {
    @ApiModelProperty(value = "资源id")
    protected Long id;

    @ApiModelProperty(value = "资源名称")
    protected String name;

    @ApiModelProperty(value = "资源对应的路径")
    protected String resourceUrl = "";

    @ApiModelProperty(value = "父资源对应的id")
    protected long parentResourceId = 0;

    @ApiModelProperty(value = "菜单名称")
    protected String menuName = "";

    @ApiModelProperty(value = "备注")
    protected String remark = "";
}
