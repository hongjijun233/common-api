package com.lianghongji.manageuser.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 管理用户登录Dto
 *
 * @author liang.hongji
 */
@ApiModel(value = "ManageLoginDto", description = "用户登录信息")
@Data
public class ManageLoginDto {

    @ApiModelProperty(value = "用户名，系统唯一", required = true)
    @NotNull(message = "用户名必须输入")
    private String userName;

    @ApiModelProperty(value = "密码", required = true)
    @NotNull(message = "用户密码必须输入")
    private String password;
}
