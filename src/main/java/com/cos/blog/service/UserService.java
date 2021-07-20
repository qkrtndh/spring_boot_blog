package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

	@Transactional // 어노테이션이 붙어있는 전체가 성공해야 commit 됨
	public int 회원가입(User user) {
		String rawPassword = user.getPassword();// 비밀번호 원문
		String encPassword = encode.encode(rawPassword);// 해쉬화
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

	@Transactional
	public void 회원수정(User user) {
		// 수정시에는 영속성 컨테스트 User오브젝트를 영속화시키고 영속화된 오브젝트를 수정한다.
		// select를 해서 user오브젝트를 db로부터 가져와서 영속화한다.
		User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
			return new IllegalArgumentException("회원찾기 실패");
		});
		// 중복 닉네임 체크
		User temp = userRepository.findByNickname(user.getNickname());
		if (temp!=null && temp.getId()!=user.getId())
		{
			throw new IllegalArgumentException("중복 회원");
		}

		persistance.setNickname(user.getNickname());

		// validate체크
		if (persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encode.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}

		// 회원 수정 함수 종료 시점 = 서비스 종료시점 = 트랜잭션 종료 = commit 됨
		// 영속화된 persistance 객체의 변화가 발생하면 더티체킹이 되어 변화를 반영함(update문 을 자동으로)
	}

	@Transactional(readOnly = true)
	public User 회원찾기(String username) {
		User user = userRepository.findByUsername(username).orElseGet(() -> {
			return new User();
		});
		return user;
	}
	/*
	 * @Transactional(readOnly = true) //select 시 트랜젝션 시작, 서비스 종료시 트랜잭션 종료(정합성 유지)
	 * public User 로그인(User user) { return
	 * userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword(
	 * )); }
	 */
}
