package com.cos.blog.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.UserRepository;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void 글쓰기(Board board,User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
		
	}
	
	public List<Board> 글목록(){
		return boardRepository.findAll();
	}
	
	/*@Transactional(readOnly = true) //select 시 트랜젝션 시작, 서비스 종료시 트랜잭션 종료(정합성 유지)
	public User 로그인(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
	}*/
}
