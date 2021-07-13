package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class PrincipalDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	// 스프링이 로그인 요청을 가로챌 때 username,password 변수를 가로챈다. 
	//password처리는 알아서 함. username이  db에 있는지 확인해주면 된다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("sdasd@"+username);
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()->{
						return new UsernameNotFoundException("사용자 없음 :"+username);
					});
		return new PrincipalDetail(principal);//시큐리티 세션에 유저정보가 저장이 됨.
	}
	
}
