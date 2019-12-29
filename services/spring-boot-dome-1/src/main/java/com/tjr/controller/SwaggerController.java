package com.tjr.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tjr.common.ServerConfig;
import com.tjr.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/swagger")
@Api(value = "/Swagger", description = "Swagger请求")
public class SwaggerController extends BaseController {
	@Autowired
	private Environment environment;

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ServerConfig serverConfig;

	@RequiresPermissions(value = { "swagger.v2.api-docs" })
	@GetMapping("/v2/api-docs/{id}")
	@ApiOperation(value = "查看文档", httpMethod = "POST")
	public ResponseUtils getDocs(@PathVariable("id") String id, HttpServletRequest request) {
		String contextPath = environment.getProperty("server.servlet.context-path");
		int serverPort = serverConfig.getPort();
		ResponseEntity<String> entity = restTemplate
				.getForEntity("http://localhost:" + serverPort + contextPath + "/v2/api-docs", String.class);
		String body = entity.getBody();
		if (StringUtil.isEmpty(body)) {
			return ResponseUtils.errorMsg(this.getMessage("swagger.docs.nonull", request));
		}
		if (!JSONObject.isValid(body)) {
			return ResponseUtils.errorMsg(this.getMessage("swagger.docs.nonull", request));
		}

		JSONObject result = JSONObject.parseObject(body);
		JSONObject paths = result.getJSONObject("paths");
		JSONArray tags = result.getJSONArray("tags");

		JSONArray resultArr = new JSONArray();
		for (Object obj : tags) {
			JSONObject tag = (JSONObject) obj;
			JSONArray childrenList = new JSONArray();
			Set<String> pkey = paths.keySet();
			for (String key : pkey) {
				JSONObject menAllObj = paths.getJSONObject(key);
				Set<String> mkey = menAllObj.keySet();
				for (String key1 : mkey) {
					JSONObject menObj = menAllObj.getJSONObject(key1);
					List<String> tagList = (List<String>) menObj.get("tags");
					if (tagList.contains(tag.getString("name"))) {
						JSONObject rJson = new JSONObject();
						rJson.put("uri", key);
						rJson.put("method", key1);
						rJson.put("summary", menObj.get("summary"));
						rJson.put("parameters", menObj.get("parameters"));
						childrenList.add(rJson);
					}
				}
			}
			tag.put("children", childrenList);
			resultArr.add(tag);
		}
		return ResponseUtils.build(200, this.getMessage("swagger.docs", request), resultArr);
	}

}
