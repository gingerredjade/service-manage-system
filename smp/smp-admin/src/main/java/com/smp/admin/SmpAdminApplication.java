package com.smp.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 应用启动入口类
 *
 * @since 1.0.0 2019年09月08日
 * @author <a href="https://gisnci.com">Hongyu Jiang</a>
 */
@EnableAspectJAutoProxy // Spring启动时就会去扫描AOP相关的标注，在创建对象时帮我们去执行织入过程！
@SpringBootApplication(scanBasePackages = {"com.smp.admin"})
//@EnableDiscoveryClient
@EnableTransactionManagement
public class SmpAdminApplication {
	private  static Logger logger = LoggerFactory.getLogger(SmpAdminApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SmpAdminApplication.class, args);
		logger.info("[SmpAdminApplication微服务] 已启动");
	}

}
