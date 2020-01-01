package com.lianghongji.manageapi.controller.v1;

import com.lianghongji.ErrorCode;
import com.lianghongji.controller.PageCommResult;
import com.lianghongji.manageuser.service.ManageUserService;
import com.lianghongji.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 检查管理后台用户是否有权限访问相关路径
 * 
 * @author  liang.hongji
 *
 */
public class AccessFilter implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(AccessFilter.class);

	private static final String TOKEN =  "ManageUserAccessToken";
	
	@Autowired
	private ManageUserService manageUserService;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest htRequest = (HttpServletRequest) request;
		String token = htRequest.getHeader(TOKEN);
		HttpSession session = htRequest.getSession();
		if(StringUtils.isBlank(token)){
			sendErrorResult(response, ErrorCode.USER_NOT_LOGNIN);
			return;
		}
		long userId = manageUserService.findUserIdByToken(token);
		if( userId  <= 0){
			sendErrorResult(response, ErrorCode.USER_NOT_LOGNIN);
			return;
		}
		String path = htRequest.getServletPath();
		LOG.info(path);
		if(manageUserService.checkAccess(path, userId)){
			chain.doFilter(request, response);
		}else{
			LOG.info(" user {} access path {} no right ", userId, path);
			sendErrorResult(response, ErrorCode.NO_RIGHT);
		}
	}
	
	private void sendErrorResult(ServletResponse resp, ErrorCode errorCode) throws IOException{
		PageCommResult<String> failResult = PageCommResult.failResult();
		failResult.setMsg(errorCode.getMessage());
		failResult.setResultCode(errorCode.getCode());
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=UTF-8");
		resp.getWriter().write(JsonUtils.toJson(failResult));
	}
	
	@Override
	public void destroy() {
	}
}
