package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//controller는 file을 리턴
@Controller
public class TempControllerTest {
	//http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome");
		
		//파일리턴 기본경로 : src/main/resources/static
		//+return 에서 찾는다.
		//src/main/resources/static/home.html
		
		//springboot는 기본적으로 jsp지원 x
		//기본경로인 static은 정적파일만 동작함.	
		
		//jsp파일 경로
		//main/src/webapp/WEB-INF/views
		return "/home.html";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		//springboot는 기본적으로 jsp지원 x
		//기본경로인 static은 정적파일만 동작함.	
		System.out.println("@");
		//jsp파일 경로
		//main/src/webapp/WEB-INF/views
		return "test";
	}
}
