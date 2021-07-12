package com.cos.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity//시큐리티 필터가 등록됨 = 스프링 시큐리티 설정을 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true)//특정주소로 접근하면 권한 및 인증을 미리 체크한다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//세션생성 후 request요청이 들어오면, loginform이나 joinform을 통한 요청일 경우 permit 한다
		http
			.authorizeRequests()
				.antMatchers("/auth/**")
				.permitAll()
				.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/auth/loginForm");
		//다른 모든 요청은 인증되어야 함
	}
}
