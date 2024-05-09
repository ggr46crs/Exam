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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
			<form method="post" action="StudentUpdateExecute.action">
				<div class="col-4">
					<label class="form-label" for="student-f1-select">入学年度</label>
					<div class="form-input"><input class="form-control" type="text" name="ent_year" value="${ent_year_set}" readonly></div>
					<label class="form-label" for="student-f1-select">学生番号</label>
					<div class="form-input"><input class="form-control" type="text" name="no" value="${no_set}" readonly></div>
					<label class="form-label" for="student-f1-select">氏名</label>
					<div class="form-input"><input class="form-control" type="text" name="name" value="${name_set}" required></div>
					<label class="form-label" for="student-f1-select">クラス</label>
					<div class="form-input">
					<select class="form-select" name="class_num" required>
							<c:forEach var="num" items="${class_num_set}">
							<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num}" <c:if test="${num==class_num}">selected</c:if>>${num}</option>
							</c:forEach>
					</select>
					</div>
					<label class="form-check-label" >在学中
					<input class="form-check-input" type="checkbox"
							name="is_attend" value="t"
							<c:if test="${isAttend_set==true}">checked</c:if>>
					</label>
					<div class="btn-seco">
						<button class="btn btn-primary" name="end">変更</button><br>
					</div>
					<a href="StudentList.action">戻る</a>
				</div>
			</form>
		</section>
	</c:param>
</c:import>