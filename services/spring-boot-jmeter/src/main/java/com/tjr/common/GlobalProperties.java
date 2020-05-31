package com.tjr.common;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取属性文件
 * @author matebook
 *
 */
@Data
@Component
@ConfigurationProperties(prefix = "config.tjr")
@PropertySource(value = "classpath:global.properties", encoding = "UTF-8") 
public class GlobalProperties {
	private JSONObject datajson;
}