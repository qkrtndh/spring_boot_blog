package com.cos.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

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
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaocallback(String code) {//DATA를 리턴하는 컨트롤러 함수가 된다.
		//POST 방식으로 key=value 데이터를 요청(카카오 쪽으로)
		//RestTemplate 라는 라이브러리를 이용
		RestTemplate rt = new RestTemplate();
		
		//http 헤더 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
		
		//body 데이터를 담을 obj
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id","c30fbe4a62f92d5249981e1c2bbd8787");
		params.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
		params.add("code",code);
		
		//헤더와 바디값을 가진 엔티티
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params,headers);
		
		//요청,response 변수의 응답을 받음
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class);
		return "카카오 토큰 요청 완료 : 토큰응답 : "+response;
	}
}
