let index = {
	init:function(){
		$("#btn-save").on("click",()=>{
			this.save();
		});
	},
	
	save:function(){
		let data={
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		}
		$.ajax().done().fail();//ajax통신을 이용하여 3개의 데이터를 json으로 변경하여 insert 요청
	}
}

index.init();	