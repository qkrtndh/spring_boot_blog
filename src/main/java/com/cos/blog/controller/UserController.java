package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//인증이 안된 사용자는 /auth 이하의 경로만 가능
// "/"인 주소면 index.jsp 허용
//static 폴더 이하에 있는 /js/88, /css/88, /image/** 허용
@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		
		return "user/loginForm";
	}
}
