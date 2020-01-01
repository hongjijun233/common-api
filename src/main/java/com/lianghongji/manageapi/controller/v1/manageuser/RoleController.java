package com.lianghongji.manageapi.controller.v1.manageuser;

import com.lianghongji.ErrorCode;
import com.lianghongji.Exception.BusinessRuntimeException;
import com.lianghongji.controller.AbstractController;
import com.lianghongji.controller.PageCommResult;
import com.lianghongji.manageuser.dto.ResourceDto;
import com.lianghongji.manageuser.dto.RoleDto;
import com.lianghongji.manageuser.dto.resp.RoleWithIdDto;
import com.lianghongji.manageuser.entity.Role;
import com.lianghongji.manageuser.service.RoleResourceRelaService;
import com.lianghongji.manageuser.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色管理相关Controller
 *
 * @author liang.hongji
 */
@RestController
@Api(description = "角色管理相关接口")
@RequestMapping("v1/manageApi/manageUser/role")
public class RoleController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleResourceRelaService roleResourceRelaService;

    @ApiOperation(value = "搜索角色")
    @ApiImplicitParam(name="ManageUserAccessToken", value="用于验证登录的token", paramType="header",dataType="string")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public PageCommResult<RoleWithIdDto> search(@RequestParam(value = "roleName", defaultValue = "") String roleName,
                                                @RequestParam(value = "start", defaultValue = "0") Integer start,
                                                @RequestParam(value = "count", defaultValue = "10") Integer count) {
        LOG.info(" access /v1/manageApi/manageUser/role/search roleName {} start {} count {} ", roleName, start, count);
        return roleService.search(roleName, start, count);
    }

    @ApiOperation(value = "获取角色信息")
    @ApiImplicitParam(name="ManageUserAccessToken", value="用于验证登录的token", paramType="header",dataType="string")
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public PageCommResult<RoleDto> get(@PathVariable("roleId") Long roleId) {
        LOG.info(" access /v1/manageApi/manageUser/role/{roleId} roleId {} ", roleId);
        Role role = roleService.find(roleId, ErrorCode.ROLE_NOT_FOUND);
        RoleDto roleDto = new RoleDto();
        BeanUtils.copyProperties(role, roleDto);
        return successPageResult(roleDto);
    }

    @ApiOperation(value = "新增角色")
    @ApiImplicitParam(name="ManageUserAccessToken", value="用于验证登录的token", paramType="header",dataType="string")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public PageCommResult<Object> add(@Valid @RequestBody RoleDto roleDto, BindingResult bindingResult) {
        LOG.info(" access /v1/manageApi/manageUser/role/add roleDto {} ", roleDto);
        if (bindingResult.hasErrors()) {
            throw new BusinessRuntimeException(ErrorCode.PRAMERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        if(roleService.exits("name", roleDto.getName(), Role.class)){
            throw new BusinessRuntimeException(ErrorCode.ROLE_EXITS);
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);
        roleService.save(role);
        return this.successPageResultWithEmptyResult();
    }

    @ApiOperation(value = "编辑角色")
    @ApiImplicitParam(name="ManageUserAccessToken", value="用于验证登录的token", paramType="header",dataType="string")
    @RequestMapping(value = "/{roleId}/edit", method = RequestMethod.POST)
    public PageCommResult<Object> edit(@PathVariable("roleId") Long roleId, @Valid @RequestBody RoleDto roleDto,
                                       BindingResult bindingResult) {
        LOG.info(" access /v1/manageApi/manageuser/role/{roleId}/edit roleId {} roleDto {} ", roleId, roleDto);
        if (bindingResult.hasErrors()) {
            throw new BusinessRuntimeException(ErrorCode.PRAMERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        roleService.editAfterFind(roleId, roleDto, ErrorCode.ROLE_NOT_FOUND);
        return this.successPageResultWithEmptyResult();
    }

    @ApiOperation(value = "批量删除角色")
    @ApiImplicitParam(name="ManageUserAccessToken", value="用于验证登录的token", paramType="header",dataType="string")
    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public PageCommResult<Object> batchDelete(@RequestBody List<Long> roleIds) {
        LOG.info(" access /v1/manageApi/manageuser/role/batchDelete roleIds {} ", roleIds);
        roleService.batchDelete(roleIds);
        return this.successPageResultWithEmptyResult();
    }

    @ApiOperation(value = "查找角色绑定的资源")
    @ApiImplicitParam(name="ManageUserAccessToken", value="用于验证登录的token", paramType="header",dataType="string")
    @RequestMapping(value = "/{roleId}/resources", method = RequestMethod.GET)
    public PageCommResult<ResourceDto> findResources(@PathVariable("roleId") long roleId) {
        LOG.info(" access /v1/manageApi/manageuser/role/{roleId}/resources roleId {} ", roleId);
        return roleResourceRelaService.search(roleId);
    }

    @ApiOperation(value = "角色绑定资源")
    @ApiImplicitParam(name="ManageUserAccessToken", value="用于验证登录的token", paramType="header",dataType="string")
    @RequestMapping(value = "/{roleId}/assignResources", method = RequestMethod.POST)
    public PageCommResult<Object> assignResources(@PathVariable("roleId") long roleId,
                                                  @Valid @RequestParam(required = false) List<Long> resourceIds) {
        LOG.info(" access /v1/manageApi/manageuser/role/{roleId}/assignResources roleId {} resourceIds {} ", roleId,
                resourceIds);
        roleResourceRelaService.batchInsert(roleId,resourceIds);
        return successPageResultWithEmptyResult();
    }
}
