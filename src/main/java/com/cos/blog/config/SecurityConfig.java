package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

@Configuration
@EnableWebSecurity//시큐리티 필터가 등록됨 = 스프링 시큐리티 설정을 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled = true)//특정주소로 접근하면 권한 및 인증을 미리 체크한다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 비밀번호를 중간에 가져가서 암호화하는데, 해당 패스워드가 어떻게 해시화되었는지 알아야 DB의 비밀번호와 비교할 수 있다.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//세션생성 후 request요청이 들어오면, loginform이나 joinform을 통한 요청일 경우 permit 한다
		http
			.csrf().disable()//csrf 토큰 비활성화
			.authorizeRequests()
				.antMatchers("/auth/**","/js/**","/css/**","/image/**","/")
				.permitAll()
				.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc")
				.defaultSuccessUrl("/");//스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인을 진행한다.
		//다른 모든 요청은 인증되어야 함
	}
}
