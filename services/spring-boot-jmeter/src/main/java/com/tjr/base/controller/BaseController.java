package com.tjr.base.controller;

import com.alibaba.fastjson.JSONArray;
import com.tjr.utils.JmeterUntil;
import com.tjr.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;
@RestController
@RequestMapping
public class BaseController {


    @GetMapping("/jmx")
    public JSONArray testJpa() throws IOException {
        JSONArray jsonArray=  JmeterUntil.getTreeList("D:/java/apache-jmeter-5.3","D:\\java\\HTTP请求.jmx");;
        return jsonArray;
    }
}
