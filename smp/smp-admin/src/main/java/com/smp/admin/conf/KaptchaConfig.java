package com.smp.admin.conf;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码配置
 * 	配置验证码的一些生成属性
 *
 * 	防止广告机注册等操作
 * 	防止暴力破解
 *
 * @author Hongyu Jiang
 * @since  Jul. 3 2020
 */
@Configuration
public class KaptchaConfig {

	@Bean
	public DefaultKaptcha producer() {
		Properties properties = new Properties();
		properties.put("kaptcha.border", "no");
		properties.put("kaptcha.textproducer.font.color", "black");
		properties.put("kaptcha.textproducer.char.space", "5");

		/*properties.put("kaptcha.border", "yes");
		properties.put("kaptcha.border.color", "105,179,90");
		properties.put("kaptcha.textproducer.font.color", "blue");
		properties.put("kaptcha.textproducer.char.space", "5");
		properties.put("kaptcha.textproducer.font.size", "27");
		properties.put("kaptcha.textproducer.char.length", "6");
		properties.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
		properties.put("kaptcha.noise.color", "black");
		properties.put("kaptcha.background.clear.from", "185,56,213");
		properties.put("kaptcha.background.clear.to", "white");
		properties.put("kaptcha.session.key", "code");
		properties.put("kaptcha.textproducer.char.string", "0123456789ABCEFGHIJKLMNOPQRSTUVWXYZ");
		properties.put("kaptcha.image.width", "100");
		properties.put("kaptcha.image.height", "50");*/

		Config config = new Config(properties);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}

}
