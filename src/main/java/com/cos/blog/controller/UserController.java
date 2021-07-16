package com.cos.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//인증이 안된 사용자는 /auth 이하의 경로만 가능
// "/"인 주소면 index.jsp 허용
//static 폴더 이하에 있는 /js/88, /css/88, /image/** 허용
@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {

		return "user/joinForm";
	}
	
	@Value("${cos.key}")
	private String cosKey;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@GetMapping("/auth/loginForm")
	public String loginForm() {

		return "user/loginForm";
	}

	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}

	@GetMapping("/auth/kakao/callback")
	public String kakaocallback(String code) {// DATA를 리턴하는 컨트롤러 함수가 된다.
		// POST 방식으로 key=value 데이터를 요청(카카오 쪽으로)
		// RestTemplate 라는 라이브러리를 이용
		RestTemplate rt = new RestTemplate();

		// http 헤더 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// body 데이터를 담을 obj
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "c30fbe4a62f92d5249981e1c2bbd8787");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);

		// 헤더와 바디값을 가진 엔티티
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// 요청,response 변수의 응답을 받음
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoTokenRequest, String.class);
		// json오브젝트를 받을 수 있는 라이브러리들 Gson, Json Simple,ObjectMapper.....
		ObjectMapper obMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = obMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("카카오 엑세스 토큰:" + oauthToken.getAccess_token());

		RestTemplate rt2 = new RestTemplate();

		// http 헤더 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// 헤더와 바디값을 가진 엔티티
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

		// 요청,response 변수의 응답을 받음
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kakaoProfileRequest, String.class);

		ObjectMapper obMapper2 = new ObjectMapper();
		KakaoProfile kakaoprofile = null;
		try {
			kakaoprofile = obMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("아이디 : " + kakaoprofile.getId());
		System.out.println("이메일 : " + kakaoprofile.getKakao_account().getEmail());

		System.out.println("블로그서버 유저네임:" + kakaoprofile.getKakao_account().getEmail() + "_" + kakaoprofile.getId());
		System.out.println("블로그서버 이메일:" + kakaoprofile.getKakao_account().getEmail());
		
		System.out.println("블로그서버 패스워드 : " + cosKey);
		User kakaouser = User.builder()
				.username(kakaoprofile.getKakao_account().getEmail() + "_" + kakaoprofile.getId())
				.password(cosKey).email(kakaoprofile.getKakao_account().getEmail()).oauth("kakao").build();
		// 가입자 비 가입자 처리
		User originuser = userService.회원찾기(kakaouser.getUsername());
		if (originuser.getUsername() == null) {
			userService.회원가입(kakaouser);
		}
		//로그인처리
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(kakaouser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}
}
