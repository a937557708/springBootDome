package com.tjr.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebAppConfig extends WebMvcConfigurationSupport {
	private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	@Autowired
	private Environment environment;

	@Value("${config.tjr.datajson.uploadFile}")
	private String uploadFile;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (uploadFile.equals("") || uploadFile.equals("${config.tjr.datajson.uploadFile}")) {
			String filePath = WebAppConfig.class.getClassLoader().getResource("").getPath();
			if (filePath.indexOf(".jar") > 0) {
				filePath = filePath.substring(0, filePath.indexOf(".jar"));
			} else if (filePath.indexOf("classes") > 0) {
				filePath = "file:" + filePath.substring(0, filePath.indexOf("classes"));
			}
			filePath = filePath.substring(0, filePath.lastIndexOf("/")) + "/file/";
			uploadFile = filePath;
		}
		String contextPath = environment.getProperty("server.servlet.context-path");
		logger.info("server.servlet.context-path:{}", contextPath);
		logger.info("magesPath=============:{}", uploadFile);
		registry.addResourceHandler("/uploadFile/**").addResourceLocations(uploadFile);
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		super.addResourceHandlers(registry);
	}
}
