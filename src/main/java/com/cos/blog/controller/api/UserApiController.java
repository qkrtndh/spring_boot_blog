package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/auth/joinProc")
	public  ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("회원가입");
		int result = userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),result);		
	}
	@PutMapping("/user")
	public  ResponseDto<Integer> update(@RequestBody User user) {
		System.out.println("회원수정");
		userService.회원수정(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);		
	}
	//스프링 시큐리티가 화면을 가로채서 로그인을 유도한다. 이때 비밀번호는 콘솔에 뜨며 아이디는 user.
	//세션은 자동으로 생성된다.
	
	/*
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user,HttpSession session){
		System.out.println("login 호출");
		User principal = userService.로그인(user);
		if(principal != null) {
			session.setAttribute("principal", principal);
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}*/
}
