package com.cos.blog.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Transactional
	public void 글쓰기(Board board,User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
		
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("상세보기 실패: 아이디를 찾을 수 없음");
		});
	}
	
	@Transactional
	public void 조회수증가(int id, int userId) {
		Board board = boardRepository.findById(id).get();
		if(board.getUser().getId() != userId) {
			board.setCount(board.getCount()+1);
		}
	}
	
	@Transactional
	public void 삭제하기(int id)
	{
		System.out.println("삭제하기:" + id);
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 수정하기(int id,Board requestBoard)
	{
		System.out.println("수정하기:" + id);
		Board board = boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("상세보기 실패: 아이디를 찾을 수 없음");
		});//영속화
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수로 종료시 service가 종료될때 트랜잭션이 종료됨, 이때 더티체킹-자동 업데이트가됨(db flush)
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
				
		replyRepository.mSave(replySaveRequestDto.getUserId(),replySaveRequestDto.getBoardId(),replySaveRequestDto.getContent());
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
				
		replyRepository.deleteById(replyId);
	}
	
}
	/*@Transactional(readOnly = true) //select 시 트랜젝션 시작, 서비스 종료시 트랜잭션 종료(정합성 유지)
	public User 로그인(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
	}*/
