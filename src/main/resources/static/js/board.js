let index = {
	init: function() {
		$("#btn-save").on("click", () => {
			this.save();
		});
		$("#btn-delete").on("click", () => {
			this.deleteById();
		});
		$("#btn-update").on("click", () => {
			this.update();
		});
		$("#btn-reply-save").on("click", () => {
			this.replySave();
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
		let id = $("#id").text();
		$.ajax({
			type: "DELETE",
			url: "/api/board/"+id
		}).done(function(response) {
			alert("삭제 완료");
			location.href = "/";

		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	update: function() {
		let id = $("#id").val()
		
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		$.ajax({
			type: "PUT",
			url: "/api/board/"+id,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8"
			//dataType:"json"//응답이 왔을 때 json 형식으로 온다면 javascript로 변환//자동 변환 되는듯
		}).done(function(response) {
			alert("글수정 완료");
			location.href = "/";

		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	replySave: function() {
		let data = {
			content: $("#reply-content").val(),
			boardId: $("#boardId").val(),
			userId:$("#userId").val()
		};
		
		$.ajax({
			type: "POST",
			url: `/api/board/${data.boardId}/reply`,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8"
			//dataType:"json"//응답이 왔을 때 json 형식으로 온다면 javascript로 변환//자동 변환 되는듯
		}).done(function(response) {
			alert("댓글 작성 완료");
			location.href = `/board/${data.boardId}`;

		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	}
}

index.init();	