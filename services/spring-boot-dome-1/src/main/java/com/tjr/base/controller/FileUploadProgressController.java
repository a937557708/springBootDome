package com.tjr.base.controller;


import com.tjr.utils.ResponseUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/progress")
public class FileUploadProgressController {
    /**
     * 获取实时长传进度
     *
     * @param request
     * @return int percent
     */
    @GetMapping("getPercent")
    public ResponseUtils getUploadPercent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int percent = session.getAttribute("upload_percent") == null ? 0 : (int) session.getAttribute("upload_percent");
        return ResponseUtils.ok(percent);
    }


    /**
     * 重置上传进度 前端调用进度之前调用此接口
     *
     * @param request
     * @return void
     */
    @PostMapping("resetPercent")
    public ResponseUtils resetPercent(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("upload_percent", 0);
        return ResponseUtils.ok();
    }


}
