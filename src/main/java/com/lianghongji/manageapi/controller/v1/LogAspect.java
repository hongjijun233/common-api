package com.lianghongji.manageapi.controller.v1;

import com.lianghongji.manageuser.entity.ManageUser;
import com.lianghongji.manageuser.service.ManageUserService;
import com.lianghongji.oplog.entity.OperationLog;
import com.lianghongji.oplog.service.OplogService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

/**
 * 日志切面类
 *
 * @author  liang.hongji
 */
@Aspect
@Component
@Order(1)
public class LogAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    private static final String TOKEN =  "ManageUserAccessToken";
    
    @Autowired
    private ManageUserService manageUserService;
    
    @Autowired
    private OplogService oplogService;

    @Pointcut("execution(public * com.lianghongji.manageapi.controller.v1.manageuser..*.*(..))")
    private void controllerAspect(){
    }

    @Before(value = "controllerAspect()")
    public void methodBefore(JoinPoint joinPoint){
        getAndRecordAccess(joinPoint);
    }

    private void getAndRecordAccess(JoinPoint joinPoint){
        OperationLog operationLog = new OperationLog();
        getAccessInfo(joinPoint, operationLog);
        oplogService.save(operationLog);
    }

    private void getAccessInfo(JoinPoint joinPoint, OperationLog operationLog){
        setAccessPath(joinPoint,operationLog);
        setAccessUserName(operationLog);
        operationLog.setOperationParam(Arrays.toString(joinPoint.getArgs()));
        operationLog.setOperationTime(new Date());
    }

    private void setAccessPath(JoinPoint joinPoint, OperationLog operationLog){
        String accessRootUrl = getAccessRootUrl(joinPoint);
        String accessMethodUrl = getAccessMethodUrl(joinPoint);
        operationLog.setOperationPath(accessRootUrl + accessMethodUrl);
    }

    private String getAccessRootUrl(JoinPoint joinPoint){
        Class<?> accessClass = joinPoint.getTarget().getClass();
        if(accessClass.isAnnotationPresent(RequestMapping.class)){
            RequestMapping req = (RequestMapping) accessClass.getAnnotation(RequestMapping.class);
            return req.value()[0];
        }
        return "";
    }

    private String getAccessMethodUrl(JoinPoint joinPoint){
        MethodSignature accessMethodSignature = (MethodSignature) joinPoint.getSignature();
        Method accessMethod = accessMethodSignature.getMethod();
        if (accessMethod.isAnnotationPresent(RequestMapping.class)){
            RequestMapping req = accessMethod.getAnnotation(RequestMapping.class);
            return req.value()[0];
        }
        return "";
    }

    private void setAccessUserName(OperationLog operationLog){
        String name = getAccessUserName();
        operationLog.setUsername(name);
    }

    private String getAccessUserName(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader(TOKEN);
        String name = null;
        if(!StringUtils.isBlank(token)){
            Long userId = manageUserService.findUserIdByToken(token);
            ManageUser user = manageUserService.find(userId);
            if(user != null){
                name = user.getUserName();
            } else {
                name = "未授权";
            }
        }
        return name;
    }
    
    @AfterReturning(returning = "o",pointcut = "controllerAspect()")
    public void methodAfterReturing(Object o ){
    }
}
