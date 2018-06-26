<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.abc.asms.utils.HTMLUtils" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<jsp:include page="_header.jsp" />
<title>売上検索結果表示|物品売上管理システム</title>
</head>
<body>
		<jsp:include page="_navbar.jsp" />

		<div class="container">

			<jsp:include page="_successes.jsp" />
			<jsp:include page="_errors.jsp" />

			<div class="row">
				<h1 class="col-sm-12">売上検索結果表示</h1>
			</div>

			<div class="row">
			<div class="col-sm-12">
				<table class="table">
					<tr>
						<th class="col-sm-1">操作</th>
						<th>No</th>
						<th>販売日</th>
						<th>担当</th>
						<th class="col-sm-2">商品カテゴリー</th>
						<th class="col-sm-4">商品名</th>
						<th>単価</th>
						<th>個数</th>
						<th>小計</th>
					</tr>

					<c:forEach var="sale" items="${salesList}">
					<tr>
						<td><a href="S0022.html?saleId=${HTMLUtils.escapeHTML(sale.saleId)}" class="btn btn-primary"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 詳 細</a></td>
						<td class="text-right">${HTMLUtils.escapeHTML(sale.saleId)}</td>
						<td>${HTMLUtils.escapeHTML(HTMLUtils.formatLocalDate(sale.saleDate))}</td>
						<td>${HTMLUtils.escapeHTML(sale.account)}</td>
						<td>${HTMLUtils.escapeHTML(sale.category)}</td>
						<td>${HTMLUtils.escapeHTML(sale.tradeName)}</td>
						<td class="text-right"><fmt:formatNumber value="${HTMLUtils.escapeHTML(sale.unitPrice)}" /></td>
						<td class="text-right"><fmt:formatNumber value="${HTMLUtils.escapeHTML(sale.saleNumber)}" /></td>
						<td class="text-right"><fmt:formatNumber value="${HTMLUtils.calcSum(sale.unitPrice, sale.saleNumber)}" /></td>
					</tr>
					</c:forEach>
				</table>
			</div>
			</div>

		</div><!-- /container -->

		<jsp:include page="_footer.jsp" />

</body>
</html>