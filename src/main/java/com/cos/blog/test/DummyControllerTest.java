package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired//의존성 주입(DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch (Exception e) {
			return "존재하지 않는 아이디";
		}
		
		return id+"삭제";
	}
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	//Json 데이터를 요청했는데 스프링이 java오브젝트로 변환해서 받아줌 @RequestBody 가 하는일
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id : "+id);
		System.out.println("password : "+requestUser.getPassword());
		System.out.println("email : "+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정실패");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(requestUser);
		//더티체킹
		return null;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한 페이지당 2건의 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2,sort="id",direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		List<User> users = pagingUser.getContent();
		return users;
	}
	
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
