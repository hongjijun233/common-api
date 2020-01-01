package com.lianghongji;

import com.lianghongji.Exception.CommonErrorCode;

/**
 * 后台错误码
 *
 * @author liang.hongji
 */
public class ErrorCode extends CommonErrorCode {

    public static final ErrorCode USER_NOT_FOUND = new ErrorCode(1000, "用户不存在或密码错误");
    public static final ErrorCode PASSWORD_INCONSISTENT = new ErrorCode(1001, "两次输入密码不一致");
    public static final ErrorCode INVAILD_TYPE = new ErrorCode(1002, "注册类型非法");
    public static final ErrorCode EXSIST_USER = new ErrorCode(1003, "用户已存在");
    public static final ErrorCode USER_NOT_LOGNIN = new ErrorCode(1004, "用户未登录或登录已过期");
    public static final ErrorCode ROLE_NOT_FOUND = new ErrorCode(1005, "角色不存在");
    public static final ErrorCode ROLE_EXITS = new ErrorCode(1006, "角色已存在");
    public static final ErrorCode RESOURCE_NOT_FOUND = new ErrorCode(1007, "资源不存在");
    public static final ErrorCode NO_RIGHT = new ErrorCode(1008, "没有访问权限");


    public ErrorCode(int code, String message) {
        super(code, message);
    }
}
