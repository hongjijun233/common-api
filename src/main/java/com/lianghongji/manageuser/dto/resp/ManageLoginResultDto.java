package com.lianghongji.manageuser.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 管理用户登录成功返回结果
 * 
 * @author liang.hongji
 *
 */
@ApiModel(value = "ManageLoginResultDto", description = "管理用户登录成功返回结果")
@Data
@AllArgsConstructor
public class ManageLoginResultDto {

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "用户名token")
	private String userAccessToken;

	@ApiModelProperty(value = "用户名id")
	private Long userId;

	@ApiModelProperty(value = "用户名角色名")
	private String role;

	public ManageLoginResultDto(){

	}
}
