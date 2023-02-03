package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer { // web 설정 파일

	@Value("${file.path}")
	private String uploadFolder; // 여기서 uploadFolder는 yml파일에 내가 적은 file 경로
	
	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			WebMvcConfigurer.super.addResourceHandlers(registry);
			
			// file:///C:/workspace/springbootwork/photogram_upload/
			registry
			.addResourceHandler("/upload/**") // jsp페이지에서 /upload/** 이런 주소 패턴이 나오면 발동
			.addResourceLocations("file:///"+uploadFolder)
			.setCachePeriod(60*10*6) // 초단위 / 60*10*6 = 1시간
			.resourceChain(true)
			.addResolver(new PathResourceResolver());
		}
}
