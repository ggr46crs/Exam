<%-- 学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生管理</h2>
			<div class="my-2 text-end px-4">
				<a href="StudentCreate.action">成績参照</a>
			</div>
			<form method="get" action="TestListSubjectExecute.action">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select" id="student-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select " id="student-f2-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set}">


								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="form-label" for="student-f3-select">科目</label>
						<select class="form-select " id="student-f3-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="subject" items="${subject_set}">


								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${subject.key}">${subject.value}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1")}</div>
				</div>
			</form>
			<form method="get" action="TestListStudentExecute.action">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="student-f4-select">学生番号</label>
						<div class="form-input"><input class="form-control" type="text" name="f4" placeholder="学生番号を入力して下さい。" required></div>
					</div>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1")}</div>
				</div>
			</form>
			<c:choose>
			<c:when test="${tests.size()!=0}">
				科目：${subject_name}
				<table class="table table-hover">
						<tr>
							<th>科目名</th>
							<th>科目コード</th>
							<th>回数</th>
							<th>点数</th>
						</tr>
						<c:forEach var="test" items="${tests}">
							<tr>
								<td>${test.Sub_name}</td>
								<td>${test.subjectCd}</td>
								<td>${test.no}</td>
								<td>${test.point}</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					学生情報が存在しませんでした。
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>