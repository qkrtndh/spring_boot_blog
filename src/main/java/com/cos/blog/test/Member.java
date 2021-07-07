package com.cos.blog.test;

public class Member {
	
	//외부에서 변수에 직접 접근하지 못하게 하고, 반드시 getter, setter로 접근하도록 제한하기 위해 private 사용
	private int id;
	private String username;
	private String password;
	private String email;
	
	
	
	public Member(int id, String name, String password, String email) { 
		this.id = id;
		this.username = name;
		this.password = password;
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return username;
	}
	public void setName(String name) {
		this.username = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
