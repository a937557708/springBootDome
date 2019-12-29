package com.tjr.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

public class BaseController {
	@Autowired
	private MessageSource messageSource;

	public String getMessage(String code, @Nullable Object[] args, Locale locale) {
		return messageSource.getMessage(code, args, locale);
	}

	public String getMessage(String code) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(code, null, locale);
	}

	public String getMessage(String code, HttpServletRequest request) {
		String lang = request.getHeader("lang");
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		if ("en_US".equals(lang)) {
			localeResolver.setLocale(request, null, Locale.US);
		} else {
			localeResolver.setLocale(request, null, Locale.CHINA);
		}
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(code, null, locale);
	}
}
