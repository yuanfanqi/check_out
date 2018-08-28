package com.checkOut.common.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
 * kaptcha验证码配置类
 * @author raymee E-mail:leohaoo@vip.qq.com
 * @date 创建时间：2017年5月23日 下午8:39:28
 * @version 1.0
 * @since
 */
@Component(value="producer")
public class KaptchaConfig{

	@Bean
	public DefaultKaptcha getDefaultKaptcha() {

		com.google.code.kaptcha.impl.DefaultKaptcha defaultKaptcha = new com.google.code.kaptcha.impl.DefaultKaptcha();
		Properties properties = new Properties();
		properties.setProperty("kaptcha.border", "no");
		properties.setProperty("kaptcha.textproducer.font.color", "black");
		properties.setProperty("kaptcha.textproducer.char.length", "5");
		Config config = new Config(properties);
		defaultKaptcha.setConfig(config);

		return defaultKaptcha;
	}

}
