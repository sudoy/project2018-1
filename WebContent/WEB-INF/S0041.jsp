<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.abc.asms.utils.HTMLUtils" %>


<!DOCTYPE html>
<html lang="ja">
	<head>
		<jsp:include page="_header.jsp" />
		<title>アカウント検索結果一覧|物品売上管理システム</title>
		<style>

			.dropdown-menu{
				top: auto;
			    bottom: 100%;
			    margin-bottom: 2px;
			}
			th[aria-expanded="true"]>.dropdown-menu{
				display: block;
			}

			.table>thead>tr>th {
				padding: 0;
			}
			th>.btn-default{
				padding: 10px 30px 10px 9px;
			}
			#crl{
				padding: 10px 30px 10px 9px;
			}
			#name{
				padding: 10px 30px 10px 9px;
			}

			.dropdown{
				cursor:pointer;
			}

		</style>

	</head>
	<body>

		<jsp:include page="_navbar.jsp" />

		<div class="container">

		<jsp:include page="_successes.jsp" />
		<jsp:include page="_errors.jsp" />

			<div class="row">
				<h1 class="col-sm-12">アカウント検索結果一覧</h1>
			</div>

			<div class="row">
				<div class="col-sm-12">
					<table class="table">
						<thead>
						<tr>
							<c:if test="${check == 10 || check == 11}">
								<th class="text-center" id="crl" nowrap>操作</th>
							</c:if>
							<th id="name" nowrap>氏名</th>
							<th class="btn-default dropdown" nowrap>
								<div class=" dropdown-toggle btn-default" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									ふりがな
									<span class="glyphicon glyphicon-sort${HTMLUtils.changeMark(param.sort, 'kana')}" aria-hidden="true"></span>
								</div>
								<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
									<li><a href="S0041.html?sort=kana">昇順</a></li>
									<li><a href="S0041.html?sort=kana desc">降順</a></li>
								</ul>
							</th>
							<th class="btn-default dropdown" nowrap>
								<div class=" dropdown-toggle btn-default" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									メールアドレス
									<span class="glyphicon glyphicon-sort${HTMLUtils.changeMark(param.sort, 'mail')}" aria-hidden="true"></span>
								</div>
								<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
									<li><a href="S0041.html?sort=mail">昇順</a></li>
									<li><a href="S0041.html?sort=mail desc">降順</a></li>
								</ul>
							</th>
							<th class="btn-default dropdown" nowrap>
								<div class=" dropdown-toggle btn-default" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									権限
									<span class="glyphicon glyphicon-sort${HTMLUtils.changeMark(param.sort, 'authority')}" aria-hidden="true"></span>
								</div>
								<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
									<li><a href="S0041.html?sort=authority">昇順</a></li>
									<li><a href="S0041.html?sort=authority desc">降順</a></li>
								</ul>
							</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach var="account" items="${accountList}">
							<tr>
								<c:if test="${check == 10 || check == 11}">
									<td>
										<a href="S0042.html?accountId=${account.accountId}" class="btn btn-primary"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 編 集</a>
										<a href="S0044.html?accountId=${account.accountId}" class="btn btn-danger"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 削 除</a>
									</td>
								</c:if>
								<td>${HTMLUtils.escapeHTML(account.name)}</td>
								<td>${HTMLUtils.escapeHTML(account.kana)}</td>
								<td>${HTMLUtils.escapeHTML(account.mail)}</td>
								<td>${HTMLUtils.formatAuthority(account.authority)}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<c:if test="${pageDisplay != 0}">
						<div style="text-align:center;">
							<nav aria-label="Page navigation ">
								<ul class="pagination  pagination-lg">
									<c:if test="${param.pageNumber != null ? param.pageNumber != 0 : false}">
									<li>
										<a href="S0041.html?sort=${param.sort}&pageNumber=0" aria-label="Previous">
											<span aria-hidden="true">&laquo;</span>
										</a>
									</li>
									</c:if>
									<c:forEach var="page" begin="0" end="${pageDisplay}">
										<li class="${HTMLUtils.currentPage(param.pageNumber, page)}"><a href="S0041.html?sort=${param.sort}&pageNumber=${page}">${page + 1}</a></li>
									</c:forEach>
									<c:if test="${param.pageNumber != pageDisplay}">
									<li>
										<a href="S0041.html?sort=${param.sort}&pageNumber=${pageDisplay}" aria-label="Next">
											<span aria-hidden="true">&raquo;</span>
										</a>
									</li>
									</c:if>
								</ul>
							</nav>
						</div>
					</c:if>
				</div>
			</div>

		</div><!-- /container -->

		<jsp:include page="_footer.jsp" />

	</body>
</html>