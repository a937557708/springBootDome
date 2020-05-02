package com.tjr.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.tjr.common.GlobalProperties;
import com.tjr.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.io.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file")
@Api(value = "/file", description = "文件")
public class UploadController extends com.tjr.base.controller.BaseController {
    @Autowired
    private GlobalProperties configProperties;

    @PostMapping("/upload")
    @ApiOperation(value = "上传单个文件", httpMethod = "POST")
    public ResponseUtils singleFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return ResponseUtils.errorMsg(this.getMessage("file.upload.isNotNull", request));
        }
        try {
            JSONObject datajson = configProperties.getDatajson();
            String saveFile = datajson.getString("saveFile");
            byte[] bytes = file.getBytes();
            Path path = Paths.get(saveFile + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            return ResponseUtils.errorException(this.getMessage("file.upload.error", request));
        }

        return ResponseUtils.ok(this.getMessage("file.upload.success", request));
    }
//	/**
//	 * 上传文件
//	 */
//	@RequestMapping(method = RequestMethod.POST, path = "/upload")
//	@ResponseBody
//	public String upload(@RequestPart("file") MultipartFile file) {
//		try {
//			// 得到上传时的文件名
//			String originalFilename = file.getOriginalFilename();
//			JSONObject datajson = configProperties.getDatajson();
//			String saveFile = datajson.getString("saveFile");
//			file.transferTo(new File(saveFile + originalFilename));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "上传失败";
//		}
//		return "上传成功";
//	}

    /**
     * 下载文件
     */
    @RequestMapping("/{fileName}/{type}")
    public void renderPicture(@PathVariable("fileName") String fileName, @PathVariable("type") String type, HttpServletResponse response) {
        try {
            JSONObject datajson = configProperties.getDatajson();
            String saveFile = datajson.getString("saveFile");
            byte[] bytes = FileUtil.readBytes(saveFile + File.separator + fileName + "." + type);
			response.setContentType("application/force-download;fileName=" + fileName+ "." + type);// 设置强制下载不打开            
            response.getOutputStream().write(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
