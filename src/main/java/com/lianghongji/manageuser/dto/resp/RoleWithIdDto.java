package com.lianghongji.manageuser.dto.resp;

import com.lianghongji.manageuser.dto.RoleDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 角色信息包括id
 *
 * @author liang.hongji
 */
@ApiModel(value = "RoleWithIdDto", description = "角色信息包括id")
@Data
public class RoleWithIdDto extends RoleDto {

	@NotNull(message = "角色名id")
    private Long id;

	@ApiModelProperty(value = "创建时间")
	private String createTime;
	
	public RoleWithIdDto(){
    }
    
	public RoleWithIdDto(String name, String remark, Long id, String createTime) {
		super(name, remark);
		this.id = id;
		this.createTime = createTime;
	}
}
