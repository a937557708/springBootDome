package com.tjr.common.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.aspectj.bridge.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.tjr.utils.ResponseUtils;

import jodd.util.StringUtil;

public class ShiroPermissionsFilter extends FormAuthenticationFilter {

	private static final Logger logger = LoggerFactory.getLogger(ShiroPermissionsFilter.class);

	@Override
	protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String username = request.getHeader("username");
		String uri=request.getServletPath();
		if (!isAjax(request)) {
			return true;
		}
		if (StringUtil.isNotEmpty(username)) {
			return true;
		}
		return false;
	}

	public boolean isAjax(HttpServletRequest request) {
		return (request.getHeader("X-Requested-With") != null
				&& "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
	}

	/**
	 * shiro认证perms资源失败后回调方法
	 * 
	 * @param servletRequest
	 * @param servletResponse
	 * @return
	 * @throws IOException
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse)
			throws IOException {
		logger.info("----------权限控制-------------");
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (!isAjax(request)) {
			return true;
		}
		String username = request.getHeader("username");
		try {
			Subject subject = getSubject(servletRequest, response);
			UsernamePasswordToken token = new UsernamePasswordToken(username, username, true);
			subject.login(token);
		} catch (AuthenticationException e) {
			return false;
		}
		return true;
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("application/json;charset=UTF-8");
//		ResponseUtils responseUtils = ResponseUtils.errorMsg(this.getMessage("permission.verifiy.failed", request));
//		response.getWriter().write(JSONObject.toJSONString(responseUtils));
//		response.getWriter().flush();
	}
}
