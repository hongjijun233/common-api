package com.lianghongji.manageuser.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * 角色信息
 *
 * @author liang.hongji
 */
@ApiModel(value = "RoleDto", description = "角色信息")
@Data
@AllArgsConstructor
public class RoleDto {

    @ApiModelProperty(value = "角色名，系统唯一", required = true)
    @NotBlank(message = "角色名不能为空")
    private String name;

    @ApiModelProperty(value = "角色名，备注")
    private String remark;
    
    public RoleDto(){}
}
