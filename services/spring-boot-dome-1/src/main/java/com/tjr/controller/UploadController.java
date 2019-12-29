package com.tjr.controller;

import com.alibaba.fastjson.JSONObject;
import com.tjr.common.GlobalProperties;
import com.tjr.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file")
@Api(value = "/file", description = "文件")
public class UploadController extends BaseController {
	@Autowired
	private GlobalProperties configProperties;

	@PostMapping("/upload")
	@ApiOperation(value = "上传单个文件",httpMethod ="POST")
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

}
