let index = {
	init:function(){
		$("#btn-save").on("click",()=>{
			this.save();
		});
		$("#btn-update").on("click",()=>{
			this.update();
		});
	},
	
	save:function(){
		let data={
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		}
		//ajax 호출시 default가 비동기 호출
		$.ajax({
			//회원가입 수행 요청
			type:"POST",
			url:"/auth/joinProc",
			data:JSON.stringify(data),//http body 데이터
			contentType:"application/json; charset=utf-8"//body데이터 타입 명시
			//dataType:"json"//응답이 왔을 때 json 형식으로 온다면 javascript로 변환//자동 변환 되는듯
		}).done(function(response){
			//성공시
			alert("회원가입 완료");
			location.href="/";
			
		}).fail(function(error){
			//실패시
			alert(JSON.stringify(error));
		});//ajax통신을 이용하여 3개의 데이터를 json으로 변경하여 insert 요청
	},
	
	update:function(){
		let data={
			id :$("#id").val(),
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		};
		$.ajax({
			type:"PUT",
			url:"/user",
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8"
			
		}).done(function(response){
			//성공시
			alert("수정 완료");
			location.href="/";
			
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
}

index.init();	