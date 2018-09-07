package com.checkOut.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public abstract class ExceptionController {

	/**
	 * 权限不足 异常处理
	 * @return
	 */
	@ExceptionHandler(AuthorizationException.class)
	@ResponseBody()
	public Map<String,Object> authorizationException() {
            Map<String,Object> map = new HashMap<>();
            map.put("code", "-998");
            map.put("msg", "当前用户暂无该权限");
            return map;
    }
}
