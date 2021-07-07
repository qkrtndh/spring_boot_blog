package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

//테이블화 시키기 위함 user클래스가 mysql에 테이블 생성함
@Entity
public class User {
	
	@Id//primarykey 등록
	@GeneratedValue(strategy = GenerationType.IDENTITY)//연결된 DB의 너버링 전략을 따라간다는 설정	
	private int id;//auto_increment
	
	@Column(nullable = false,length = 30)
	private String username;//아이디
	@Column(nullable = false,length = 100)//hash 암호화시를 위해 넉넉하게 설정
	private String password;
	@Column(nullable = false,length = 50)
	private String email;
	
	@ColumnDefault("'user'")
	private String role;//Enum을 쓰는게 좋다. 우선 임시로 String
	
	@CreationTimestamp//시간이 자동 입력
	private Timestamp createDate;
}
