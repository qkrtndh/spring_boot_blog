package com.cos.blog.repository;

import java.io.Console;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//DAO와 같음. 하지만 JpaReopsitory 에 대부분의 기능이 있으므로 CRUD 구현은 안해도 됨.
//자동으로 Bean 등록이 되므로
//@Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer>{
	//select * from user where username=?
		Optional<User> findByUsername(String username);
}
//JPA naming 전략
	//이하의 함수명은
	//select * from user where username=? and password=?; 와 같다.
	//User findByUsernameAndPassword(String username, String password);
	
	//이것과 같은 기능을 한다.
	//@Query(value="select * from user where username=? and password=?",nativeQuery = true)
	//User login(String username,String password);