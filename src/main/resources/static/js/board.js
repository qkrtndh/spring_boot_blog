let index = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		});
		$("#btn-delete").on("click", () => {
			this.deleteById();
		});
	},

	save: function() {
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8"
			//dataType:"json"//응답이 왔을 때 json 형식으로 온다면 javascript로 변환//자동 변환 되는듯
		}).done(function(response) {
			alert("글쓰기 완료");
			location.href = "/";

		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	deleteById: function() {
		var id = $("#id").text();
		$.ajax({
			type: "DELETE",
			url: "/api/board/"+id
		}).done(function(response) {
			alert("삭제 완료");
			location.href = "/";

		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	}
}

index.init();	