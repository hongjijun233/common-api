package com.lianghongji.manageuser.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "ManageUserSignUpDto", description = "用户注册dto")
public class ManageUserSighUpDto {

    @ApiModelProperty(value = "用户名", required = true)
    @NotNull(message = "必须输入用户名")
    private String username;

    @ApiModelProperty(value = "姓名", required = true)
    @NotNull(message = "必须输入姓名")
    private String name;

    @ApiModelProperty(value = "密码", required = true)
    @NotNull(message = "必须输入密码")
    private String password;

    @ApiModelProperty(value = "确认密码", required = true)
    @NotNull(message = "必须输入确认密码")
    private String verifyPassword;

    @ApiModelProperty(value = "注册类型，2为品牌商，3为借卖方", required = true)
    @NotNull(message = "必须选择注册类型")
    private Long type;
}
