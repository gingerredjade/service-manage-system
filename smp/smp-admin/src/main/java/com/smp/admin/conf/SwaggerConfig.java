package com.smp.admin.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * SwaggerConfig配置
 * 实际上，为了形成一个完整的api文档，需要添加的注解常常很多，若是都写在同一个文件里就会显得臃肿
 * 我们常常会写一个接口文件，将注解都放在接口文件中，然后再用一个实体类来实现控制器，算是实现配置和逻辑分离了吧。
 * <p>
 * '@EnableSwagger2'用来启动Swagger支持，表示这是一个Spring Swagger的配置文件
 *
 * @author <a href="https://gisnci.com">Hongyu Jiang</a>
 * @since 1.0.0 2018年08月18日
 */
@Configuration      // 必须存在
@EnableSwagger2     // 必须存在
@ComponentScan(basePackages = {"com.smp.admin"})
//必须存在 扫描的API Controller package name 也可以直接扫描class (basePackageClasses)
public class SwaggerConfig extends WebMvcConfigurationSupport {

	/**
	 * UI页面显示信息
	 */
	private static String apiTitle = "服务管理平台";
	private static String apiDesc = "一体化战场环境服务管理平台API接口。";
	private static String apiVersion = "1.0.0";
	private static String basePackage = "com.smp.admin";

	@Value(value = "${preread.upload-path}")
	String uploadPath;

	/**
	 * 默认的swagger api页面
	 * @return
	 */
	/*@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo());
	}*/

	// 创建新的swagger api页面，暂不需要，使用默认的
//	@Bean
//	public Docket accessToken() {
//		return new Docket(DocumentationType.SWAGGER_2)
//			.directModelSubstitute(Byte.class, Integer.class)	// 修正Byte转string的Bug
//			.groupName("服务管理平台")   				// 定义组
//			.select()                               // 选择哪些路径和api会生成document
//			//.apis(RequestHandlerSelectors.any())  // 对所有api进行监控
//			.apis(RequestHandlerSelectors.basePackage(basePackage))
//			.paths(PathSelectors.any())             // 对所有路径进行监控
//			//.paths(regex("/api/.*"))              // 拦截的接口路径
//			.build() // 创建
//			.apiInfo(apiInfo());                    // 配置说明
//	}

	/**
	 * 创建新的swagger api页面，暂不需要，使用默认的
	 * 	在Swagger添加令牌参数。
	 * 		由于引入Spring Security安全框架，接口受到保护，需要携带合法的token令牌（一般是登录成功之后由后台返回）才能正常访问，
	 * 		但是swagger本身的接口测试页面默认并没有提供传送token参数的地方，因此需要简单定制一下，修改SwaggerConfig配置类即可。
	 *
	 * 		在Swagger测试页面测试接口时先将登录接口返回的token复制到对应参数，swagger在发送请求的时候会把token放入请求头。
	 * @return
	 */
	@Bean
	public Docket accessToken() {
		// 添加请求参数，我们这里把token作为请求头部参数传入后端
		ParameterBuilder parameterBuilder = new ParameterBuilder();
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameterBuilder.name("token").description("令牌")
			.modelRef(new ModelRef("string")).parameterType("header")
			.required(false).build();
		parameters.add(parameterBuilder.build());

		return new Docket(DocumentationType.SWAGGER_2)
			.directModelSubstitute(Byte.class, Integer.class)	// 修正Byte转string的Bug
			.groupName("服务管理平台")   				// 定义组
			.select()                               // 选择哪些路径和api会生成document
			//.apis(RequestHandlerSelectors.any())  // 对所有api进行监控
			.apis(RequestHandlerSelectors.basePackage(basePackage))
			.paths(PathSelectors.any())             // 对所有路径进行监控
			//.paths(regex("/api/.*"))              // 拦截的接口路径
			.build() // 创建
			.globalOperationParameters(parameters)	// 添加定制请求参数
			.apiInfo(apiInfo());                    // 配置说明
	}

	/**
	 * Spring中名字并不重要，重要的是它返回一个Docket类，DocumentationType.SWAGGER_2作为Docket构造方法的参数，指定了所用的swagger版本2.0，官网上已经在预告3.0版本了。
	 * 而之后的apiInfo则是调用接下来的apiInfo函数，来创建Docket的信息。
	 * apiInfo函数采用ApiInfoBuilder来创建ApiInfo类
	 *
	 * @return 返回值
	 */
	private static ApiInfo apiInfo() {
		//Contact contact = new Contact("gis", "http://www.gis.com/configcenter/", "gis@126.com");
		return new ApiInfoBuilder()
			.title(apiTitle + "-API")                            // 标题
			.description(apiDesc) // 描述
			.termsOfServiceUrl("https://spring.io/")             //
			//.contact(contact)                                  // 联系
			//.license("gis 1.0")                                // 开源协议
			//.licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE") // 地址
			.version(apiVersion)                                 // 版本
			.build();
	}

	/**
	 * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。
	 * 需要重新指定静态资源
	 *
	 * @param registry ResourceHandlerRegistry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		File path = null;
		try {
			path = new File(ResourceUtils.getURL("classpath:").getPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// 获取放项目jar的路径
		assert path != null;
		String gitPath = path.getParentFile().getParentFile().getParent() +
			File.separator + "show" + File.separator + "visual" + File.separator;

		// 映射外部文件
		registry.addResourceHandler("visual/**").addResourceLocations(gitPath);

		// 映射项目的静态资源
		registry.addResourceHandler("/**")
			.addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/")
			.addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/public/")
			.addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/resources/");
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/preview/**")
			.addResourceLocations("file:///" + uploadPath);
		super.addResourceHandlers(registry);
	}

	/**
	 * 解决中文乱码问题
	 *
	 * @return 返回值
	 */
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		return converter;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		converters.add(responseBodyConverter());
	}


}
