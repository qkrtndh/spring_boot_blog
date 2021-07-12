package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encode;
	
	@Transactional//어노테이션이 붙어있는 전체가 성공해야 commit 됨
	public int 회원가입(User user) {
		String rawPassword = user.getPassword();//비밀번호 원문
		String encPassword = encode.encode(rawPassword);//해쉬화
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		try {
			userRepository.save(user);
			return 1;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return -1;
	}
	
	/*@Transactional(readOnly = true) //select 시 트랜젝션 시작, 서비스 종료시 트랜잭션 종료(정합성 유지)
	public User 로그인(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
	}*/
}
