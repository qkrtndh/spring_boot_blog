package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//DAO와 같음. 하지만 JpaReopsitory 에 대부분의 기능이 있으므로 CRUD 구현은 안해도 됨.
//자동으로 Bean 등록이 되므로
//@Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer>{
	
}
