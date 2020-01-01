package com.lianghongji.manageapi.controller.v1;

import com.lianghongji.ErrorCode;
import com.lianghongji.Exception.BusinessRuntimeException;
import com.lianghongji.controller.AbstractController;
import com.lianghongji.controller.CommonResult;
import com.lianghongji.manageuser.dto.req.ManageLoginDto;
import com.lianghongji.manageuser.dto.req.ManageUserSighUpDto;
import com.lianghongji.manageuser.dto.resp.ManageLoginResultDto;
import com.lianghongji.manageuser.service.ManageUserService;
import com.lianghongji.util.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 后台管理通用接口
 *
 * @author liang.hongji
 */
@Api(description = "后台管理系统通用接口")
@RestController
@RequestMapping("/v1/manageApi")
public class ManageController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(ManageController.class);

    @Autowired
    HttpServletRequest request;

    @Autowired
    ManageUserService manageUserService;

    @ApiOperation(value = "管理用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult<ManageLoginResultDto> login(@Valid @RequestBody ManageLoginDto manageLoginDto,
                                                    BindingResult bindingResult){
        LOG.info(" access /v1/manageApi/login manageLoginDto {} ", manageLoginDto);
        if (bindingResult.hasErrors()) {
            throw new BusinessRuntimeException(ErrorCode.PRAMERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        String ip = IpUtils.getIpAddr(request);
        return  successResult(manageUserService.login(manageLoginDto, ip));
    }

    @ApiOperation(value = "注册")
    @RequestMapping(value = "/sighup", method = RequestMethod.POST)
    public CommonResult<ManageLoginResultDto> sighup(@Valid @RequestBody ManageUserSighUpDto manageUserSighUpDto,
                                                     BindingResult bindingResult){
        LOG.info(" access /v1/manageApi/sighup manageUserSighUpDto {} ", manageUserSighUpDto);
        if (bindingResult.hasErrors()) {
            throw new BusinessRuntimeException(ErrorCode.PRAMERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        return successResult(manageUserService.sighup(manageUserSighUpDto));
    }
}
