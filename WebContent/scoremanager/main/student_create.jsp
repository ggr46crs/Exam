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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>
			<form method="post" action="StudentCreateExecute.action">
				<div class="col-4">
					<label class="form-label" for="student-f1-select">入学年度</label>
					<div class="form-input">
						<select class="form-select" name="ent_year" required>
							<option value="">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
									<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year}" <c:if test="${year==ent_year}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					<label class="form-label" for="student-f1-select">学生番号</label>
					<div class="form-input"><input class="form-control" type="text" name="no" placeholder="学生番号を入力してください。" required></div>
					<label class="form-label" for="student-f1-select">氏名</label>
					<div class="form-input"><input class="form-control" type="text" name="name" placeholder="氏名を入力して下さい。" required></div>
					<label class="form-label" for="student-f1-select">クラス</label>
					<div class="form-input">
					<select class="form-select" name="class_num" required>
							<option value="">--------</option>
							<c:forEach var="num" items="${class_num_set}">
							<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num}" <c:if test="${num==class_num}">selected</c:if>>${num}</option>
							</c:forEach>
					</select>
					</div>
					<div class="btn-seco">
						<button class="btn btn-secondary" name="end">登録して終了</button><br>
					</div>
					<a href="StudentList.action">戻る</a>
				</div>
			</form>
		</section>
	</c:param>
</c:import>