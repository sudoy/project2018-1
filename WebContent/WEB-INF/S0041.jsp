<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.abc.asms.utils.HTMLUtils" %>


<!DOCTYPE html>
<html lang="ja">
	<head>
		<jsp:include page="_header.jsp" />
		<title>アカウント検索結果一覧|物品売上管理システム</title>
	</head>
	<body>

		<jsp:include page="_navbar.jsp" />

		<div class="container">

			<div class="row">
				<h1>アカウント検索結果一覧</h1>
			</div>

			<div class="row">
				<form class="form-horizontal" action="#" method="post">

					<table class="table">
						<tr>
							<c:if test="${check == 10 || check == 11}">
								<th class="col-sm-2">操作</th>
							</c:if>
							<th class="col-sm-1">No</th>
							<th class="col-sm-2">氏名</th>
							<th class="col-sm-4">メールアドレス</th>
							<th class="col-sm-4">権限</th>
						</tr>

						<c:forEach var="account" items="${accountList}">
							<tr>
								<c:if test="${check == 10 || check == 11}">
									<td>
										<a href="S0042.html?accountId=${account.accountId}" class="btn btn-primary"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 編 集</a>
										<a href="S0044.html?accountId=${account.accountId}" class="btn btn-danger"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 削 除</a>
									</td>
								</c:if>
								<td class="text-right">${account.accountId}</td>
								<td>${account.name}</td>
								<td>${account.mail}</td>
								<td>${HTMLUtils.parseAuthority(account.authority)}</td>
							</tr>
						</c:forEach>
					</table>

				</form>
			</div>

		</div><!-- /container -->

		<jsp:include page="_footer.jsp" />

	</body>
</html>