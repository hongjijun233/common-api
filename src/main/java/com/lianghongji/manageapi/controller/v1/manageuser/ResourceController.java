package com.lianghongji.manageapi.controller.v1.manageuser;

import com.lianghongji.ErrorCode;
import com.lianghongji.Exception.BusinessRuntimeException;
import com.lianghongji.controller.AbstractController;
import com.lianghongji.controller.PageCommResult;
import com.lianghongji.manageuser.dto.ResourceDto;
import com.lianghongji.manageuser.dto.req.ResourceSearch;
import com.lianghongji.manageuser.dto.resp.ResourceSearchResult;
import com.lianghongji.manageuser.entity.ManageResources;
import com.lianghongji.manageuser.service.ResourceService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 资源管理接口
 *
 * @author liang.hongji
 */
@Api(description = "资源管理接口")
@RestController
@RequestMapping("/v1/manageApi/manageuser/resource")
public class ResourceController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

	@Autowired
	private ResourceService resourceService;

	@ApiOperation(value = "搜索资源")
	@ApiImplicitParam(name="ManageUserAccessToken", value="用于验证登录的token", paramType="header",dataType="string")
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public PageCommResult<ResourceSearchResult> search(@Valid @RequestBody ResourceSearch resourceSearch,
													   BindingResult bindingResult) {
		LOG.info(" access /v1/manageApi/manageuser/resource/search resourceSearch {}", resourceSearch);
		return resourceService.search(resourceSearch);
	}

	@ApiOperation(value = "获取资源信息")
	@ApiImplicitParam(name="ManageUserAccessToken", value="用于验证登录的token", paramType="header",dataType="string")
	@RequestMapping(value = "/{resourceId}", method = RequestMethod.GET)
	public PageCommResult<ResourceSearchResult> get(@PathVariable("resourceId") Long resourceId) {
		LOG.info(" access /v1/manageApi/manageuser/resource/{resourceId} resourceId {}", resourceId);
		ManageResources manageResources = resourceService.find(resourceId, ErrorCode.RESOURCE_NOT_FOUND);
		ResourceSearchResult searchResult = new ResourceSearchResult();
		BeanUtils.copyProperties(manageResources,searchResult);
		return successPageResult(searchResult);
	}

	@ApiOperation(value = "新增资源")
	@ApiImplicitParam(name="ManageUserAccessToken", value="用于验证登录的token", paramType="header",dataType="string")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public PageCommResult<Object> add(@Valid @RequestBody ResourceDto resourceDto,
									  BindingResult bindingResult) {
		LOG.info(" access /v1/manageApi/manageuser/resource/add resourceDto {} ", resourceDto);
		if (bindingResult.hasErrors()) {
			throw new BusinessRuntimeException(ErrorCode.PRAMERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		ManageResources manageResources = new ManageResources();
		BeanUtils.copyProperties(resourceDto,manageResources);
		manageResources.setName(manageResources.getMenuName());
		resourceService.save(manageResources);
		return successPageResultWithEmptyResult();
	}

	@ApiOperation(value = "编辑资源")
	@ApiImplicitParam(name="ManageUserAccessToken", value="用于验证登录的token", paramType="header",dataType="string")
	@RequestMapping(value = "/{resourceId}/edit", method = RequestMethod.POST)
	public PageCommResult<Object> edit(@PathVariable("resourceId") Long resourceId,
									   @Valid @RequestBody ResourceDto resourceDto, BindingResult bindingResult) {
		LOG.info(" access /v1/manageApi/manageuser/resource/{resourceId}/edit resourceId {} resourceDto {} ", resourceId, resourceDto);
		if (bindingResult.hasErrors()) {
			throw new BusinessRuntimeException(ErrorCode.PRAMERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		resourceDto.setName(resourceDto.getMenuName());
		resourceService.editAfterFind(resourceId,resourceDto,ErrorCode.RESOURCE_NOT_FOUND);
		return successPageResultWithEmptyResult();
	}

	@ApiOperation(value = "批量删除资源")
	@ApiImplicitParam(name="ManageUserAccessToken", value="用于验证登录的token", paramType="header",dataType="string")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public PageCommResult<Object> batchDelete(@RequestParam("resourceIds") List<Long> resourceIds) {
		LOG.info(" access /v1/manageApi/manageuser/resource/batchDelete resourceIds {} ", resourceIds);
		resourceService.batchDelete(resourceIds);
		return successPageResultWithEmptyResult();
	}

	@ApiOperation(value = "获取所有菜单")
	@ApiImplicitParam(name="ManageUserAccessToken", value="用于验证登录的token", paramType="header",dataType="string")
	@RequestMapping(value = "/allMenus", method = RequestMethod.GET)
	public PageCommResult<ResourceDto> allMenus() {
		LOG.info(" access /v1/manageApi/manageuser/resource/allMenus");
		List<ManageResources> manageResources = resourceService.findAll();
		List<ResourceDto> resourceDtos = new ArrayList<>();
		manageResources.forEach(resources -> {
			ResourceDto resourceDto = new ResourceDto();
			BeanUtils.copyProperties(resources,resourceDto);
			resourceDtos.add(resourceDto);
		});
		return successPageResult(resourceDtos,0,0,resourceDtos.size());
	}
}
