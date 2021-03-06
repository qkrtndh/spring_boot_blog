package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.BoardService;

@Controller//일반 Controller는 리턴시 viewResolver가 동작함
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@GetMapping({"","/"})
	public String index(Model model,@PageableDefault(size=3,sort="id",direction=Sort.Direction.DESC)Pageable pageable) {
		model.addAttribute("boards",boardService.글목록(pageable));
		return "index";//viewResolver가 동작하면 model정보를 가지고 인덱스페이지로 이동 반환값 앞뒤로 prefix suffix 붙여줌
	}
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id,Model model, @AuthenticationPrincipal PrincipalDetail principal) {
		model.addAttribute("board",boardService.글상세보기(id));
		boardService.조회수증가(id,principal.getUser().getId());
		return "board/detail";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/updateForm";
	}
	
	@GetMapping("/reply/{id}/updateForm")
	public String replyUpdateForm(@PathVariable int id, Model model) {
		model.addAttribute("reply",boardService.댓글찾기(id));
		return "reply/updateForm";
	}
}
