package com.cos.blog.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false,length = 100)
	private String title;
	
	@Lob//대용량 데이터
	private String content;//섬머노트 라이브러리 사용할것
	//일반적 글은 HTML태그가 섞여 용량이 커짐
	
	@ColumnDefault("0")
	private int count;//조회수
	
	
	//private int userid;
	@ManyToOne//Many=Board, User=One 하나의 유저가 많은 게시글을 작성할 수 있다.
	@JoinColumn(name="userId")//DB만들어질땐 userid라는 이름으로 만들어진다.
	private User user;//DB는 오브젝터를 저장할 수 없다. 자바는 가능하다.
	//그러므로 보통은 자바가 DB에 맞춰서 join함. 하지만 orm을 사용하면 오브젝트로 저장이 가능하다.
	//이렇게하면 자동으로 관계를 형성하고 외래키로 등록이 된다
	
	@CreationTimestamp
	private Timestamp createDate;
}
