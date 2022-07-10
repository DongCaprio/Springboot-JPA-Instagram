package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity //해당 파일로 시큐리티를 활성화
@Configuration 		//IOC     
public class SecurityConfig extends WebSecurityConfigurerAdapter{ //설정파일설정 extends
	
	@Bean //@Bean을 해주면 위에 @Configuration때문에 SecurityConfig클래스가 IOC에 담길때 @Bean어노테이션을 읽어서 이 메소드의 리턴값을 IOC가 들고있게 된다 
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super.configure(http); 이거때문에 springsecurity 로그인 페이지 걸린다
		// 위에꺼 삭제하면 기존 시큐리티가 가지고 있는 기능이 다 비활성화된다.
		
		http.csrf().disable(); //csrf 비활성화
		
		http.authorizeRequests()
		.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**","/api/**").authenticated() //이것들만 인증필요
		.anyRequest().permitAll() //나머지는 다 허용하겠다.
		.and()
		.formLogin() //맨줄의 막혀있는거 들어오면 로그인 페이지를 거치게 하겠다.
		.loginPage("/auth/signin") //그 로그인 페이지가 바로 " "안의 값이다 //얘는 GET //얘는 로그인창화면
		.loginProcessingUrl("/auth/signin") //POST //얘는 로그인창화면에서 로그인클릭시 POST로 진행되는 과정. 즉 위와 이건 다른것임
		.defaultSuccessUrl("/"); //로그인 성공하면 일로간다
	}
}
