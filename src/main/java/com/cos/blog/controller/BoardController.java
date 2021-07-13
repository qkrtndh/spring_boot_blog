package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
