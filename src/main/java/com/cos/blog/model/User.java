package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//테이블화 시키기 위함 user클래스가 mysql에 테이블 생성함

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@DynamicInsert insert시에 null 인 필드 제외
//role의 경우 default가 user인데, insert시 null이 입력되어 덮어씌워짐. 이를 방지하기 위함. 하지만 덕지덕지 어노테이션이 많아져서 제외
public class User {
	
	@Id//primarykey 등록
	@GeneratedValue(strategy = GenerationType.IDENTITY)//연결된 DB의 너버링 전략을 따라간다는 설정	
	private int id;//auto_increment
	
	@Column(nullable = false,length = 30, unique = true)
	private String username;//아이디
	@Column(nullable = false,length = 100)//hash 암호화시를 위해 넉넉하게 설정
	private String password;
	@Column(nullable = false,length = 50)
	private String email;
	
	//@ColumnDefault("'user'")//어노테이션증가를 막기위해 제외.
	//private String role;//Enum을 쓰는게 좋다. 우선 임시로 String
	@Enumerated(EnumType.STRING)//DB에는 RoleType이라는 Enum이 없으므로 적용해줌
	private RoleType role;	//USER,ADMIN으로 강제됨
	
	@CreationTimestamp//시간이 자동 입력
	private Timestamp createDate;
}
