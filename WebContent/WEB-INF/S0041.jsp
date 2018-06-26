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

		<jsp:include page="_successes.jsp" />

			<div class="row">
				<h1 class="col-sm-12">アカウント検索結果一覧</h1>
			</div>

			<div class="row">
				<div class="col-sm-12">
				<form class="form-horizontal" action="#" method="post">

					<table class="table">
						<tr>
							<c:if test="${check == 10 || check == 11}">
								<th class="col-sm-2">操作</th>
							</c:if>
							<th class="col-sm-3">氏名</th>
							<th class="col-sm-2">No(ここｶﾅに変えてね)</th>
							<th>メールアドレス</th>
							<th class="col-sm-2">権限</th>
						</tr>

						<c:forEach var="account" items="${accountList}">
							<tr>
								<c:if test="${check == 10 || check == 11}">
									<td>
										<a href="S0042.html?accountId=${account.accountId}" class="btn btn-primary"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 編 集</a>
										<a href="S0044.html?accountId=${account.accountId}" class="btn btn-danger"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 削 除</a>
									</td>
								</c:if>
								<td>${HTMLUtils.escapeHTML(account.name)}</td>
								<td class="text-right">${account.accountId}</td>
								<td>${HTMLUtils.escapeHTML(account.mail)}</td>
								<td>${HTMLUtils.formatAuthority(account.authority)}</td>
							</tr>
						</c:forEach>
					</table>

				</form>
				</div>
			</div>

		</div><!-- /container -->

		<jsp:include page="_footer.jsp" />

	</body>
</html>