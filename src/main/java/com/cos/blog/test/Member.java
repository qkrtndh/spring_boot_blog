package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//getter와 setter 를 각각 생성하는 어노테이션
//@Getter
//@Setter

//getter,setter를 둘다 생성하는 어노테이션
@Data
//모든 변수를 사용하는 생성자
@AllArgsConstructor
//기본생성자
@NoArgsConstructor
//final붙은 변수들에 대한 constuctor
//@RequiredArgsConstructor
public class Member {
	
	//외부에서 변수에 직접 접근하지 못하게 하고, 반드시 getter, setter로 접근하도록 제한하기 위해 private 사용
	//db에서 가져온값을 변경하지 않기 위해 final 사용
	private int id;
	private String username;
	private String password;
	private String email;	
	
	//@Builder사용시, 생성자 위에 쓰게 되며, controller 에서 새 객체 생성시 순서를 지키지 않고 .builder를 이용하여 간단하게 객체에 내용을 순서 상관없이 넣을 수 있다.
	//Member m1 = Member.builder().username("ssar").password("1234"); 전부가아닌 일부만 넣을 수도 있으며, id등이 자동 증가하는 경우 등에 사용하기 좋다.
}
