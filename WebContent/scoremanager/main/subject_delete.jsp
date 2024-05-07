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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
				<div class="col-4">
					<label class="form-label" for="student-f1-select">「${name_set}(${cd_set})」を削除してもよろしいですか</label>
					<div class="btn-seco">
						<button class="btn btn-primary" name="end">削除</button><br>
						<a href="SubjectList.action">戻る</a>
					</div>
				</div>
			</section>
		</c:param>
</c:import>