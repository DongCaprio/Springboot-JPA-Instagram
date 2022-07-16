package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{ //web설정파일
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		//밑에꺼 뜻이 내가 앞에 upload만 붙혀서 업로드를 하려고 하면 알아서 yml의 C:/dongj_workspace/springbootwork/upload/ 이걸로 업로드주소가 변경된다는것이다
		registry.addResourceHandler("/upload/**") //jsp페이지에서 /upload/** 이런 주소 패턴이 나오면 발동
		.addResourceLocations("file:///"+uploadFolder)
		.setCachePeriod(60*10*6) //초단위, 1시간 캐싱
		.resourceChain(true)
		.addResolver(new PathResourceResolver());
	}
	
	//이건 profile.jsp 같은 곳에서 파일 upload할때 등등 사용하기 위해서 이 클래스를 생성한것!
	//궁금하면 /upload/ 를 쓰는 곳을 찾아라!
	//        /upload/쓰면 C:/dongj_workspace/springbootwork/upload/ 로 바꿔줌
}
