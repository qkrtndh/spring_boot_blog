package com.cos.blog.test;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired//의존성 주입(DI)
	private UserRepository userRepository;
	
	//{id}주소로 파라미터 전달 받을 수 있음
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//리턴 타입이 optional. 못찾은 경우 null이 반환되면 문제가 생길 수 있으므로 optional로 감싸서 반환하여 null인지 판단하도록 함
		//.get은 판단없이 바로 객채를 꺼냄 .orElseGet()하면 null일때 임의로 생성하여 빈 객체를 반환함. supplier를 사용하며 인터페이스로 get을 overriding 하여 구현
		//이하의 방법은 없으면 에러를 발생시킬 수 있음.
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				
				return new IllegalArgumentException("해당 유저는 없습니다. id: "+id);
			}
		});
		//@RestController는 html이 아닌 데이터(text를 리턴)
		//웹 브라우저에서 요청하여 user객체를 리턴하는데, 브라우저에선 객체를 인식할 수 없음.
		//따라서 json형식으로 변환이 필요한데, 스프링부트에서 자동으로 변환해준다.
		return user;
	}
	
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
