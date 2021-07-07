package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 ->응답(HTML)파일
//@Controller

//브라우저를 통한 요청은 get밖에 할 수 없다
//컨트롤러 -> 사용자가 요청시 응답(Data)을 해준다
@RestController
public class HttpControllerTest {
	
	//http://localhost:8080/http/get(select)
	//http://localhost:8080/http/get?id=1&username=ssar
	@GetMapping("/http/get")
	//public String getTest(@RequestParam int id, @RequestParam String username) {
	//동일하게 동작한다.
	public String getTest(Member m) {
		return "get 요청"+m.getId()+", "+m.getName();
	}
	
	//http://localhost:8080/http/post(insert)
	//post는 주소에 정보를 보내지 않고 body에 보낸다. ex:form 태그 방식
	@PostMapping("/http/post")
	
	//public String postTest(@RequestBody String text) {
	//post방식으로 일반 text를 전송할때 사용한다.
	//return text;(text/plain)
	
	//public String postTest(@RequestBody Member m) {
		//post방식으로 json방식으로 전송할때 사용한다.
		//return "post 요청"+m.getId()+", "+m.getName();(application/json)
	
	//위의 두가지 는 스프링 부트의 messageconverter 가 자동으로 파싱하여 데이터를 넣어준다.
	
	public String postTest(Member m) {
		return "post 요청"+m.getId()+", "+m.getName();
	}
	
	//http://localhost:8080/http/put(update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put  요청"+m.getId()+", "+m.getName();
	}
	
	//http://localhost:8080/http/delete(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
