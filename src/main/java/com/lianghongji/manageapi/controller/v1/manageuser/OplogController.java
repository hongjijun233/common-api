package com.lianghongji.manageapi.controller.v1.manageuser;


import com.lianghongji.controller.AbstractController;
import com.lianghongji.controller.PageCommResult;
import com.lianghongji.oplog.dto.req.OplogSearch;
import com.lianghongji.oplog.dto.resp.OplogSearchResult;
import com.lianghongji.oplog.service.OplogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 操作日志接口
 *
 * @author deng.huaiyu
 */
@Api(description = "操作日志管理接口")
@RestController
@RequestMapping("/v1/manageApi/manageuser/opLog")
public class OplogController extends AbstractController {

	private static final Logger LOG = LoggerFactory.getLogger(OplogController.class);

	@Autowired
	OplogService service;

	@ApiOperation(value = "搜索操作日志")
	@ApiImplicitParam(name = "ManageUserAccessToken", value = "用于验证登录的token", paramType = "header", dataType = "string")
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public PageCommResult<OplogSearchResult> search(@Valid @RequestBody OplogSearch oplogSearch,
													BindingResult bindingResult) {
		LOG.info(" access /v1/manageApi/manageuser/opLog/search oplogSearch {} ",
				oplogSearch);
		return service.search(oplogSearch);
	}
}
