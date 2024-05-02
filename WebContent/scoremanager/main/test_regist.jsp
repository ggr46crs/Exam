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
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

			<form method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-4">
						<label class="form-label" for="test-f1-select">入学年度</label>
						<select class="form-select" id="test-f1r-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${year}">
								<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-4">
						<label class="form-label" for="test-f2-select">クラス</label>
						<select class="form-select " id="test-f2-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${num}">
								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-4">
						<label class="form-label" for="test-f3-select">科目</label>
						<select class="form-select " id="test-f3-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="subject.cd" items="${subject.cd}">
								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-4">
						<label class="form-label" for="test-f4-select">回目</label>
						<select class="form-select " id="test-f4-select" name="f4">
							<option value="0">--------</option>
							<c:forEach var="num" items="${num}">
								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num}" <c:if test="${num==f4}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>

					<div class="mt-2 text-warning">${errors.get("f1")}</div>
				</div>
			</form>
			<c:choose>
				<c:when test="${tests.size()>0}">
					<div>検索結果:${tests.size()}件</div>
					<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>クラス</th>
							<th>科目</th>
							<th>回目</th>
							<th></th>
							<th></th>
						</tr>
						<c:forEach var="test" items="${tests}">
							<tr>
								<td>${test.year}</td>
								<td>${test.num}</td>
								<td>${test.subject.cd}</td>
								<td>${test.num}</td>
								<td class="text-center">
									<%-- 在学フラグが経っている場合「〇」それ以外は「×」を表示 --%>
								</td>
								<td><a href="StudentUpdate.action?no=${student.no}">変更</a></td>
								<td><a href="StudentDelete.action?no=${student.no}">削除</a></td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div>成績情報が存在しませんでした</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>
