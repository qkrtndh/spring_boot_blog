package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Getter;

@Getter
public class PrincipalDetail implements UserDetails{
	private User user;//컴포지션
	

	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() { 
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		//계정만료 여부
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		//계정 잠금 여부
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		//계정암호 만료 여부
		return true;
	}

	@Override
	public boolean isEnabled() {
		//계정 활성화 여부
		return true;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//계정이 가지고 있는 권한 목록을 리턴한다.
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		//ROLE_USER로 리턴됨 _는 스프링에서 role 을 받을 때 규칙으로 넣어야함
		collectors.add(()->{	return "ROLE_"+user.getRole();});
		
		return collectors;
	}
}
