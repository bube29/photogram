package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity  // 3. 해당 파일로 시큐리티를 활성화
@Configuration // 2. IoC 등록하여 메모리에 떠야한다.   
public class SecurityConfig extends WebSecurityConfigurerAdapter{ // 1. 해당 클래스에 WebSecurityConfigurerAdapter 상속

	@Bean // SecurityConfig가 IoC에 등록될때 @Bean 어노테이션을 읽어서 return을 해서 IoC가 BCryptPasswordEncoder를 가지고 있다.
	public BCryptPasswordEncoder encode() { // 패스워드 암호화
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// super.configure(http); 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화됨.
		http.csrf().disable(); // CSRF 토큰 비활성화
		http.authorizeRequests()
			.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**","/api/**").authenticated()
			//.antMatchers <- ( )의 해당 경로(인증이 필요한 페이지)로 들어올 경우.. .authenticated() <- 인증이 필요해..
			.anyRequest().permitAll()
			// .anyRequest() <- 그게 아닌 모든 요청은.. permitAll() <- 허용하겠다.
			.and()
			.formLogin() // .antMatchers().authenticated()의 주소를 요청하면 .formLogin() 하겠다. 
			.loginPage("/auth/signin") // .formLogin() 할 페이지가 .loginPage의 ("/auth/signin")로 가게 하겠다는 것. // GET / 인증이 필요한 페이지를 요청하면.. SecurityConfig가 GET방식으로 이쪽을 호출.
			.loginProcessingUrl("/auth/signin") // POST / POST 방식으로 요청하면 Spring Security가 로그인 프로세스를 진행하다.
			.defaultSuccessUrl("/"); // 로그인이 정상적으로 처리되면 가게될 경로
	}
}
