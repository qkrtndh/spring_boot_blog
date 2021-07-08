package com.cos.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired//의존성 주입(DI)
	private UserRepository userRepository;
	
	@PostMapping("/dummy/join")
	//본래는 @RequestParam("username") Stirng u 이런식으로 했으나, 명확하게 인자명을 기입한 경우
	//http body에 데이터를 가지고 요청하게 되면 파라미터가 인자로 들어가게된다.
	//public String join(String username, String password, String email) {
	public String join(User user) {//오브젝트로 받아올 수 있으나 입력되지 않은 부분에 대해선 값을 가지고 있지 않다.
		System.out.println("username: "+user.getUsername());
		System.out.println("password: "+user.getPassword());
		System.out.println("email: "+user.getEmail());
		
		//이렇게 해서 어노테이션을 늘리지 않고 가입시에 초기값을 정할 수 있다.
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입 완료";
	}
}
