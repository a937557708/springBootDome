package com.tjr.base.controller;

import com.alibaba.fastjson.JSONArray;
import com.tjr.base.annotation.NoRepeatSubmit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping
public class BaseController {


    @RequestMapping("/test")
    @NoRepeatSubmit
    public String tt(HttpServletRequest request) {

        return "1";
    }
}
