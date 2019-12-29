package com.tjr.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.tjr.utils.RandomServerPort;

import jodd.util.StringUtil;

/**
 * 改变端口
 * 
 * @author matebook
 *
 */
@Component
public class CustomContainer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

	@Autowired
	private Environment environment;

	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		String serverPort=environment.getProperty("server.port");
		if(StringUtil.isEmpty(serverPort)) {
			factory.setPort(new RandomServerPort().nextValue(8086, 8099));
		}
	}

}