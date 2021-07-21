<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form>
	<input type="hidden" id="id" value="${reply.id}"/>
	<input type="hidden" id="boardId" value="${reply.board.id }"/>
		<div class="form-group">
			<label for="content">댓글 수정</label>
			<textarea class="form-control summernote" rows="1" id="content">${reply.content}</textarea>
		</div>
	</form>
	<button id="btn-replyUpdate" class="btn btn-primary">save</button>
</div>

<script src="/js/board.js"></script>
<br />
<%@ include file="../layout/footer.jsp"%>
