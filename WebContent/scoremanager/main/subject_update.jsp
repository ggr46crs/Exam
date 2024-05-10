<%-- 学生登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="../../css/style.css">
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
			<form method="post" action="SubjectUpdateExecute.action">
				<div class="col-4">
					<label class="form-label" for="subject-f1-select">科目コード</label>
					<div class="form-input"><input class="form-control" type="text" name="cd" value="${cd_set}" readonly></div>

					<label class="form-label" for="subject-f1-select">科目名</label>
					<div class="form-input"><input class="form-control" type="text" name="name" value="${name_set}" required></div>

					<div class="btn-seco">
						<button class="btn btn-primary" name="end">変更</button><br>
					</div>
					<a href="SubjectList.action">戻る</a>
				</div>
			</form>
		</section>
	</c:param>
</c:import>
