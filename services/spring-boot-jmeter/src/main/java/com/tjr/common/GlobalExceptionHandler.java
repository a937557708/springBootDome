package com.tjr.common;

import com.tjr.base.controller.BaseController;
import com.tjr.utils.ResponseUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常
 * 
 * @author matebook
 *
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler extends BaseController {
	@ExceptionHandler(value = Exception.class)
	public ResponseUtils exceptionHandler(HttpServletRequest request, Exception ex) {
		if (ex instanceof UnauthenticatedException) {
			return ResponseUtils.errorException(this.getMessage("shiro.error", request));
		} else if (ex instanceof UnauthorizedException) {
			return ResponseUtils.errorException(this.getMessage("shiro.error", request));
		}
		ex.printStackTrace();
		return ResponseUtils.errorException(this.getMessage("execption.error", request));
	}

	@ExceptionHandler(value = MultipartException.class)
	public ResponseUtils multipartExceptionHandler(HttpServletRequest request, MultipartException e) {
		e.printStackTrace();
		return ResponseUtils.errorException(this.getMessage("file.upload.error", request));
	}

}
